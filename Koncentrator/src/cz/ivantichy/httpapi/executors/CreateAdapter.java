package cz.ivantichy.httpapi.executors;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

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

				Method[] ms = c.getDeclaredMethods();
				log.debug("Size " + ms.length);

				for (int i = 0; i < ms.length; i++) {
					log.debug("name " + ms[i].getName());
					log.debug("name " + ms[i].getParameters()[0].getType().getName());

				}

				m.invoke(null, new Object[] { json });
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				log.error(e.getStackTrace());
				throw new IOException("Create handling failed.");

			}
			return new Response(creator.createForTunBasic(json).toString(),
					true);

		}
		if (json.getString("subvpn_type").equalsIgnoreCase(
				Static.TAP_ADVANCED_TYPE)) {
			return new Response(creator.createForTapAdvanced(json).toString(),
					true);
		}

		throw new IOException("subvpn_type not implemented");
	}

}
