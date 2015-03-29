package cz.ivantichy.supersimple.restapi.handlers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.config.ConfigElement;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.GETHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.scripts.ExecutionResult;
import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class UniversalScriptHandler implements POSTHandlerInterface,
		GETHandlerInterface, DELETEHandlerInterface, PUTHandlerInterface {

	private static final Logger log = LogManager
			.getLogger(UniversalScriptHandler.class.getName());

	private ConfigElement config;

	public UniversalScriptHandler(ConfigElement config) {

		this.config = config;
	}

	protected ExecutionResult executeCommand(String script, String parametres)
			throws Exception {
		log.info("Executing command " + parametres);
		return new ExecutionResult(0, "");
	}

	protected String updatePath(HashMap<String, String> params,
			ConfigElement config) throws Exception {

		return config.getScript();

	}

	private String constructCommandParams(HashMap<String, String> params)
			throws IOException {

		StringBuffer out = new StringBuffer();
		log.debug("Parsing params to build command line params " + params);
		log.debug("Params definition from config " + config.getAruments());

		for (Iterator<String> iterator = config.getAruments().keySet()
				.iterator(); iterator.hasNext();) {

			String name = (String) iterator.next();
			String type = config.getAruments().get(name);
			String value = params.get(name);

			log.debug("Parsed param: " + name + " " + value + " " + type);

			if (!checkParam(value, type)) {
				log.error("Invalid param - contains unallowed character "
						+ name + " " + value + " " + type);
				throw new IOException(
						"Invalid param - contains unallowed character " + name
								+ " " + value + " " + type);
			}

			if (out.length() > 0)
				out.append(Static.CMDSPACE);

			out.append(value);
		}

		log.debug("Built command params " + out);
		return out.toString();

	}

	private boolean checkParam(String value, String type) {

		if (type.equalsIgnoreCase(Static.NUMBER_TYPE))
			return Static.NUMBER_TYPE_CHECK.matcher(value).matches();
		if (type.equalsIgnoreCase(Static.STRING_TYPE))
			return Static.STRING_TYPE_CHECK.matcher(value).matches();
		if (type.equalsIgnoreCase(Static.SAFE_STRING_TYPE))
			return Static.SAFE_STRING_TYPE_CHECK.matcher(value).matches();
		if (type.equalsIgnoreCase(Static.IP_ADDRESS_TYPE))
			return Static.IP_ADDRESS_TYPE_CHECK.matcher(value).matches();
		if (type.equalsIgnoreCase(Static.BASE64_SAFE_STRING_TYPE))
			return Static.BASE64_STRING_TYPE_CHECK.matcher(value).matches();

		return false;

	}

	@Override
	public Response handlePOST(POSTRequest req) throws IOException {

		return handle(req.getparams, req.decoratedurl);
	}

	private Response handle(HashMap<String, String> getparams,
			String decoratedurl) throws IOException {

		try {

			ExecutionResult re = executeCommand(updatePath(getparams, config),
					constructCommandParams(getparams));

			log.info("Command executed " + decoratedurl + " " + re.getCode()
					+ " " + re.getText());

			String output = config.getOutput();

			if (output.equalsIgnoreCase(Static.OUTPUT_NONE)) {
				log.debug("Output none");
				return new Response("", re.getCode() == 0);

			}
			if (output.equalsIgnoreCase(Static.OUTPUT_CODE)) {
				log.debug("Output code");
				return new Response(String.valueOf(re.getCode()),
						re.getCode() == 0);
			}
			if (output.equalsIgnoreCase(Static.OUTPUT_TEXT)) {
				log.debug("Output text");
				return new Response(re.getText(), re.getCode() == 0);

			}
			if (output.equalsIgnoreCase(Static.OUTPUT_JSON)) {
				log.debug("Output JSON");
				return new Response("{ \"return_code\" : " + re.getCode()
						+ ", \"text_url_encoded\" : \""
						+ URLEncoder.encode(re.getText(), Static.ENCODING)
						+ "\"", re.getCode() == 0);

			}

			log.warn("Wrong output type setting? " + output);

			return new Response("", re.getCode() == 0);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Request handling error " + decoratedurl + " "
					+ getparams);
			throw new IOException("Execution failed.");
		}

	}

	@Override
	public Response handleDELETE(DELETERequest req) throws IOException {
		return handle(req.getparams, req.decoratedurl);
	}

	@Override
	public Response handleGET(GETRequest req) throws IOException {
		return handle(req.getparams, req.decoratedurl);

	}

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {
		return handle(req.getparams, req.decoratedurl);

	}
}
