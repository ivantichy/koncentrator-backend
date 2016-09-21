package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateAllianceAdapter extends CommandExecutor implements
		PUTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(CreateAllianceAdapter.class.getName());

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {
		clear();

		log.debug("PUT data: " + req.putdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.putdata);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type1") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name1") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String destination2 = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type2") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name2") + Static.FOLDERSEPARATOR;
		log.info("Destination2 location:" + destination2);

		String cajsonfile = destination + slash
				+ json.getString("subvpn_name1") + ".json";
		log.info("Reading CA/Server JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("SA/Server JSON: " + cajson.toString());

		String cajsonfile2 = destination2 + slash
				+ json.getString("subvpn_name2") + ".json";
		log.info("Reading CA/Server 2 JSON: " + cajsonfile2);
		JSONObject cajson2 = new JSONObject(FileWork.readFile(cajsonfile2));
		log.debug("SA/Server 2 JSON: " + cajson2.toString());

		// json.merge(cajson);

		json.put("ip_range1", cajson.getString("ip_range"));
		json.put("ip_range2", cajson2.getString("ip_range"));

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		// # subvpn_name1 subvpn_type1 subvpn_name2 subvpn_type2 ip_range1
		// ip_range2
		appendLine("./createalliance.sh {subvpn_name1} {subvpn_type1} {subvpn_name2} {subvpn_type2} {ip_range1} {ip_range2}");

		exec(json);

		FileWork.storeJSON(
				json,
				destination + slash + "alliances" + slash
						+ json.getString("subvpn_type2") + slash
						+ json.getString("subvpn_name2") + ".json");

		log.info("JSON stored");
		log.debug("Stored alliance JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

}
