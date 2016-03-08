package cz.ivantichy.supersimple.restapi.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.scripts.UnixScriptExecutor;

public class UnixConfigReader extends ScriptConfigReader {
	private static final Logger log = LogManager
			.getLogger(UnixConfigReader.class.getName());

	protected UniversalScriptHandler createHandler(ConfigElement config) {
		log.info("Creating Unix Executor Handler");
		return new UnixScriptExecutor(config);
	}

	public static void main(String[] args) throws Exception {

		UnixConfigReader reader = new UnixConfigReader();

		reader.readConfigAndListen(args);
	}
}
