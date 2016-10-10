package cz.koncentrator_v2.api.cert.common;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.reflections.Reflections;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.CreateCa.CreateCaInterface;
import cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerInterface;
import cz.koncentrator_v2.api.common.Factory;
import cz.koncentrator_v2.api.common.interfaces.Create;
import cz.koncentrator_v2.api.common.interfaces.Generate;
import cz.koncentrator_v2.api.common.interfaces.TypedInterface;

public class CERTFactory extends Factory {

	public static class InternalCache<K> {

		private  HashMap<String, HashMap<Class<K>, Class<? extends K>>> caches = new HashMap<>();

		public void put(Class<K> key, String type, Class<? extends K> value) {
			
			
			HashMap<Class<K>, Class <? extends K>> local = new HashMap<>();
			local.put(key, value);
			caches.put(type, local);

		}

		public Class<? extends K> get(Class<K> key, String type) {

			
			HashMap<Class<K>, Class <? extends K>> local = caches.get(type);
			return null;
		}

	}

	private static final Logger log = LogManager.getLogger(CERTFactory.class
			.getName());

	private static <T extends TypedInterface> Class<? extends T> findClassForInterface(
			Class<T> interfc, String type) throws Exception {

		// HashMap<Class<T>, Class<? extends T>> internalcache = new
		// HashMap<>();// CERTFactory.Test<>();

		CERTFactory.InternalCache<T> internalcache = new CERTFactory.InternalCache<>();

		synchronized (internalcache) {

			// TODO neuklada se to oddelene pro ruzne typy, prepisuje se to, tj.
			// dodelat test
			
			//TODO softreference

			// internalcache.add(interfc, interfc);

			log.debug("Going to find implementation of " + interfc.getName());

			Class<? extends T> local = internalcache.get(interfc, type);

			if (local != null) {
				log.debug("This was found in internal cache: "
						+ local.getName());

				return local;

			}

			Reflections r = new Reflections("cz.koncentrator");

			Set<Class<? extends T>> all = r.getSubTypesOf(interfc);

			// TODO genericke typy v return a filtery a scannery

			log.debug("Found " + all.size() + " implementation for interface "
					+ interfc.getName());

			boolean found = false;
			Class<? extends T> result = null;
			for (Class<? extends T> c : all) {

				if (Modifier.isAbstract(c.getModifiers()) || c.isInterface()) {

					log.debug("Skipping " + c.getName());
					continue;

				}

				String localtype = c.newInstance().implementedType();

				if (type.equalsIgnoreCase(localtype)) {

					if (found) {
						log.error("Multiple implementations found for interface "
								+ interfc
								+ " "
								+ result.getName()
								+ " and "
								+ c.getName());

						throw new IllegalStateException(
								"Multiple implementations found for interface "
										+ interfc + " " + result.getName()
										+ " and " + c.getName());

					}
					log.debug("Implementation " + c.getName()
							+ " found for interface " + interfc);

					result = c;
					found = true;
				}

			}

			internalcache.put(interfc, type, result);
			if (found)
				return result;

			log.error("No implementation found for interface " + interfc);

			throw new ClassNotFoundException(
					"No implementation found for interface " + interfc);

		}
	}

	public static CreateCaInterface getInstanceForCreateCa(JSONObject json)
			throws Exception {

		return findClassForInterface(CreateCaInterface.class,
				json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

	public static GenerateServerInterface getInstanceForGenerateServer(
			JSONObject json) throws Exception {

		return findClassForInterface(GenerateServerInterface.class,
				json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

	public static Generate getInstanceForGenerateProfile(JSONObject json)
			throws Exception {

		return null; // findClassForInterface(CreateCaInterface.class,
						// json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

}
