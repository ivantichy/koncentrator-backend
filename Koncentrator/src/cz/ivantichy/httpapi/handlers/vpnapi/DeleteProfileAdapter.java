package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.koncentrator.simple.certgen.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class DeleteProfileAdapter extends CommandExecutor implements
		DELETEHandlerInterface {

	@Override
	public Response handleDELETE(DELETERequest req) throws IOException {

		clear();

		log.debug("DELETE GET data: " + req.getparams);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ req.getparams.get("subvpn_type") + Static.FOLDERSEPARATOR
				+ req.getparams.get("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		String profilejsonfile = destination + slash + "profiles" + slash
				+ req.getparams.get("common_name") + "_profile.json";
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
