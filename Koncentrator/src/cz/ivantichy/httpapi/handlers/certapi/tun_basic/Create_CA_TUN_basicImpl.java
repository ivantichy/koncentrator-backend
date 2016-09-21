package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class Create_CA_TUN_basicImpl extends CommandExecutor implements Create {
	private static Logger log = LogManager
			.getLogger(Create_CA_TUN_basicImpl.class.getName());;

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am Create_CA_TUN_basicImpl");

		clear();

		json = IPMaskConverter.addIPRangeOrMask(json);

		String source = Static.OPENVPNLOCATION + Static.GENERATEFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR;
		log.info("Source location:" + source);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		FileWork.checkFolder(source, destination);
		FileWork.copyFolder(source, destination);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");

		exec(json);

		json.put("destination", destination.replaceAll("//", "/"));
		json.put("source", source.replaceAll("//", "/"));

		FileWork.storeJSON(
				json,
				destination + Static.FOLDERSEPARATOR
						+ json.getString("subvpn_name") + ".json");
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
