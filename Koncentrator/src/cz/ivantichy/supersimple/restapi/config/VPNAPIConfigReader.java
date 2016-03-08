package cz.ivantichy.supersimple.restapi.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.handlers.VPNAPIScriptHandler;

public class VPNAPIConfigReader extends UnixConfigReader {
	private static final Logger log = LogManager
			.getLogger(VPNAPIConfigReader.class.getName());

	protected UniversalScriptHandler createHandler(ConfigElement config) {
		log.info("Creating VPN API Executor Handler");
		return new VPNAPIScriptHandler(config);
	}

}
