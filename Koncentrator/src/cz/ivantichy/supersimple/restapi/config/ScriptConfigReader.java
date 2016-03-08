package cz.ivantichy.supersimple.restapi.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.handlers.UniversalScriptHandler;
import cz.ivantichy.supersimple.restapi.server.Server;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class ScriptConfigReader {

	private static final Logger log = LogManager
			.getLogger(ScriptConfigReader.class.getName());

	private LinkedHashMap<String, String> parseParams(String params)
			throws IOException {

		String[] splStrings = Static.SEPARATOR.split(params);

		LinkedHashMap<String, String> out = new LinkedHashMap<String, String>();

		for (int i = 0; i < splStrings.length; i++) {
			log.debug("Parameter " + splStrings[i]);

			String[] oneString = Static.COLON.split(splStrings[i].trim());

			if (oneString.length != 2)
				throw new IOException("Malformed paramater: " + splStrings[i]);

			String type = oneString[1].trim().toLowerCase();

			if (!Static.ALLOWEDTYPES.matcher(type).matches())
				throw new IOException("Invalid param type: " + type);

			log.debug("Parameter to put " + oneString[0].trim().toLowerCase()
					+ " " + type);

			out.put(oneString[0].trim().toLowerCase(), type);

		}

		return out;

	}

	private ConfigElement parseLine(String line) throws IOException {

		log.info("Parsing line " + line);
		String[] Strings = Static.SEPARATOR.split(line.trim(), 5);

		if (Strings.length < 3)
			throw new IOException("Invalid config line: " + line);

		ConfigElement config = new ConfigElement(

		Strings[0].trim(), // typ
				Strings[1].trim(), // url
				Strings[2].trim(), // output
				Strings[3].trim(), // output
				null);

		if (Strings.length < 5) {

			config.setAruments(new LinkedHashMap<String, String>());

		} else {

			config.setAruments(parseParams(Strings[4].trim()));
		}

		log.debug("Parsed config element " + config);

		// POST URL OUTPUT|RETURNCODE|NONE scriptpath argumen:typ argument:typ
		// argument:typ
		// POST /test CODE /script/path/neco.sh a:string b:string c:string
		// typ: string
		// typ: number

		return config;
	}

	protected UniversalScriptHandler createHandler(ConfigElement config)

	{
		return new UniversalScriptHandler(config);
	}

	private void addHandlerForConfig(ConfigElement config, Server server)
			throws IOException {
		String type = config.getType();

		if (type.equalsIgnoreCase(Static.POST)) {

			log.info("Registering POST handler " + config.getUrl());
			server.registerPOSTHandler(config.getUrl(), createHandler(config));
			return;

		}
		if (type.equalsIgnoreCase(Static.GET)) {

			log.info("Registering GET handler " + config.getUrl());
			server.registerGETHandler(config.getUrl(), createHandler(config));
			return;

		}
		if (type.equalsIgnoreCase(Static.DELETE)) {

			log.info("Registering DELETE handler " + config.getUrl());
			server.registerDELETEHandler(config.getUrl(), createHandler(config));
			return;

		}
		
		if (type.equalsIgnoreCase(Static.PUT)) {

			log.info("Registering PUT handler " + config.getUrl());
			server.registerPUTHandler(config.getUrl(), createHandler(config));
			return;

		}

		throw new IOException("Wrong config " + config.getType());
	}

	public void readConfig(File file, Server s) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;

		while ((line = br.readLine()) != null) {

			if (line.trim().length() == 0
					|| Static.COMMENT.matcher(line).matches()) {

				log.debug("Ingoring line " + line);
				continue;
			}

			log.debug("Config line " + line);

			addHandlerForConfig(parseLine(line), s);

		}

		br.close();

	}

	public static void main(String[] args) throws Exception {

		ScriptConfigReader s = new ScriptConfigReader();
		s.readConfigAndListen(args);

	}

	public Server readConfigAndListen(String[] args) throws Exception {

		String conf;

		if (args.length > 0) {

			conf = args[0];

		} else {

			conf = "config.conf";
		}

		int port;

		if (args.length > 1) {

			port = Integer.parseInt(args[1]);

		} else {

			port = 8081;
		}

		Server server = new Server();
		log.info("Going to read config file " + conf);
		readConfig(new File(conf), server);
		server.listen(port);
		return server;

	}
}
