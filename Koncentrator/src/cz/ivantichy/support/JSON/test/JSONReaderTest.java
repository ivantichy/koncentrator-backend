package cz.ivantichy.support.JSON.test;

import java.io.IOException;

import org.json.JSONException;

import cz.ivantichy.support.JSON.JSONReader;

public class JSONReaderTest {

	public static void main(String[] args) {

		try {
			System.out.println(JSONReader.read(args[0], args[1]));
		} catch (JSONException | IOException e) {

			e.printStackTrace();
			System.exit(1);

		}
	}
}
