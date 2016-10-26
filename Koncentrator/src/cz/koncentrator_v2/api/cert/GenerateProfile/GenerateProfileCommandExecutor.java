package cz.koncentrator_v2.api.cert.GenerateProfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.PathConfigCERT_TUN_basic;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class GenerateProfileCommandExecutor extends CommandExecutor {
	private static final Logger log = LogManager
			.getLogger(GenerateProfileCommandExecutor.class.getName());

	public JSONObject runGenerateProfile(JSONObject json) throws Exception {

		log.info("GenerateProfileCommandExecutor.runGenerateProfile started");
		clear();

		String common_name = json.getString(Static.COMMON_NAME);

		log.debug("Going to read stored CA SON");
		JSONObject loadedcajson = FileWork
				.readJSON(PathConfigCERT_TUN_basic.instance.getCAJsonPath(json));

		json = loadedcajson.merge(json);

		String destination = json.getString("destination");

		FileWork.checkFolder(destination);

		appendLine("set -e");
		appendLine("cd {destination}");
		appendLine("> {destination}/keys/index.txt");
		// appendLine("touch {destination}/keys/index.txt");
		appendLine("echo 01 > {destination}/keys/serial");
		appendLine("source ./vars");
		appendLine("export KEY_EXPIRE={profile_valid_days}");
		appendLine("export KEY_COUNTRY=CZ");
		appendLine("export KEY_PROVINCE=CZ");
		appendLine("export KEY_CITY=");
		appendLine("export KEY_ORG={domain}");
		appendLine("export KEY_EMAIL=");
		appendLine("export KEY_CN={common_name}");
		appendLine("export KEY_NAME=");
		appendLine("export KEY_OU=");
		appendLine("export PKCS11_MODULE_PATH=");
		appendLine("export PKCS11_PIN=");
		appendLine("./pkitool {common_name} 2> ./output.err");
		log.debug("JSON: " + json);
		exec(json);

		FileWork.readFileToJSON(json, destination + "/keys/" + common_name
				+ ".key", "key");
		FileWork.readFileToJSON(json, destination + "/keys/" + common_name
				+ ".crt", "cert");
		FileWork.readFileToJSON(json, destination + "/keys/ca.crt", "ca");

		FileWork.readFileToJSON(json, destination + "/keys/ta.key", "ta");

		FileWork.deleteFile(destination + "/keys/" + common_name + ".key");
		FileWork.deleteFile(destination + "/keys/" + common_name + ".crt");
		FileWork.deleteFile(destination + "/keys/" + common_name + ".csr");
		FileWork.deleteFile(destination + "/keys/01.pem");
		log.info("Output files read");

		return json;

	}

}
