package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.koncentrator.simple.certgen.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class BlockProfileAdapter extends CommandExecutor implements
		POSTHandlerInterface {

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

		if (!!profilejson.keySet().contains("blocked")
				|| !profilejson.getString("blocked").equalsIgnoreCase("y")) {
			throw new IOException("Profile must be blocked to be deleted.");

		}
		appendLine("set -ex");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds");
		appendLine("./deleteprofile.sh {common_name} {subvpn_name}");
		exec(profilejson);

		FileWork.deleteFile(profilejsonfile);

		log.info("JSON deleted");

		return new Response(profilejson.toString(), true);

	}
}
