package cz.ivantichy.supersimple.restapi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestParser {

	private static Pattern colon = Pattern.compile("[:]");
	private static Pattern ampersand = Pattern.compile("([&])|([&]amp[;])");
	private static Pattern equals = Pattern.compile("[=]");
	private static Pattern questionmark = Pattern.compile("[?]");

	public final static String encoding = "UTF-8";
	private static final Logger log = LogManager.getLogger(RequestParser.class
			.getName());

	public synchronized ParsedURL parseURL(String url) throws IOException {
		String[] splurl = questionmark.split(url);
		log.debug("parseURL " + url);

		if (splurl.length == 1) {

			return new ParsedURL(decorate(splurl[0]), "");

		} else if (splurl.length == 2) {

			return new ParsedURL(decorate(splurl[0]), URLDecoder.decode(
					splurl[1].trim(), encoding));

		} else {
			log.error("Strange url: " + url);
			throw new IOException("Strange url: " + url);

		}
	}

	public synchronized GETRequest parseGETRequest(ParsedURL parsedurl,
			String headers, String protocol) throws IOException, IOException {

		log.info("parseGETRequest " + parsedurl.getDecoratedurl());

		return new GETRequest(parseGETParams(parsedurl),
				headersToHashMap(headers), parsedurl.getDecoratedurl(),
				protocol);

	}

	public synchronized DELETERequest parseDELETERequest(ParsedURL parsedurl,
			String headers, String protocol) throws IOException, IOException {
		log.info("parseDELETERequest " + parsedurl.getDecoratedurl());

		return new DELETERequest(parseGETParams(parsedurl),
				headersToHashMap(headers), parsedurl.getDecoratedurl(),
				protocol);

	}

	public synchronized POSTRequest parsePOSTRequest(ParsedURL parsedurl,
			String headers, String postdata, String protocol)
			throws IOException, IOException {
		log.info("parsePOSTRequest " + parsedurl.getDecoratedurl());

		return new POSTRequest(parseGETParams(parsedurl),
				headersToHashMap(headers), parsedurl.getDecoratedurl(),
				postdata, protocol);

	}

	public synchronized PUTRequest parsePUTRequest(ParsedURL parsedurl,
			String headers, String putdata, String protocol)
			throws IOException, IOException {
		log.info("parsePOSTRequest " + parsedurl.getDecoratedurl());

		return new PUTRequest(parseGETParams(parsedurl),
				headersToHashMap(headers), parsedurl.getDecoratedurl(),
				putdata, protocol);

	}

	private static String decorate(String s) {
		s = s.toLowerCase().trim();
		if (s.endsWith("/")) {

			return s.substring(0, s.length() - 1);
		}
		return s;
	}

	private static HashMap<String, String> headersToHashMap(String headers)
			throws IOException {

		BufferedReader br = new BufferedReader(new StringReader(headers));
		HashMap<String, String> h = new HashMap<String, String>();
		String line;
		while ((line = br.readLine()) != null) {
			String[] splitted = colon.split(line);
			h.put(splitted[0].trim(), splitted[1].trim());

		}

		return h;

	}

	public HashMap<String, String> parseGETParams(String params)
			throws UnsupportedEncodingException {

		HashMap<String, String> getparams = new HashMap<String, String>();

		if (params.length() > 1) {

			String[] spl = ampersand.split(params);

			for (int i = 0; i < spl.length; i++) {

				String[] param = equals.split(spl[i]);

				if (param.length != 2) {

					// strange or empty param?
					if (param.length == 1) {
						getparams.put(param[0].trim().toLowerCase(), "");
					} else {
						log.warn("Cannot parse GET param: " + spl[i]);
					}

				} else {
					log.debug("Adding get param: " + param[0].trim() + "="
							+ param[1].trim());
					getparams.put(param[0].trim().toLowerCase(),
							param[1].trim());
				}

			}

		}

		log.debug("Get parametres: " + getparams.toString());

		return getparams;

	}

	public HashMap<String, String> parseGETParams(ParsedURL url)
			throws UnsupportedEncodingException {

		return parseGETParams(url.getGetparams());

	}

}
