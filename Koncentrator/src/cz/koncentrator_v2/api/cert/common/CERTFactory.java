package cz.koncentrator_v2.api.cert.common;

import java.lang.reflect.Method;
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

	public static class CacheRecord<T> extends HashMap<Class<T>, Class<T>> {

		private static final long serialVersionUID = 1L;

	}

	public static class CacheObject<T extends CacheRecord<? extends TypedInterface>> {

		private HashMap<String, T> internal = new HashMap<>();

		public T put(String type, T record) {

			return internal.put(type, record);

		}

		public T get(String type) {
			return internal.get(type);
		}

	}

	public static class InternalCache {

		private CacheObject<CacheRecord<? extends TypedInterface>> caches = new CacheObject<>();

		// private static HashMap<String, CacheRecord<? extends TypedInterface>>
		// caches2 = new HashMap<>();

		private <T extends TypedInterface> void createNewCache(Class<T> key,
				Class<T> value, String type) {

			CacheRecord<T> r = new CacheRecord<>();

			r.put(key, value);

			caches.put(type, r);

		}

		public <T extends TypedInterface> Class<T> get(Class<T> key, String type) {

			@SuppressWarnings("unchecked")
			CacheRecord<T> cache = (CacheRecord<T>) caches.get(type);

			Class<T> record = cache.get(key);

			HashSet<? super Long> xxx = new HashSet<Comparable<Long>>();

			return record;

		};

	}

	private static final Logger log = LogManager.getLogger(CERTFactory.class
			.getName());
	private static InternalCache internalcache = new CERTFactory.InternalCache();

	private static HashMap<Class<? extends TypedInterface>, Class<? extends TypedInterface>> neco = new HashMap<>();

	private static <T extends TypedInterface> Class<T> findClassForInterface(
			Class<T> interfc, String type) throws Exception {

		// HashMap<Class<T>, Class<? extends T>> internalcache = new
		// HashMap<>();// CERTFactory.Test<>();

		// CERTFactory.InternalCache<T> internalcache = new
		// CERTFactory.InternalCache<>();

		synchronized (internalcache) {

			// TODO neuklada se to oddelene pro ruzne typy, prepisuje se to, tj.
			// dodelat test

			// TODO softreference

			// internalcache.add(interfc, interfc);

			log.debug("Going to find implementation of " + interfc.getName());

			Class<? extends T> local = internalcache.get(interfc, type);

			if (local != null) {
				log.debug("This was found in internal cache: "
						+ local.getName());

				if (local.isAssignableFrom(interfc)) {
					return (Class<T>) local;
				}

			}

			Reflections r = new Reflections("cz.koncentrator");

			Set<Class<? extends T>> all = r.getSubTypesOf(interfc);

			neco.put(interfc, all.iterator().next());

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
				return (Class<T>) result;

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
