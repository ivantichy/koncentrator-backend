package cz.koncentrator_v2.api.common;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cz.koncentrator_v2.api.common.interfaces.TypedInterface;

public class Factory2 {

	private static final Logger log = LogManager.getLogger(Factory2.class
			.getName());

	abstract protected static class Cache<V extends TypedInterface> {

		protected Class<V> findClassForInterface(Class<V> interfc, String type)
				throws Exception {

			Reflections r = new Reflections("cz.koncentrator");

			Set<Class<? extends V>> all = r.getSubTypesOf(interfc);

			log.debug("Found " + all.size() + " implementation for interface "
					+ interfc.getName());

			boolean found = false;

			Class<? extends V> result = null;
			for (Class<? extends V> c : all) {

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

			@SuppressWarnings("unchecked")
			Class<V> finalresult = (Class<V>) result;

			if (found)
				return finalresult;

			log.error("No implementation found for interface " + interfc);

			throw new ClassNotFoundException(
					"No implementation found for interface " + interfc);

		}

		public Key<V> getKey(String type, Class<? extends V> interfc) {

			return new Key<V>(type, interfc);

		}

		protected static class Key<V> {

			public Class<? extends V> getInterfc() {
				return interfc;
			}

			public String getType() {
				return type;
			}

			private String type;
			private Class<? extends V> interfc;

			private Key(String type, Class<? extends V> interfc) {

				this.type = type;
				this.interfc = interfc;

			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				// result = prime * result + getOuterType().hashCode();
				result = prime * result
						+ ((interfc == null) ? 0 : interfc.hashCode());
				result = prime * result
						+ ((type == null) ? 0 : type.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				@SuppressWarnings("unchecked")
				Key<V> other = (Key<V>) obj;

				if (interfc == null) {
					if (other.interfc != null)
						return false;
				} else if (!interfc.equals(other.interfc))
					return false;
				if (type == null) {
					if (other.type != null)
						return false;
				} else if (!type.equals(other.type))
					return false;
				return true;
			}

		}

		protected abstract Class<? extends V> getValue(Key<? extends V> key)
				throws Exception;

		private LoadingCache<Key<? extends V>, Class<? extends V>> cache = CacheBuilder
				.newBuilder().softValues()
				.build(new CacheLoader<Key<? extends V>, Class<? extends V>>() {
					public Class<? extends V> load(Key<? extends V> key)
							throws Exception {
						return getValue(key);
					}
				});

		public <Y extends V> Class<Y> get(Class<Y> key, String type)
				throws ExecutionException {

			@SuppressWarnings("unchecked")
			Class<Y> result = (Class<Y>) cache.get(getKey(type, key));
			log.debug("Object found in cache " + result + " " + type);
			return result;

		};

		public <Y extends V> void put(String type, Class<Y> interfc,
				Class<Y> impl) {

			cache.put(getKey(type, interfc), impl);
			log.debug("Object stored in cache " + interfc + " " + impl + " "
					+ type);

		}
	}

}
