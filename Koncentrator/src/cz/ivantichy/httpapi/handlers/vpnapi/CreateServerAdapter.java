package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.File;

import cz.ivantichy.fileutils.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import cz.ivantichy.koncentrator.simple.certgen.*;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.koncentrator.simple.certgen.SyncPipe;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.ivantichy.httpapi.*;
import cz.ivantichy.base64.*;

public class CreateServerAdapter extends CommandExecutor implements
		PUTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(CreateServerAdapter.class.getName());

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {

		log.debug("PUT data: " + req.putdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.putdata);

		json.put(
				"ip_range",
				IPMaskConverter.maskToRange(json.getString("ip_server"),
						json.getString("ip_mask")));

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String sourceconfigpath = destination + slash + "server.conf";
		log.info("Going to read config: " + sourceconfigpath);
		String config = FileWork.readFile(sourceconfigpath);
		log.debug("Config read: " + config);

		log.info("Going to fill config templace");

		config = fillConfig(config, json);

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

		storeJSON(json, destination + slash + json.getString("subvpn_name")
				+ "_server.json");

		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

	private String fillConfig(String config, JSONObject json) {

		config = replaceField("server_port", config, json);
		config = replaceField("server_protocol", config, json);
		config = replaceField("management_port", config, json);
		config = replaceField("device", config, json);
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
				+ json.getString("commands").replaceAll("[,]",
						System.lineSeparator());

		return config;

	}

}
