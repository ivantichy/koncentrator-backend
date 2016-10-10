package cz.ivantichy.koncentrator.tunbasic;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import archive.Create;
import cz.ivantichy.base64.B64;
import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.handlers.certapi.tun_basic.ConfigUpdaterTUNBasic;
import cz.ivantichy.httpapi.handlers.certapi.tun_basic.ServiceRestarter;
import cz.ivantichy.koncentrator.simple.IPUtils.IPMaskConverter;
import cz.koncentrator_v2.api.common.PathConfigVPN_TUN_basic;

public class Create_CA_TUN_basicImpl implements Create {
	private static Logger log = LogManager
			.getLogger(Create_CA_TUN_basicImpl.class.getName());;

	@Override
	public JSONObject create(JSONObject json) throws IOException {

		log.info("Hi, I am Create_CA_TUN_basicImpl");

		String destination = PathConfigVPN_TUN_basic.instance
				.getDestinationPath(json);
		log.info("Destination location:" + destination);
		FileWork.folderExists(destination);

		String cajsonfile = PathConfigVPN_TUN_basic.instance
				.getCAJsonPath(json);
		log.info("Reading CA JSON: " + cajsonfile);
		JSONObject cajson = new JSONObject(FileWork.readFile(cajsonfile));
		log.info("CA JSON: " + cajson.toString());

		json = cajson.merge(json);
		cajson = null;

		json = IPMaskConverter.addIPRangeOrMask(json);

		String config = ConfigUpdaterTUNBasic.updateAndSaveConfig(json);

		json.put("destination", destination);
		json.put("server_conf_base64", B64.encode(config));

		FileWork.storeJSON(json,
				PathConfigVPN_TUN_basic.instance.getCAJsonPath(json));
		log.info("JSON stored");
		log.debug("Stored JSON: " + json.toString());

		ServiceRestarter.restartAllProcesses(json);
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