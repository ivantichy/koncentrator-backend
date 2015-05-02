package cz.ivantichy.httpapi.executors.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateSubVPN extends CommandExecutor implements Create {
	private static final Logger log = LogManager.getLogger(CreateSubVPN.class
			.getName());

	private JSONObject createSubVPNTapAdvanced(JSONObject json)
			throws IOException {
		clear();

		if (json.keySet().contains("ip_range")) {

			json.put("ip_range", IPMaskConverter.maskToRange(
					json.getString("ip_server"), json.getString("ip_mask")));
		}

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

		storeJSON(json, destination + slash + json.getString("subvpn_name")
				+ ".json");
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return json;
	}

	public JSONObject createSubVPNTunBasic(JSONObject json)
			throws IOException {

		return createSubVPNTapAdvanced(json);
	}

	@Override
	public JSONObject createForTunBasic(JSONObject json) throws IOException {
		return createSubVPNTapAdvanced(json);
	}

	@Override
	public JSONObject createForTapAdvanced(JSONObject json) throws IOException {
		return createSubVPNTapAdvanced(json);
	}
}
