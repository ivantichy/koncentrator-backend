package cz.ivantichy.support.JSON.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;

public class JSONAddParameter {

	@Test()
	public static void main(String[] args) throws JSONException, IOException {

		JSONObject json = new JSONObject(FileWork.readFile(args[0]));
		json.put(args[1], args[2]);

		FileWork.saveFile(args[0], json.toString());

	}
}
