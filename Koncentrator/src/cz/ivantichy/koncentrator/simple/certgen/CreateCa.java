package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import cz.ivantichy.base64.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.httpapi.handlers.vpnapi.CreateSubVPNAdapter;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class CreateCa extends CommandExecutor {
	private static final Logger log = LogManager.getLogger(CreateCa.class
			.getName());
	static String location = Static.RSALOCATION;
	static String slash = Static.FOLDERSEPARATOR;

	public static synchronized String createCa(JSONObject json)
			throws Exception {

		String type = json.getString("subvpn_type");
		String name = json.getString("subvpn_name");
		String domain = json.getString("domain");
		int days = json.getInt("ca_valid_days");
		String source = location + slash + Static.GENERATEFOLDER + slash + type;
		String destination = location + slash + Static.INSTANCESFOLDER + slash
				+ type + slash + name;

		exec(json, source, destination, type, name, domain, days);

		return createCaToJSON(destination, json).toString();

	}

	private static JSONObject createCaToJSON(String path, JSONObject json)
			throws IOException {

		String ca = new String(Files.readAllBytes(Paths.get(path
				+ "/keys/ca.crt")));
		String ta = new String(Files.readAllBytes(Paths.get(path
				+ "/keys/ta.key")));

		String dh = new String(Files.readAllBytes(Paths.get(path
				+ "/keys/dh2048.pem")));

		json.put("ta", B64.encode(ta));
		json.put("ca", B64.encode(ca));
		json.put("dh", B64.encode(dh));

		return json;

	}

	private static void exec(JSONObject json, String source,
			String destination, String type, String name, String domain,
			int days) throws IOException {

		FileWork.checkFolder(source, destination);
		FileWork.copyFolder(source, destination);

		Process p = preExec();
		OutputStream o = p.getOutputStream();
		
		StringBuffer a pak replace all

		o.write(("set -ex \n").getBytes());
		o.flush();
		o.write(("cd " + destination + "\n").getBytes());
		o.flush();
		o.write("source ./vars \n".getBytes());
		o.write(("export KEY_EXPIRE=" + days + "\n").getBytes());
		o.write("export KEY_COUNTRY=CZ\n".getBytes());
		o.write("export KEY_PROVINCE=CZ\n".getBytes());
		o.write("export KEY_CITY=\n".getBytes());
		o.write(("export KEY_ORG=" + domain + "\n").getBytes());
		o.write("export KEY_EMAIL=\n".getBytes());
		o.write(("export KEY_CN=\"" + name + "\"\n").getBytes());
		o.write("export KEY_NAME=\n".getBytes());
		o.write("export KEY_OU=\n".getBytes());
		o.write("export PKCS11_MODULE_PATH=\n".getBytes());
		o.write("export PKCS11_PIN=\n".getBytes());
		o.write(replaceAllFields(json,
				"./createca.sh {subvpn_type} {subvpn_name} \n").getBytes());

		o.flush();
		o.write(("exit\n").getBytes());
		o.close();

		postExec(p);

	}

}
