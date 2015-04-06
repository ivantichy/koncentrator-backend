package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.koncentrator.simple.certgen.CommandExecutor;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateSubVPNAdapter extends CommandExecutor implements
		PUTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(CreateSubVPNAdapter.class.getName());

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {

		log.debug("PUT data: " + req.putdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.putdata);
		json.put(
				"ip_range",
				IPMaskConverter.maskToRange(json.getString("ip_server"),
						json.getString("ip_mask")));

		String source = Static.OPENVPNLOCATION + Static.GENERATEFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR;
		log.info("Source location:" + source);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);
		
		FileWork.checkFolder(source, destination);
		FileWork.copyFolder(source, destination);

		appendLine("set -ex \n");
		appendLine("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n");
		appendLine("./createsubvpn.sh {subvpn_name} {subvpn_type} {ip_range}\n");
		exec(json);

		json.put("destination", destination.replaceAll("//", "/"));
		json.put("source", source.replaceAll("//", "/"));

		storeJSON(json, destination + slash + json.getString("subvpn_name")
				+ ".json");
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return new Response(json.toString(), true);
	}

}
