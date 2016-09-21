package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class DeleteSubVPNAdapter extends CommandExecutor implements
		DELETEHandlerInterface {
	private static final Logger log = LogManager.getLogger(DeleteSubVPNAdapter.class
			.getName());


	@Override
	public Response handleDELETE(DELETERequest req) throws IOException {

		clear();

		log.debug("DELETE GET data: " + req.getparams);

		@SuppressWarnings({ "unchecked", "rawtypes" })
		JSONObject json = new JSONObject((Map) req.getparams);
		log.debug("Parsed JSON: " + json);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;

		log.info("Destination location:" + destination);

		String cajsonfile = destination + slash + json.getString("subvpn_name")
				+ ".json";
		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.debug("CA JSON: " + cajson.toString());

		if (!cajson.keySet().contains("blocked")
				|| !cajson.getString("blocked").equalsIgnoreCase("y")) {
			throw new IOException("Alliance must be blocked to be deleted.");

		}

		appendLine("set -ex");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds");
		appendLine("./deletesubvpn.sh {subvpn_name} {subvpn_type} {ip_range}");
		exec(cajson);

		FileWork.deleteFile(cajsonfile);

		log.info("JSON deleted");

		return new Response("", true);

	}

}
