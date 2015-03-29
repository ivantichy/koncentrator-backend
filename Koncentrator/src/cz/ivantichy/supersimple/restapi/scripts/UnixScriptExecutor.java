package cz.ivantichy.supersimple.restapi.scripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.config.ConfigElement;
import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class UnixScriptExecutor extends UniversalScriptHandler {

	private static final Logger log = LogManager
			.getLogger(UnixScriptExecutor.class.getName());

	public UnixScriptExecutor(ConfigElement config) {
		super(config);

	}

	protected ExecutionResult executeCommand(String script, String parametres)
			throws Exception {
		log.info("Executing command \"" + script + Static.CMDSPACE
				+ parametres + "\"");

		return RunScriptUnix.exec(script, parametres);

	}

}
