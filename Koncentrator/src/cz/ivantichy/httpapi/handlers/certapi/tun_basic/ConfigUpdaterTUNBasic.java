package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;

import cz.koncentrator_v2.api.common.PathConfigVPN_TUN_basic;
import cz.koncentrator_v2.api.common.VariableReplacer;

public class ConfigUpdaterTUNBasic {

	private static Logger log = LogManager
			.getLogger(ConfigUpdaterTUNBasic.class.getName());;

	public static String updateAndSaveConfig(JSONObject json)
			throws JSONException, IOException {

		String config = FileWork.readFile(PathConfigVPN_TUN_basic.instance
				.getConfigTemplatePath(json));

		log.info("Config read: " + config);
		log.debug("Going to fill config templace");

		config = VariableReplacer.replaceAllFields(json, config);

		String destconfigpath = PathConfigVPN_TUN_basic.instance
				.getConfigInstancePath(json);

		log.info("Destination config file path: " + destconfigpath);
		FileWork.saveFile(destconfigpath, config);
		log.debug("Config file written: \n" + config);
		return config;

	}

}
