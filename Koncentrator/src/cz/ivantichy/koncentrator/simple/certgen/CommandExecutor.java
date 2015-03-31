package cz.ivantichy.koncentrator.simple.certgen;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public abstract class CommandExecutor {

	protected static Logger log;

	protected static String replaceField(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]",
				json.getString(fieldname));

	}
	
	
	protected static String replaceAllFields(JSONObject json, String input)
			throws IOException {

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

	protected static Process preExec() throws IOException {

		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new ErrorLogSyncPipe(p.getErrorStream(), log)).start();
		new Thread(new LogSyncPipe(p.getInputStream(), log)).start();

		return p;
	}

	//

	protected static void postExec(Process p) throws IOException {

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
