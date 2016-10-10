package cz.koncentrator_v2.api.cert.CreateCa;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class CreateCaCommandExecutor extends CommandExecutor {

	private static final Logger log = LogManager
			.getLogger(CreateCaCommandExecutor.class.getName());

	public JSONObject runCAgeneration(JSONObject json) throws IOException {

		log.info("CreateCa.runCAgeneration started");
		clear();

		// String subvpn_type = json.getString(Static.SUBVPN_TYPE);
		String ca_name = json.getString(Static.CA_NAME);
		String destination = json.getString("destination");

		log.info("CreateCa json data parsed");

		appendLine("set -e");
		appendLine("cd {destination}");
		appendLine("source ./vars");
		appendLine("export KEY_EXPIRE={ca_valid_days}");
		appendLine("export KEY_COUNTRY=CZ");
		appendLine("export KEY_PROVINCE=CZ");
		appendLine("export KEY_CITY=");
		appendLine("export KEY_ORG={domain}");
		appendLine("export KEY_EMAIL=");
		appendLine("export KEY_CN={ca_name}");
		appendLine("export KEY_NAME=");
		appendLine("export KEY_OU=");
		appendLine("export PKCS11_MODULE_PATH=");
		appendLine("export PKCS11_PIN=");
		appendLine("./createca.sh {subvpn_type} {ca_name}");
		log.debug("JSON: " + json);
		exec(json);

		log.info("Executed");

		FileWork.readFileToJSON(json, destination + "/keys/ca.crt", "ca");
		FileWork.readFileToJSON(json, destination + "/keys/ta.key", "ta");
		FileWork.readFileToJSON(json, destination + "/keys/dh2048.pem", "dh");

		log.info("Output files read");
	
		return json;

	}
}
