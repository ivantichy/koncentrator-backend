package cz.ivantichy.supersimple.restapi.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.scripts.WindowsScriptExecutor;

public class WinConfigReader extends ScriptConfigReader {
	private static final Logger log = LogManager
			.getLogger(WinConfigReader.class.getName());

	protected UniversalScriptHandler createHandler(ConfigElement config) {
		log.info("Creating Windows Executor Handler");
		return new WindowsScriptExecutor(config);
	}

	public static void main(String[] args) throws Exception {

		WinConfigReader reader = new WinConfigReader();

		reader.readConfigAndListen(args);

	}
}
