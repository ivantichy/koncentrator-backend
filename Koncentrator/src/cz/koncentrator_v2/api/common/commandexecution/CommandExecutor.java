package cz.koncentrator_v2.api.common.commandexecution;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.koncentrator.simple.certgen.E_rrorLogSyncPipe;
import cz.ivantichy.koncentrator.simple.certgen.LogSyncPipe;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.VariableReplacer;

public abstract class CommandExecutor {

	private static Logger log = LogManager.getLogger(CommandExecutor.class
			.getName());;
	private static StringBuffer buff = new StringBuffer(1024);

	@Deprecated
	protected static String rsalocation = Static.RSALOCATION;
	@Deprecated
	protected static String slash = Static.FOLDERSEPARATOR;
	@Deprecated
	protected static String location = Static.OPENVPNLOCATION;

	protected static void appendLine(String line) {

		buff.append(line);
		buff.append(System.lineSeparator());

	}

	protected static void clear() {

		buff = new StringBuffer(1024);
	}

	protected static void exec(JSONObject json) throws IOException {
		appendLine("exit");

		String cmds = VariableReplacer.replaceAllFields(json, buff.toString());

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
