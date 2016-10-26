package cz.koncentrator_v2.api.cert.GenerateServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.PathConfigCERT_TUN_basic;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class GenerateServerCommandExecutor extends CommandExecutor {
	private static final Logger log = LogManager
			.getLogger(GenerateServerCommandExecutor.class.getName());

	public JSONObject runGenerateServer(JSONObject json) throws Exception {

		log.info("GenerateServerCommandExecutor.runGenerateServer started");
		clear();

		String server_name = json.getString(Static.SERVER_NAME);

		log.debug("Going to read stored CA SON");
		
		//TODO tady je specificky TUN_basic - to je blbe
		JSONObject loadedcajson = FileWork
				.readJSON(PathConfigCERT_TUN_basic.instance.getCAJsonPath(json));

		json = loadedcajson.merge(json);

		String destination = json.getString("destination");

		FileWork.checkFolder(destination);

		appendLine("set -e");
		appendLine("cd {destination}");
		appendLine("> {destination}/keys/index.txt");
		//appendLine("touch {destination}/keys/index.txt");
		appendLine("echo 01 > {destination}/keys/serial");
		appendLine("source ./vars");
		appendLine("export KEY_EXPIRE={server_valid_days}");
		appendLine("export KEY_COUNTRY=CZ");
		appendLine("export KEY_PROVINCE=CZ");
		appendLine("export KEY_CITY=");
		appendLine("export KEY_ORG={domain}");
		appendLine("export KEY_EMAIL=");
		appendLine("export KEY_CN={server_name}");
		appendLine("export KEY_NAME=");
		appendLine("export KEY_OU=");
		appendLine("export PKCS11_MODULE_PATH=");
		appendLine("export PKCS11_PIN=");
		appendLine("./generateserver.sh {subvpn_type} {server_name} {ca_name}");
		log.debug("JSON: " + json);
		exec(json);

		log.info("Executed");

		FileWork.readFileToJSON(json, destination + "/keys/" + server_name
				+ ".key", "key");
		FileWork.readFileToJSON(json, destination + "/keys/" + server_name
				+ ".crt", "cert");

		FileWork.deleteFile(destination + "/keys/" + server_name + ".key");
		FileWork.deleteFile(destination + "/keys/" + server_name + ".crt");
		FileWork.deleteFile(destination + "/keys/" + server_name + ".csr");

		log.info("Output files read");

		return json;
	}
}
