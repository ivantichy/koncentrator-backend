package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.VariableReplacer;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class UpdateProfileAdapter extends CommandExecutor implements
		POSTHandlerInterface {

	private static final Logger log = LogManager
			.getLogger(UpdateProfileAdapter.class.getName());

	@Override
	public Response handlePOST(POSTRequest req) throws IOException {
		clear();

		log.debug("PUT data: " + req.postdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.postdata);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String sourceconfigpath = destination + slash + "client.conf";
		log.info("Going to read config: " + sourceconfigpath);
		String config = FileWork.readFile(sourceconfigpath);
		log.debug("Config read: " + config);

		String oldprofilejsonfile = destination + slash + "profiles" + slash
				+ json.getString("common_name") + "_profile.json";

		log.info("Reading Old Profile JSON: " + oldprofilejsonfile);
		JSONObject oldprofilejson = new JSONObject(
				FileWork.readFile(oldprofilejsonfile));
		log.debug("Old profile JSON: " + oldprofilejson.toString());

		// schvalne, zda to zde upadne
		// serverjson.put("server_common_name", serverjson.get("common_name"));
		// serverjson.remove("common_name");

		json = oldprofilejson.merge(json);

		log.debug("Merged JSON: " + json.toString());
		log.info("Going to fill config templace");

		config = fillConfig(config, json);

		log.debug("Config file written: \n" + config);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");

		// # common_name ip_remote ip_local subvpn_name subvpn_type
		// # $1 $2 $3 $4 $5
		// appendLine("./createprofile.sh {common_name} {ip_remote} {ip_local} {subvpn_name} {subvpn_type}\n");
		// update profile

		exec(json);
		json.put("destination", destination.replaceAll("//", "/"));

		json.put("client_conf_base64", B64.encode(config));

		FileWork.storeJSON(
				json,
				destination + slash + "profiles" + slash
						+ json.getString("common_name") + "_profile.json");

		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

	private String fillConfig(String config, JSONObject json) {

		config = VariableReplacer.replaceField("server_port", config, json);
		config = VariableReplacer.replaceField("server_protocol", config, json);
		config = VariableReplacer.replaceField("server_domain_name", config, json);
		config = VariableReplacer.replaceField("server_common_name", config, json);
		config = VariableReplacer.replaceField("subvpn_name", config, json);
		config = VariableReplacer.replaceField("subvpn_type", config, json);
		config = VariableReplacer.replaceField("ta", config, json);
		config = VariableReplacer.replaceField("ca", config, json);
		config = VariableReplacer.replaceField("key", config, json);
		config = VariableReplacer.replaceField("cert", config, json);
		config += System.lineSeparator()
				+ json.getString("profile_commands").replaceAll("[,]",
						System.lineSeparator());

		return config;

	}

}
