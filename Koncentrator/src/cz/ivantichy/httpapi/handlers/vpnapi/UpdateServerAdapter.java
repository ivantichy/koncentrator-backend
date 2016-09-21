package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class UpdateServerAdapter extends CommandExecutor implements
		POSTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(UpdateServerAdapter.class.getName());

	@Override
	public Response handlePOST(POSTRequest req) throws IOException {
		clear();

		log.debug("POST data: " + req.postdata);

		log.info("going to handle POST. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.postdata);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String sourceconfigpath = destination + slash + "server.conf";
		log.info("Going to read config: " + sourceconfigpath);
		String config = FileWork.readFile(sourceconfigpath);
		log.debug("Config read: " + config);

		String cajsonfile = destination + slash + json.getString("subvpn_name")
				+ ".json";
		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("CA JSON: " + cajson.toString());

		json = cajson.merge(json);

		json.put(
				"ip_range",
				IPMaskConverter.maskToRange(json.getString("ip_server"),
						json.getString("ip_mask")));

		log.info("Going to fill config templace");

		config = fillConfig(config, json);

		String destconfigpath = Static.OPENVPNLOCATION
				+ json.getString("subvpn_type") + "_" + json.get("subvpn_name");

		log.info("Destination config file path: " + destconfigpath);
		FileWork.saveFile(destconfigpath, config);

		log.debug("Config file written: \n" + config);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		// restart server

		exec(json);
		json.put("destination", destination.replaceAll("//", "/"));

		json.put("server_conf_base64", B64.encode(config));

		FileWork.storeJSON(json, destination + slash + json.getString("subvpn_name")
				+ ".json");

		log.info("JSON updated");
		log.debug("Stored CA/Server JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

	private String fillConfig(String config, JSONObject json) {

		config = replaceField("server_port", config, json);
		config = replaceField("server_protocol", config, json);
		config = replaceField("server_management_port", config, json);
		config = replaceField("server_device", config, json);
		config = replaceField("subvpn_name", config, json);
		config = replaceField("subvpn_type", config, json);
		config = replaceField("ta", config, json);
		config = replaceField("ca", config, json);
		config = replaceField("key", config, json);
		config = replaceField("cert", config, json);
		config = replaceField("dh", config, json);
		config = replaceField("dh_size", config, json);
		config = replaceField("ip_server", config, json);
		config = replaceField("ip_mask", config, json);
		config += System.lineSeparator()
				+ json.getString("server_commands").replaceAll("[,]",
						System.lineSeparator());

		return config;

	}

}
