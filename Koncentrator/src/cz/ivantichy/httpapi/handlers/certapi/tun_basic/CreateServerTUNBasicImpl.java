package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateServerTUNBasicImpl extends CommandExecutor implements Create {
	private static Logger log = LogManager
			.getLogger(CreateServerTUNBasicImpl.class.getName());

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am CreateServerTUNBasicImpl");

		String source = FileWork.replaceDoubleSlashes(Static.OPENVPNLOCATION
				+ Static.TEMPLATEFOLDER + Static.FOLDERSEPARATOR
				+ json.getString(Static.SUBVPN_TYPE) + Static.FOLDERSEPARATOR);
		log.info("Source location:" + source);

		log.info("wtf?");
		String destination = FileWork
				.replaceDoubleSlashes(Static.OPENVPNLOCATION
						+ Static.FOLDERSEPARATOR + Static.INSTANCESFOLDER
						+ Static.FOLDERSEPARATOR
						+ json.getString(Static.SUBVPN_TYPE)
						+ Static.FOLDERSEPARATOR)
				+ json.getString(Static.SERVER_NAME) + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		// folder must not exist
		FileWork.checkFolder(source, destination);

		FileWork.copyFolder(source, destination);

		json.put("destination", destination);
		json.put("source", source);

		clear();
		appendLine(Static.STARTOPENVPNSERVICE);
		exec(json);

		FileWork.storeJSON(
				json,
				destination + Static.FOLDERSEPARATOR
						+ json.getString("server_name") + ".json");
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

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
