package cz.ivantichy.base64;

import java.util.Base64;

public class B64 {

	public static String encode(String string) {

		return new String(Base64.getEncoder().encode(string.getBytes()))
				.replaceAll("\\s", "");
	}

	public static String decode(String string) {

		return new String(Base64.getDecoder().decode(string.getBytes()));

	}

}
