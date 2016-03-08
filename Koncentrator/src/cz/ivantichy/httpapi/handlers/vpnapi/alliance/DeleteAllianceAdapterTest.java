package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;

public class DeleteAllianceAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		DeleteAllianceAdapter daa = new DeleteAllianceAdapter();

		JSONObject json = new JSONObject(FileWork.readFile(args[0]));

		HashMap<String, String> getparams = new HashMap<String, String>();
		for (Iterator<String> iterator = json.keys(); iterator.hasNext();) {
			String key = iterator.next();
			getparams.put(key, json.getString(key));

		}

		FileWork.saveFile(
				args[0],
				daa.handleDELETE(new DELETERequest(getparams, null, null, null))
						.toString());

	}
}
