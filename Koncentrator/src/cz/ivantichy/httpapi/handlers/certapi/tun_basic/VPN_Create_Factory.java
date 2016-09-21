package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class VPN_Create_Factory {

	private static final Logger log = LogManager
			.getLogger(VPN_Create_Factory.class.getName());

	/**
	 * Factory to get Implementation of create of VPN API
	 * 
	 * @param json
	 *            Json must contain field subvpn_type in root to specify
	 *            implementation of Create interface implementation
	 * @return An implementation of Create interface based on subvpn_type
	 * @throws IOException
	 */
	public Create getCreateImpl(JSONObject json) throws IOException {

		if (json.getString(Static.SUBVPN_TYPE).equalsIgnoreCase(
				Static.TUN_BASIC_TYPE)) {

			try {

				Class<?> c = Class.forName("Create_CA_TUN_basicImpl");

				return (Create) c.newInstance();

			} catch (Exception e) {

				log.error(e.getCause().getMessage());
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				log.error(sw.toString());

				throw new IOException("Create handling failed.");

			}

		}

		throw new IOException("subvpn_type not implemented: "
				+ json.getString(Static.SUBVPN_TYPE));

	}

}
