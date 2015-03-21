package cz.ivantichy.support.JSON.test;

import java.io.IOException;

import org.json.JSONException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import cz.ivantichy.support.JSON.JSONReader;

public class JSONReaderTest {

	public static void main(String[] args) {

		try {
			System.out.println(JSONReader.read(args[0], args[1]));
		} catch (JSONException | IOException e) {

			e.printStackTrace();
			System.exit(1);
		} catch (Base64DecodingException e) {
			System.exit(2);
			e.printStackTrace();
		}

	}

}
