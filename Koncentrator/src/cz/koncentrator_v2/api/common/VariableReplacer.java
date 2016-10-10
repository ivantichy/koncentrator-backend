package cz.koncentrator_v2.api.common;

import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.base64.B64;

public class VariableReplacer {
	private static Logger log = LogManager.getLogger(VariableReplacer.class
			.getName());;

	public static String replaceField(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]", json.get(fieldname)
				.toString());

	}

	public static String replaceFieldB64(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]",
				B64.decode(json.get(fieldname).toString()));

	}

	public static String replaceAllFields(JSONObject json, String input)
			throws IOException {

		log.debug("Replace all fields - JSON: " + json.toString());
		log.debug("Replace all fields - input: " + input);
		for (Iterator<String> iterator = json.keys(); iterator.hasNext();) {
			String key = iterator.next();

			log.debug("Trying to replace: " + key);
			input = replaceField(key, input, json);

		}

		if (input.indexOf('{') > -1) {
			log.error("Missing param here: "
					+ input.substring(input.indexOf('{')) + "  JSON:"
					+ json.toString());
			throw new IOException("Missing parameter here: "
					+ input.substring(input.indexOf('{')));
		}

		return input;
	}

}
