package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.executors.CommandExecutor;
import cz.ivantichy.httpapi.executors.Create;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class Create_CA_TUN_basicImpl extends CommandExecutor implements Create {
	private static Logger log = LogManager
			.getLogger(Create_CA_TUN_basicImpl.class.getName());;

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am Create_CA_TUN_basicImpl");

		clear();

		String destination = PathConfigTUN_basic.getDestionationPath(json);
		log.info("Destination location:" + destination);
		FileWork.folderExists(destination);

		String cajsonfile = PathConfigTUN_basic.getCAJsonPath(json);
		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.info("CA JSON: " + cajson.toString());

		json = cajson.merge(json);
		cajson = null;

		json = IPMaskConverter.addIPRangeOrMask(json);

		String config = FileWork.readFile(PathConfigTUN_basic
				.getConfigTemplatePath(json));

		log.info("Config read: " + config);
		log.debug("Going to fill config templace");

		config = replaceAllFields(json, config);

		String destconfigpath = PathConfigTUN_basic.getConfigInstancePath(json);

		log.info("Destination config file path: " + destconfigpath);
		FileWork.saveFile(destconfigpath, config);
		log.debug("Config file written: \n" + config);

		appendLine("set -ex \n");
		appendLine(PathConfigTUN_basic.getCMDspath(json) + "\n");
		// appendLine("./createsubvpn.sh {subvpn_name} {subvpn_type} {ip_range}\n");

		exec(json);
		json.put("destination", destination);
		json.put("server_conf_base64", B64.encode(config));

		FileWork.storeJSON(json, PathConfigTUN_basic.getCAJsonPath(json));
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		return json;

	}

	@Override
	public JSONObject createForTunBasic(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject createForTapAdvanced(JSONObject json) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
