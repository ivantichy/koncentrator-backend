package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.koncentrator.simple.certgen.GenerateServer;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.GETHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.server.Server;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class GenerateServerAdapter implements GETHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(GenerateServerAdapter.class.getName());

	@Override
	public Response handleGET(GETRequest req) throws IOException {

		try {

			String cn = req.getparams.get("common_name");
			String domain = req.getparams.get("domain");
			int days = Integer.valueOf(req.getparams.get("valid_days"));
			String subvpn_name = req.getparams.get("subvpn_name");
			String subvpn_type = req.getparams.get("subvpn_type");

			log.info("Receiver request to generate server: " + cn + " "
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

			return new Response(GenerateServer.generateServer(subvpn_type,
					subvpn_name, domain, days, cn), true);

		} catch (Exception e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());

		}
	}
}
