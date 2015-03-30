package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.koncentrator.simple.certgen.GenerateProfile;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.GETHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.server.Server;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class GenerateProfileAdapter implements GETHandlerInterface {
	private static final Logger log = LogManager.getLogger(Server.class
			.getName());

	@Override
	public Response handleGET(GETRequest req) throws IOException {

		try {

			String cn = req.getparams.get("common_name");
			String domain = req.getparams.get("domain");
			int days = Integer.valueOf(req.getparams.get("profile_valid_days"));
			String subvpn_name = req.getparams.get("subvpn_name");
			String subvpn_type = req.getparams.get("subvpn_type");

			log.info("Received request to generate profile: " + cn + " "
					+ domain + " " + days + " " + subvpn_name);

			if (cn.length() == 0) {
				throw new IOException("Common name is empty");
			}

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

			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(cn).matches()) {
				throw new IOException("Invalid character");
			}
			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(subvpn_type).matches()) {
				throw new IOException("Invalid character");
			}

			return new Response(GenerateProfile.generateProfile(cn, domain,
					days, subvpn_name, subvpn_type), true);

		} catch (Exception e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());

		}
	}
}
