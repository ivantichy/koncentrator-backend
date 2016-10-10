package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import archive.Create;
import cz.ivantichy.fileutils.FileWork;
import cz.koncentrator_v2.api.common.PathConfigVPN_TUN_basic;

public class CreateServerTUNBasicImpl implements Create {
	private static Logger log = LogManager
			.getLogger(CreateServerTUNBasicImpl.class.getName());

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am CreateServerTUNBasicImpl");

		String source = PathConfigVPN_TUN_basic.instance.getSourcePath(json);
		log.info("Source location:" + source);

		String destination = PathConfigVPN_TUN_basic.instance
				.getDestinationPath(json);
		log.info("Destination location:" + destination);

		// folder must not exist
		FileWork.checkFolder(source, destination);

		FileWork.copyFolder(source, destination);

		json.put("destination", destination);
		json.put("source", source);

		FileWork.storeJSON(json,
				PathConfigVPN_TUN_basic.instance.getCAJsonPath(json));
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		ServiceRestarter.restartAllProcesses(json);

		return json;

	}

	@Override
	public JSONObject createForTunBasic(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject createForTapAdvanced(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
