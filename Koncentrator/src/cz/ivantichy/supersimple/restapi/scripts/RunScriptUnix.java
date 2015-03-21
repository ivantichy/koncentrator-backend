package cz.ivantichy.supersimple.restapi.scripts;

import java.io.File;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class RunScriptUnix {
	private static final Logger log = LogManager.getLogger(RunScriptUnix.class
			.getName());

	public static ExecutionResult exec(String cmd, String parametres)
			throws Exception {

		Runtime r = Runtime.getRuntime();
		Process p = r.exec("bash");
		StringBuffer s = new StringBuffer();
		StringBuffer s2 = new StringBuffer();

		Thread t1 = new Thread(new StringPump(p.getErrorStream(), s));
		t1.start();
		Thread t2 = new Thread(new StringPump(p.getInputStream(), s2));
		t2.start();

		OutputStream o = p.getOutputStream();

		log.info("Going to execute " + new String(cmd.getBytes(), "ASCII")
				+ Static.CMDSPACE + parametres);
		o.write((cmd + Static.CMDSPACE + parametres + "\n").getBytes());

		o.flush();
		o.close();

		p.waitFor();
		t1.join(Static.EXECUTIONTIMEOUT);
		t2.join(Static.EXECUTIONTIMEOUT);

		if (p.exitValue() == 0) {
			log.info("Executed. Exit code: " + p.exitValue());

		}
		else {
			
			log.warn("Executed with error code != 0. Exit code: " + p.exitValue());
		}

		return new ExecutionResult(p.exitValue(), s.toString() + "\n"
				+ s2.toString());
	}
}
