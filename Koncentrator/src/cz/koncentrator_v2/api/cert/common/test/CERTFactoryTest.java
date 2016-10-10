package cz.koncentrator_v2.api.cert.common.test;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.reflections.Reflections;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.common.CERTFactory;

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

		CERTFactory.getInstanceForGenerateServer(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

		// test of caching
		CERTFactory.getInstanceForGenerateServer(new JSONObject().put(
				Static.SUBVPN_TYPE, Static.TUN_BASIC_TYPE));

	}

}
