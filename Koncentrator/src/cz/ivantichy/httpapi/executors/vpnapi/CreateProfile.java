package cz.ivantichy.httpapi.executors.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.VariableReplacer;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class CreateProfile extends CommandExecutor {

	private static final Logger log = LogManager.getLogger(CreateProfile.class
			.getName());

	public static JSONObject createProfileTapAdvanced(JSONObject json)
			throws IOException {

		clear();

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String sourceconfigpath = destination + slash + "client.conf";
		log.info("Going to read config: " + sourceconfigpath);
		String config = FileWork.readFile(sourceconfigpath);
		log.debug("Config read: " + config);

		String cajsonfile = destination + slash + json.getString("subvpn_name")
				+ ".json";
		log.info("Reading Server JSON: " + cajsonfile);
		JSONObject serverjson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("Server JSON: " + serverjson.toString());

		serverjson.put("server_common_name", serverjson.get("common_name"));
		serverjson.remove("common_name");

		json.merge(serverjson);
		log.info("Going to fill config templace");

		config = fillConfig(config, json);

		log.debug("Config file written: \n" + config);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");

		// # common_name ip_remote ip_local subvpn_name subvpn_type
		// # $1 $2 $3 $4 $5
		appendLine("./createprofile.sh {common_name} {ip_remote} {ip_local} {subvpn_name} {subvpn_type}\n");
		exec(json);
		json.put("destination", destination.replaceAll("//", "/"));

		json.put("client_conf_base64", B64.encode(config));

		FileWork.storeJSON(
				json,
				destination + slash + "profiles" + slash
						+ json.getString("common_name") + "_profile.json");

		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return json;
	}

	private static String fillConfig(String config, JSONObject json) {

		config = VariableReplacer.replaceField("server_port", config, json);
		config = VariableReplacer.replaceField("server_protocol", config, json);
		config = VariableReplacer.replaceField("server_domain_name", config, json);
		config = VariableReplacer.replaceField("server_common_name", config, json);
		config = VariableReplacer.replaceField("subvpn_name", config, json);
		config = VariableReplacer.replaceField("subvpn_type", config, json);
		config = VariableReplacer.replaceFieldB64("ta", config, json);
		config = VariableReplacer.replaceFieldB64("ca", config, json);
		config = VariableReplacer.replaceFieldB64("key", config, json);
		config = VariableReplacer.replaceFieldB64("cert", config, json);
		config += System.lineSeparator()
				+ json.getString("profile_commands").replaceAll("[,]",
						System.lineSeparator());

		return config;

	}

	public static JSONObject createProfileTunBasic(JSONObject json)
			throws IOException {
		clear();
		// TODO not implemented
		// throw new IOException("not implemented");
		
		return createProfileTapAdvanced(json);
	}

}
