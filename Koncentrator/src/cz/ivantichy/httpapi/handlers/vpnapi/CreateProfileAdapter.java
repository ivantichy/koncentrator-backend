package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.vpnapi.CreateProfile;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateProfileAdapter extends CommandExecutor implements
		PUTHandlerInterface {

	private static final Logger log = LogManager
			.getLogger(CreateProfileAdapter.class.getName());

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {
		clear();

		log.debug("PUT data: " + req.putdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.putdata);

		// TODO - validate inputs

		if (json.getString("subvpn_type").equalsIgnoreCase(
				Static.TUN_BASIC_TYPE)) {

			return new Response(CreateProfile.createProfileTunBasic(json)
					.toString(), true);

		}
		if (json.getString("subvpn_type").equalsIgnoreCase(
				Static.TAP_ADVANCED_TYPE)) {
			return new Response(CreateProfile.createProfileTapAdvanced(json)
					.toString(), true);

		}

		throw new IOException("subvpn_type not implemented");
	}

}
