package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class BlockProfileAdapter extends CommandExecutor implements
		POSTHandlerInterface {
	private static final Logger log = LogManager.getLogger(BlockProfileAdapter.class
			.getName());


	@Override
	public Response handlePOST(POSTRequest req) throws IOException {
		clear();

		log.debug("POST data: " + req.postdata);

		JSONObject json = new JSONObject(req.postdata);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String profilejsonfile = destination + slash + "profiles" + slash
				+ json.getString("common_name") + "_profile.json";

		log.info("About to load existing profile JSON:" + profilejsonfile);

		JSONObject profilejson = new JSONObject(
				FileWork.readFile(profilejsonfile));

		log.debug("Profile JSON: " + profilejson.toString());

		if (profilejson.keySet().contains("blocked")) {
			if (profilejson.getString("blocked").equalsIgnoreCase("y"))
				throw new IOException("Profile allready blocked.");

		}
		profilejson.put("blocked", "y");
		appendLine("set -ex");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds");
		appendLine("./blockprofile.sh {common_name} {subvpn_name} {subvpn_type}");
		exec(profilejson);

		FileWork.storeJSON(profilejson, profilejsonfile);

		log.info("JSON updated");

		return new Response(profilejson.toString(), true);

	}
}
