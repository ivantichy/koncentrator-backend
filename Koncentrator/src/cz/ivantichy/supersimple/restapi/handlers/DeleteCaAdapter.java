package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.koncentrator.simple.certgen.DeleteCa;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.server.Server;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class DeleteCaAdapter implements DELETEHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(DeleteCaAdapter.class.getName());

	@Override
	public Response handleDELETE(DELETERequest req) throws IOException {

		try {

			String subvpn_name = req.getparams.get("subvpn_name");
			String subvpn_type = req.getparams.get("subvpn_type");

			log.info("Receiver request to delete ca: " + subvpn_type + " "
					+ subvpn_name);

			if (subvpn_name.length() == 0) {
				throw new IOException("subvpn_name is empty");
			}
			if (subvpn_type.length() == 0) {
				throw new IOException("subvpn_type is empty");
			}

			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(subvpn_name).matches()) {
				throw new IOException("Invalid character");
			}

			if (!Static.SAFE_STRING_TYPE_CHECK.matcher(subvpn_type).matches()) {
				throw new IOException("Invalid character");
			}

			return new Response(DeleteCa.deleteCa(subvpn_type, subvpn_name),
					true);

		} catch (Exception e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());

		}
	}
}
