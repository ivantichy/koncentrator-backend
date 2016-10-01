package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.CaCreate.CreateCa;

public class CreateCaAdapter implements PUTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(CreateCaAdapter.class.getName());

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {

		try {

			JSONObject json = new JSONObject(req.putdata);

			String domain = json.getString("domain");
			int days = json.getInt("ca_valid_days");
			String subvpn_name = json.getString("subvpn_name");
			String subvpn_type = json.getString("subvpn_type");

			log.info("Received request to create ca:  " + domain + " " + days
					+ " " + subvpn_name);

			if (days < 1) {
				throw new IOException("Invalid certificate validity period");
			}

			if (domain.length() == 0) {
				throw new IOException("Domain is empty");
			}

			if (subvpn_name.length() == 0) {
				throw new IOException("subvpn_name is empty");
			}
			if (subvpn_type.length() == 0) {
				throw new IOException("subvpn_type is empty");
			}

			if (!Static.STRING_TYPE_CHECK.matcher(domain).matches()) {
				throw new IOException("Invalid character");
			}

			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(subvpn_name).matches()) {
				throw new IOException("Invalid character");
			}

			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(subvpn_type).matches()) {
				throw new IOException("Invalid character");

			}

			if (subvpn_type.equalsIgnoreCase(Static.TUN_BASIC_TYPE)) {
				return new Response(CreateCa.createCaTunBasic(json), true);

			}
			if (subvpn_type.equalsIgnoreCase(Static.TAP_ADVANCED_TYPE)) {
				return new Response(CreateCa.createCaTapAdvanced(json), true);
			}

			throw new IOException("unsuported subvpn_type");

		} catch (Exception e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());

		}
	}
}
