package cz.koncentrator_v2.api.common;

import java.io.IOException;

import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;

public class CopyFolder {

	public static JSONObject copy(JSONObject json, String source,
			String destination) throws IOException {

		// must not exist
		FileWork.checkFolder(source, destination);

		FileWork.copyFolder(source, destination);

		json.put("source", FileWork.replaceDoubleSlashes(source));
		json.put("destination", FileWork.replaceDoubleSlashes(destination));

		return json;

	}

	public static JSONObject copy(JSONObject json) throws IOException {

		String source = json.getString("source");
		String destination = json.getString("destination");

		return copy(json, source, destination);

	}

}
