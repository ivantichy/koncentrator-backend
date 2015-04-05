package cz.ivantichy.support.JSON.test;

import java.io.IOException;

import cz.ivantichy.fileutils.*;

import org.json.*;
import org.testng.annotations.Test;

public class JSONAddParameter {

	@Test()
	public static void main(String[] args) throws JSONException, IOException {

		JSONObject json = new JSONObject(FileWork.readFile(args[0]));
		json.put(args[1], args[2]);

		FileWork.saveFile(args[0], json.toString());

	}
}
