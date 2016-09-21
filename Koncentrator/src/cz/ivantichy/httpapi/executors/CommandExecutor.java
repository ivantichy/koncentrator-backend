package cz.ivantichy.httpapi.executors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.koncentrator.simple.certgen.E_rrorLogSyncPipe;
import cz.ivantichy.koncentrator.simple.certgen.LogSyncPipe;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public abstract class CommandExecutor {

	private static Logger log = LogManager.getLogger(CommandExecutor.class
			.getName());;
	private static StringBuffer buff = new StringBuffer(1024);

	 protected static String location = Static.RSALOCATION;
	 protected static String slash = Static.FOLDERSEPARATOR;

	protected static String replaceField(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]", json.get(fieldname)
				.toString());

	}

	protected static String replaceFieldB64(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]",
				B64.decode(json.get(fieldname).toString()));

	}

	protected static String replaceAllFields(JSONObject json, String input)
			throws IOException {

		log.debug("Replace all fields - JSON: " + json.toString());
		log.debug("Replace all fields - input: " + input);
		for (Iterator<String> iterator = json.keys(); iterator.hasNext();) {
			String key = iterator.next();

			log.debug("Trying to replace: " + key);
			input = replaceField(key, input, json);

		}

		if (input.indexOf('{') > -1) {
			log.error("Missing param " + input + "  JSON:" + json.toString());
			throw new IOException("Missing parameter " + input);
		}

		return input;
	}

	protected static void appendLine(String line) {

		buff.append(line);
		buff.append(System.lineSeparator());

	}

	protected static void clear() {

		buff = new StringBuffer(1024);
	}

	protected static void exec(JSONObject json) throws IOException {
		appendLine("exit");

		String cmds = replaceAllFields(json, buff.toString());

		log.info("Going to execute: " + cmds);

		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new E_rrorLogSyncPipe(p.getErrorStream(), log)).start();
		new Thread(new LogSyncPipe(p.getInputStream(), log)).start();

		OutputStream o = p.getOutputStream();

		o.write(cmds.getBytes());

		o.close();

		try {
			p.waitFor();
		} catch (InterruptedException e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());
		}

		if (p.exitValue() != 0) {
			throw new IOException("process exited with non zero code "
					+ p.exitValue());
		}

		log.debug("Process exit code " + p.exitValue());

	}

	

}
