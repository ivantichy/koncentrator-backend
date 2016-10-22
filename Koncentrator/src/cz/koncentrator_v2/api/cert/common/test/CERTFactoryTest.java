package cz.koncentrator_v2.api.cert.common.test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.cache.LoadingCache;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.common.CERTFactory;
import cz.koncentrator_v2.api.cert.common.CERTFactory.FactoryCache;
import cz.koncentrator_v2.api.common.interfaces.*;

public class CERTFactoryTest implements CERTFactoryTestInterface {

	@Test(priority = 1)
	public static void test() {

		Reflections r = new Reflections("cz.koncentrator");
		Set<Class<? extends CERTFactoryTestInterface>> all = r
				.getSubTypesOf(CERTFactoryTestInterface.class);

		Assert.assertEquals(all.size(), 1);

		Assert.assertEquals(all.iterator().next().getName(),
				CERTFactoryTest.class.getName());

	}

	@Test(priority = 1)
	public static void test2() throws JSONException, Exception {

		CERTFactory.getInstanceForCreateCa(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

		// test of caching
		CERTFactory.getInstanceForCreateCa(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

	}

	@Test(priority = 1)
	public static void test3() throws JSONException, Exception {

		Field f = Class.forName(
				"cz.koncentrator_v2.api.cert.common.CERTFactory")
				.getDeclaredField("internalcache");
		f.setAccessible(true);

		@SuppressWarnings("unchecked")
		FactoryCache<TypedInterface> i = (FactoryCache<TypedInterface>) f
				.get(null);

		f = i.getClass().getSuperclass().getDeclaredField("cache");
		f.setAccessible(true);

		@SuppressWarnings("rawtypes")
		LoadingCache l = (LoadingCache) f.get(i);

		@SuppressWarnings("rawtypes")
		Map m = l.asMap();

		Assert.assertNull(m.get(i
				.getKey(Static.TUN_BASIC_TYPE,
						cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerInterface.class)));

		CERTFactory.getInstanceForGenerateServer(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

		m = l.asMap();

		Assert.assertNotNull(m.get(i
				.getKey(Static.TUN_BASIC_TYPE,
						cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerInterface.class)));

		// test of caching
		CERTFactory.getInstanceForGenerateServer(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

	}
}
