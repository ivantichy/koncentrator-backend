package cz.ivantichy.koncentrator.simple.certgen;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public abstract class CommandExecutor {

	protected static Logger log;
	private static StringBuffer buff = new StringBuffer(1024);
	protected static String location = Static.RSALOCATION;
	protected static String slash = Static.FOLDERSEPARATOR;

	protected static String replaceField(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]",
				json.getString(fieldname));

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
		log.debug("JSON: " + json);
		appendLine("exit");

		String cmds = replaceAllFields(json, buff.toString());
		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new ErrorLogSyncPipe(p.getErrorStream(), log)).start();
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

	protected static void readFileToJSON(JSONObject json, String path,
			String name) throws IOException {

		String f = new String(Files.readAllBytes(Paths.get(path)));

		json.put(name, B64.encode(f));
	}

	protected static void storeJSON(JSONObject json, String path)
			throws IOException {

		Files.write(Paths.get(path), json.toString().getBytes());

	}

}
