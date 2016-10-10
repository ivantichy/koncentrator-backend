package cz.koncentrator_v2.api.cert.CreateCa.Impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.cert.CreateCa.CreateCaCommandExecutor;
import cz.koncentrator_v2.api.cert.CreateCa.CreateCaInterface;
import cz.koncentrator_v2.api.common.CopyFolder;
import cz.koncentrator_v2.api.common.PathConfigCERT_TUN_basic;

public class CreateCaCERT_TUN_basicImpl implements CreateCaInterface {

	private static Logger log = LogManager
			.getLogger(CreateCaCERT_TUN_basicImpl.class.getName());;

	/**
	 * Responsible for creation of CA folder on CERT API
	 * 
	 */
	@Override
	public JSONObject create(JSONObject json) throws Exception {

		log.debug("Going to create folder for CA on CERT API.");

		String source = PathConfigCERT_TUN_basic.instance.getSourcePath(json);
		String destination = PathConfigCERT_TUN_basic.instance.getDestinationPath(json);

		json = CopyFolder.copy(json, source, destination);

		log.debug("Copied.");

		CreateCaCommandExecutor ce = new CreateCaCommandExecutor();

		json = ce.runCAgeneration(json);

		log.debug("Storign JSON.");
		FileWork.storeJSON(json, PathConfigCERT_TUN_basic.instance.getCAJsonPath(json));

		return json;
	}

	@Override
	public String implementedType() {
		return Static.TUN_BASIC_TYPE;
	}

}
