package cz.koncentrator_v2.api.cert.GenerateServer.Impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.CreateCa.CreateCaCommandExecutor;
import cz.koncentrator_v2.api.cert.CreateCa.Impls.CreateCaCERT_TUN_basicImpl;
import cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerCommandExecutor;
import cz.koncentrator_v2.api.cert.GenerateServer.GenerateServerInterface;
import cz.koncentrator_v2.api.common.CopyFolder;
import cz.koncentrator_v2.api.common.PathConfigCERT_TUN_basic;

public class GenerateServerCERT_TUN_basicImpl implements
		GenerateServerInterface {

	private static Logger log = LogManager
			.getLogger(GenerateServerCERT_TUN_basicImpl.class.getName());;

	public JSONObject generate(JSONObject json) throws Exception {

		log.debug("Going to generate server certs on CERT API.");

		GenerateServerCommandExecutor ce = new GenerateServerCommandExecutor();

		json = ce.runGenerateServer(json);

		log.debug("Storign JSON.");
		FileWork.storeJSON(json,
				PathConfigCERT_TUN_basic.instance.getServerJsonPath(json));

		return json;
	}

	@Override
	public String implementedType() {
		return Static.TUN_BASIC_TYPE;
	}

}
