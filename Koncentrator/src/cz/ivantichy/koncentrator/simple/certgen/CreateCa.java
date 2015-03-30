package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.json.JSONObject;

public class CreateCa {

	static String location = Constants.location;

	public static synchronized String createCa(JSONObject json)
			throws Exception {
		String path = "generate/" + json.getString("subvpn_type");

		// String type, String name, String domain, int days

		exec(path, json.getString("subvpn_type"),
				json.getString("subvpn_name"), json.getString("domain"),
				json.getInt("ca_valid_days"));
		path = "instances/" + json.getString("subvpn_type") + "/"
				+ json.getString("subvpn_name");

		
		return createCaToJSON(path, json).toString();

	}

	private static JSONObject createCaToJSON(String path, JSONObject json)
			throws IOException {

		String ca = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ca.crt")));
		String ta = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ta.key")));

		String dh = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/dh2048.pem")));

		json.put("ta", ta);
		json.put("ca", ca);
		json.put("dh", dh);

		return json;

	}

	public static int exec(String path, String type, String name,
			String domain, int days) throws Exception {

		if (new File(location + "/instances/" + type + "/" + name).exists())
			throw new Exception("path exists");

		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new SyncPipe(p.getErrorStream(), System.out)).start();
		new Thread(new SyncPipe(p.getInputStream(), System.out)).start();

		OutputStream o = p.getOutputStream();

		o.write(("echo \"" + location + "/" + path + "\"\n").getBytes());
		o.write(("cd \"" + location + "/" + path + "\"\n").getBytes());
		o.write(("pwd\n").getBytes());

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
		o.write(("./createca.sh " + type + " " + name + " \n").getBytes());

		o.flush();
		o.close();
		p.waitFor();

		return p.exitValue();

	}
}
