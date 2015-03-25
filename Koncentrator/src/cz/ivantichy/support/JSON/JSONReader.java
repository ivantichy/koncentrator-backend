package cz.ivantichy.support.JSON;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.*;

import java.util.Base64;

public class JSONReader {

	public static String read(String profile, String name)
			throws JSONException, IOException {

		JSONObject obj = new JSONObject(FileUtils.readFileToString(
				new File(profile)).replaceAll("\n|\r", ""));

		return new String(new String(Base64.getDecoder().decode(
				obj.get(name).toString())));

	}
}
