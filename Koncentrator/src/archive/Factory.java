package archive;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.koncentrator_v2.api.common.interfaces.TypedInterface;

public class Factory {

	private static final Logger log = LogManager.getLogger(Factory.class
			.getName());

	public static class FactoryCache<U> {

		private class CacheStorage<X extends U> {

			private HashMap<String, Cache<? extends X>> cachestorage = new HashMap<>();

			private <Y extends X> void put(String type, Class<Y> key,
					Class<Y> value) {

				Cache<Y> cache = getCache(type);

				if (cache == null) {
					createNewCache(key, value, type);
				} else {

					cache.put(key, value);
				}

			}

			private <Y extends X> void createNewCache(Class<Y> key,
					Class<Y> value, String type) {

				Cache<Y> r = new Cache<>();

				r.put(key, value);

				cachestorage.put(type, r);

			}

			private <Y extends X> Class<Y> get(String type, Class<Y> key) {

				Cache<Y> cache = getCache(type);

				if (cache == null)
					return null;

				return cache.get(key);
			}

			private <Y extends X> Cache<Y> getCache(String type) {

				@SuppressWarnings("unchecked")
				Cache<Y> cache = (FactoryCache<U>.CacheStorage<X>.Cache<Y>) cachestorage
						.get(type);

				return cache;

			}

			private class Cache<Z extends X> {

				private HashMap<Class<Z>, Class<Z>> internal = new HashMap<>();

				private void put(Class<Z> key, Class<Z> value) {

					internal.put(key, value);

				}

				private Class<Z> get(Class<Z> key) {
					return internal.get(key);
				}

			}
		}

		private CacheStorage<U> caches = new CacheStorage<>();

		public <V extends U> Class<V> get(Class<V> key, String type) {

			Class<V> result = caches.get(type, key);
			log.debug("Object found in cache " + result + " " + type);
			return result;

		};

		public <V extends U> void put(String type, Class<V> interfc,
				Class<V> impl) {

			caches.put(type, interfc, impl);
			log.debug("Object stored in cache " + interfc + " " + impl + " "
					+ type);

		}

	}

}
