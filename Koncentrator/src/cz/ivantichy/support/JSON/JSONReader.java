package cz.ivantichy.support.JSON;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.*;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class JSONReader {

	public static String read(String profile, String name)
			throws JSONException, IOException, Base64DecodingException {

		JSONObject obj = new JSONObject(FileUtils.readFileToString(
				new File(profile)).replaceAll("\n|\r", ""));

		return new String(Base64.decode(obj.get(name).toString()));

	}
}
