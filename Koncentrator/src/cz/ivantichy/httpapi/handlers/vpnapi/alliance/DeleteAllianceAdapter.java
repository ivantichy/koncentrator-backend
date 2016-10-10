package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class DeleteAllianceAdapter extends CommandExecutor implements
		DELETEHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(DeleteAllianceAdapter.class.getName());

	@Override
	public Response handleDELETE(DELETERequest req) throws IOException {
		clear();

		log.debug("DELETE GET data: " + req.getparams);

		log.info("going to handle DELETE. Reading/parsing JSON.");

		@SuppressWarnings({ "unchecked", "rawtypes" })
		JSONObject json = new JSONObject((Map) req.getparams);
		log.debug("Parsed JSON: " + json);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type1") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name1") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		/*
		 * String destination2 = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
		 * + json.getString("subvpn_type2") + Static.FOLDERSEPARATOR +
		 * json.getString("subvpn_name2") + Static.FOLDERSEPARATOR;
		 * log.info("Destination2 location:" + destination2);
		 * 
		 * String cajsonfile = destination + slash +
		 * json.getString("subvpn_name1") + ".json";
		 * log.info("Reading CA/Server JSON: " + cajsonfile); JSONObject cajson
		 * = new JSONObject(FileWork.readFile(cajsonfile));
		 * log.debug("SA/Server JSON: " + cajson.toString());
		 * 
		 * String cajsonfile2 = destination2 + slash +
		 * json.getString("subvpn_name2") + ".json";
		 * log.info("Reading CA/Server 2 JSON: " + cajsonfile2); JSONObject
		 * cajson2 = new JSONObject(FileWork.readFile(cajsonfile2));
		 * log.debug("SA/Server 2 JSON: " + cajson2.toString());
		 */

		String alliancejsonpath = destination + slash + "alliances" + slash
				+ json.getString("subvpn_type2") + slash
				+ json.getString("subvpn_name2") + ".json";
		log.info("Reading Alliance JSON: " + alliancejsonpath);
		JSONObject alliancejson = new JSONObject(
				FileWork.readFile(alliancejsonpath));
		log.debug("Alliance JSON: " + alliancejson.toString());

		// json.merge(cajson);

		if (!alliancejson.keySet().contains("blocked")
				|| !alliancejson.getString("blocked").equalsIgnoreCase("y")) {
			throw new IOException("Alliance must be blocked to be deleted.");

		}

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		// # subvpn_name1 subvpn_type1 subvpn_name2 subvpn_type2 ip_range1
		// ip_range2
		appendLine("./deletealliance.sh {subvpn_name1} {subvpn_type1} {subvpn_name2} {subvpn_type2} {ip_range1} {ip_range2}");

		exec(alliancejson);

		FileWork.deleteFile(alliancejsonpath);

		log.info("JSON deleted");

		return new Response("", true);
	}

}
