package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class BlockSubVPNAdapter extends CommandExecutor implements
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

		String cajsonfile = destination + slash + json.getString("subvpn_name")
				+ ".json";
		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("CA JSON: " + cajson.toString());

		if (cajson.keySet().contains("blocked")) {
			if (cajson.getString("blocked").equalsIgnoreCase("y"))
				throw new IOException("SUBVPN allready blocked.");

		}
		cajson.put("blocked", "y");
		appendLine("set -ex");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds");
		appendLine("./blocksubvpn.sh {subvpn_name} {subvpn_type} {ip_range}");
		exec(cajson);

		storeJSON(cajson, cajsonfile);

		log.info("JSON updated");

		return new Response(cajson.toString(), true);

	}
}
