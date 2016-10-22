package cz.koncentrator_v2.api.cert.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.CreateCa.CreateCaInterface;
import cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerInterface;
import cz.koncentrator_v2.api.common.Factory2;
import cz.koncentrator_v2.api.common.interfaces.Generate;
import cz.koncentrator_v2.api.common.interfaces.TypedInterface;

public class CERTFactory extends Factory2 {

	private static final Logger log = LogManager.getLogger(CERTFactory.class
			.getName());

	private static FactoryCache<TypedInterface> internalcache = new CERTFactory.FactoryCache<>();

	public static class FactoryCache<T extends TypedInterface> extends Cache<T> {

		@SuppressWarnings("unchecked")
		protected Class<? extends T> getValue(Key<? extends T> key)
				throws Exception {

			log.debug("Calling findClassForInterface");
			return findClassForInterface((Class<T>) key.getInterfc(),
					key.getType());
		}

	}

	public static <T extends TypedInterface> Class<T> getClassForInterface(
			Class<T> interfc, String type) throws Exception {

		return internalcache.get(interfc, type);

	}

	public static CreateCaInterface getInstanceForCreateCa(JSONObject json)
			throws Exception {

		return getClassForInterface(CreateCaInterface.class,
				json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

	public static GenerateServerInterface getInstanceForGenerateServer(
			JSONObject json) throws Exception {

		return getClassForInterface(GenerateServerInterface.class,
				json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

	public static Generate getInstanceForGenerateProfile(JSONObject json)
			throws Exception {

		return null; // getClassForInterface(CreateCaInterface.class,
						// json.getString(Static.SUBVPN_TYPE)).newInstance();

	}

}
