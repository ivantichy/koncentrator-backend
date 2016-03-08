package cz.ivantichy.supersimple.restapi.scripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.config.ConfigElement;
import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class WindowsScriptExecutor extends UniversalScriptHandler {

	private static final Logger log = LogManager
			.getLogger(WindowsScriptExecutor.class.getName());

	public WindowsScriptExecutor(ConfigElement config) {
		super(config);

	}

	protected ExecutionResult executeCommand(String script, String parametres)
			throws Exception {
		log.info("Executing command \"" + script + Static.CMDSPACE + parametres
				+ "\"");
		return RunScriptWin.exec(script, parametres);

	}

}
