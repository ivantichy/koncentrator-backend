package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.koncentrator.simple.certgen.SyncPipe;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;
import cz.ivantichy.supersimple.restapi.server.Server;
import cz.ivantichy.support.JSON.JSONReader;
import cz.ivantichy.supersimple.restapi.staticvariables.*;

public class CreateSubVPNAdapter implements PUTHandlerInterface {
	private static final Logger log = LogManager
			.getLogger(CreateSubVPNAdapter.class.getName());

	public static String replaceField(String fieldname, String input,
			JSONObject json) {

		return input.replaceAll("[{]" + fieldname + "[}]",
				json.getString(fieldname));

	}

	@Override
	public Response handlePUT(PUTRequest req) throws IOException {

		log.debug("PUT data: " + req.putdata);

		log.info("going to handle PUT. Reading/parsing JSON.");
		JSONObject json = readJSON(req.putdata);
		log.info("JSON parsed. Going to test fields.");
		testFields(json);
		log.info("Going to create config.");
		String config = createConfig(json);
		String source = Static.OPENVPNLOCATION + Static.GENERATEFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR;
		log.info("Source location:" + source);

		String destination = Static.OPENVPNLOCATION + Static.INSTANCESFOLDER
				+ json.getString("subvpn_type") + Static.FOLDERSEPARATOR
				+ json.getString("subvpn_name") + Static.FOLDERSEPARATOR;
		log.info("Destination location:" + destination);

		File sourcefile = new File(source);
		File destinationfile = new File(destination);

		if (!sourcefile.exists() || !sourcefile.isDirectory()) {

			throw new IOException("Source dir path wrong");
		}

		if (destinationfile.exists() && destinationfile.isDirectory()) {

			throw new IOException(
					"Destination path exists - subvpn probably exists already");
		}

		FileUtils.copyDirectory(sourcefile, destinationfile);

		log.info("Folder copied.");

		String configpath = Static.OPENVPNLOCATION
				+ json.getString("subvpn_type") + "_" + json.get("subvpn_name");

		log.info("Config file path: " + configpath);

		File configfile = new File(configpath);

		if (configfile.exists() && !configfile.isDirectory()) {
			throw new IOException("config file exists already");
		}

		FileUtils.writeStringToFile(configfile, config);

		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new SyncPipe(p.getErrorStream(), System.out)).start();
		new Thread(new SyncPipe(p.getInputStream(), System.out)).start();

		OutputStream o = p.getOutputStream();

		o.write(("set -ex \n").getBytes());
		o.flush();
		o.write(("cd " + destination + Static.FOLDERSEPARATOR + "cmds\n")
				.getBytes());

		o.write((replaceAllFields(json,
				"./createsubvpn.sh {subvpn_name} {subvpn_type} {ip_range}\n"))
				.getBytes());

		o.flush();
		o.write(("exit\n").getBytes());
		o.close();

		try {
			p.waitFor();
		} catch (InterruptedException e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());
		}

		return new Response("created", true);
	}

	private String replaceAllFields(JSONObject json, String input)
			throws IOException {

		for (Iterator<String> iterator = json.keys(); iterator.hasNext();) {
			String key = iterator.next();

			log.debug("Trying to replace: " + key);
			input = replaceField(key, input, json);

		}

		if (input.indexOf('{') > -1) {
			log.error("Missing param " + input + "  JSON:" + json.toString());
			throw new IOException("Missing parameter " + input);
		}

		return input;
	}

	private String createConfig(JSONObject json) {

		String config = json.getString("server_conf_base64");

		config = replaceField("server_port", config, json);
		config = replaceField("server_protocol", config, json);
		config = replaceField("management_port", config, json);
		config = replaceField("device", config, json);
		config = replaceField("subvpn_name", config, json);
		config = replaceField("subvpn_type", config, json);
		config = replaceField("ta", config, json);
		config = replaceField("ca", config, json);
		config = replaceField("key", config, json);
		config = replaceField("cert", config, json);
		config = replaceField("dh", config, json);
		config = replaceField("dh_size", config, json);
		config = replaceField("ip_server", config, json);
		config = replaceField("ip_mask", config, json);
		config += System.lineSeparator()
				+ json.getString("commands").replaceAll("[,]",
						System.lineSeparator());

		return config;

	}

	private JSONObject readJSON(String data) {

		return new JSONObject(data);

	}

	private void testFields(JSONObject json) {
		/*
		 * 
		 * { "server_conf_base64": "", "server_port": "", "server_protocol": "",
		 * "management_port": "", "device": "", "node": "", "subvpn_name": "",
		 * "subvpn_type": "", "ta": "", "ca": "", "key": "", "cert": "", "dh":
		 * "", "dh_size": "", "ip_server": "", "ip_mask": "", "commands": "" }
		 */

	}

}
