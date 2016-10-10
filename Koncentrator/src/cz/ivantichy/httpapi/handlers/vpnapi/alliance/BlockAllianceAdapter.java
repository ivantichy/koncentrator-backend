package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class BlockAllianceAdapter extends CommandExecutor implements
		POSTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(BlockAllianceAdapter.class.getName());

	@Override
	public Response handlePOST(POSTRequest req) throws IOException {
		clear();

		log.debug("POST data: " + req.postdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.postdata);

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
		if (alliancejson.keySet().contains("blocked")) {
			if (alliancejson.getString("blocked").equalsIgnoreCase("y"))
				throw new IOException("Alliance allready blocked.");

		}

		// json.merge(cajson);
		alliancejson.put("blocked", "y");

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		// # subvpn_name1 subvpn_type1 subvpn_name2 subvpn_type2 ip_range1
		// ip_range2
		appendLine("./blockalliance.sh {subvpn_name1} {subvpn_type1} {subvpn_name2} {subvpn_type2} {ip_range1} {ip_range2}");

		exec(alliancejson);

		FileWork.storeJSON(alliancejson, alliancejsonpath);

		log.info("JSON stored");
		log.debug("Updated alliance JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

}
