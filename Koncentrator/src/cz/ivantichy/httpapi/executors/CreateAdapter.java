package cz.ivantichy.httpapi.executors;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import archive.Create;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateAdapter implements PUTHandlerInterface {

	private Create creator = null;

	private static final Logger log = LogManager.getLogger(CreateAdapter.class
			.getName());

	public CreateAdapter(Create creator) {

		this.creator = creator;

	}

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {

		log.debug("PUT data: " + req.putdata);

		log.info("Going to handle PUT. Reading/parsing JSON.");
		JSONObject json = new JSONObject(req.putdata);

		if (json.getString("subvpn_type").equalsIgnoreCase(
				Static.TUN_BASIC_TYPE)) {

			try {

				Class<?> c = Class.forName(creator.getClass().getName());

				Method m = c.getDeclaredMethod("createForTunBasic",
						JSONObject.class);

				return new Response(m.invoke(c.newInstance(),
						new Object[] { json }).toString(), true);
			} catch (Exception e) {

				log.error(e.getCause().getMessage());

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);

				log.error(sw.toString());

				throw new IOException("Create handling failed.");
			}

		}

		if (json.getString("subvpn_type").equalsIgnoreCase(
				Static.TAP_ADVANCED_TYPE)) {
			return new Response(creator.createForTapAdvanced(json).toString(),
					true);
		}

		throw new IOException("subvpn_type not implemented");
	}
}