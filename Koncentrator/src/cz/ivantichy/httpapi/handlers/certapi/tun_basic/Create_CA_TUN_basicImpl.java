package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class Create_CA_TUN_basicImpl extends CommandExecutor implements Create {
	private static Logger log = LogManager
			.getLogger(Create_CA_TUN_basicImpl.class.getName());;

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am Create_CA_TUN_basicImpl");

		clear();

		json = IPMaskConverter.addIPRangeOrMask(json);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		FileWork.folderExists(destination);
		String cajsonfile = destination + Static.FOLDERSEPARATOR
				+ json.getString("server_name") + ".json";

		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("CA JSON: " + cajson.toString());

		json = cajson.merge(json);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");

		exec(json);

		FileWork.storeJSON(
				json,
				destination + Static.FOLDERSEPARATOR
						+ json.getString("subvpn_name") + ".json");
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		String config = FileWork.readFile("");
		log.debug("Config read: " + config);

	
	
		
		
		if (json.keySet().contains("ip_server")) {

			json.put("ip_range", IPMaskConverter.maskToRange(
					json.getString("ip_server"), json.getString("ip_mask")));
		} else if (json.keySet().contains("ip_range")) {

			json.put("ip_server", IPMaskConverter.rangeToIPAddress(json
					.getString("ip_range")));
			json.put("ip_mask",
					IPMaskConverter.rangeToMask(json.getString("ip_range")));

		}

		if (!json.keySet().contains("ip_range")) {
			throw new IOException("Missing server ip_range");

		}

		log.info("Going to fill config templace");

		config = replaceAllFields(json, config);

		String destconfigpath = Static.OPENVPNLOCATION
				+ json.getString("subvpn_type") + "_" + json.get("subvpn_name");

		log.info("Destination config file path: " + destconfigpath);
		FileWork.saveFile(destconfigpath, config);

		log.debug("Config file written: \n" + config);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		appendLine("./createsubvpn.sh {subvpn_name} {subvpn_type} {ip_range}\n");

		exec(json);
		json.put("destination", destination.replaceAll("//", "/"));

		json.put("server_conf_base64", B64.encode(config));

		FileWork.storeJSON(json,
				destination + slash + json.getString("subvpn_name") + ".json");

		log.info("JSON stored");
		log.debug("Stored CA/Server JSON: " + json.toString());

		
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
