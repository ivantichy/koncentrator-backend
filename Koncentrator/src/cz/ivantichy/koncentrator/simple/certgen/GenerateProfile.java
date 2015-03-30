package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.json.JSONObject;
import cz.ivantichy.base64.*;
public class GenerateProfile {

	static String location = Constants.location;

	public static synchronized String generateProfile(String cn, String domain,
			int days, String name, String type) throws Exception {

		String path = "instances/" + type + "/" + name;
		exec(cn, domain, days, path);

		JSONObject json = new JSONObject();
		json.put("common_name", cn);
		json.put("subvpn_name", name);
		json.put("subvpn_type", type);
		json.put("domain", domain);
		json.put("profile_valid_days", days);

		return createProfileToString(json, cn, path, name).toString();

	}

	public static JSONObject createProfileToString(JSONObject json, String cn,
			String path, String name) throws IOException {

		String ca = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ca.crt")));
		String ta = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ta.key")));

		String key = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + cn + ".key")));
		String crt = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + cn + ".crt")));

		// String config = new String(Files.readAllBytes(Paths.get(location +
		// "/"
		// + path + "/client.conf")));

		Files.delete(Paths.get(location + "/" + path + "/keys/" + cn + ".key"));
		Files.delete(Paths.get(location + "/" + path + "/keys/" + cn + ".crt"));
		Files.delete(Paths.get(location + "/" + path + "/keys/" + cn + ".csr"));
		Files.delete(Paths.get(location + "/" + path + "/keys/01.pem"));

		/*
		 * config = config.replaceAll("[{]ca[}]", ca); config =
		 * config.replaceAll("[{]key[}]", key); config =
		 * config.replaceAll("[{]cert[}]", crt); config =
		 * config.replaceAll("[{]ta[}]", ta); config =
		 * config.replaceAll("[{]subvpnname[}]", name);
		 */

		json.put("ca", B64.encode(ca));
		json.put("ta", B64.encode(ta));
		json.put("key", B64.encode(key));
		json.put("cert", B64.encode(crt));

		return json;

	}

	public static int exec(String cn, String domain, int days, String path)
			throws Exception {

		if (!new File(location + "/" + path).exists())
			throw new Exception("path does not exist");

		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new SyncPipe(p.getErrorStream(), System.out)).start();

		new Thread(new SyncPipe(p.getInputStream(), System.out)).start();

		OutputStream o = p.getOutputStream();

		o.write(("echo \"" + location + "/" + path + "\"\n").getBytes());
		o.write(("cd \"" + location + "/" + path + "\"\n").getBytes());
		o.write(("pwd\n").getBytes());

		o.write(("rm " + location + "/" + path + "/keys/index.txt\n")
				.getBytes());
		o.write(("touch " + location + "/" + path + "/keys/index.txt\n")
				.getBytes());

		System.out.println("touch > " + location + "/" + path
				+ "/keys/index.txt\n");

		o.write(("echo 01 > " + location + "/" + path + "/keys/serial\n")
				.getBytes());

		o.flush();

		o.write("source ./vars \n".getBytes());
		o.write(("export KEY_EXPIRE=" + days + "\n").getBytes());
		o.write("export KEY_COUNTRY=CZ\n".getBytes());
		o.write("export KEY_PROVINCE=CZ\n".getBytes());
		o.write("export KEY_CITY=\n".getBytes());
		o.write(("export KEY_ORG=" + domain + "\n").getBytes());
		o.write("export KEY_EMAIL=\n".getBytes());
		o.write(("export KEY_CN=\"" + cn + "\"\n").getBytes());
		o.write("export KEY_NAME=\n".getBytes());
		o.write("export KEY_OU=\n".getBytes());
		o.write("export PKCS11_MODULE_PATH=\n".getBytes());
		o.write("export PKCS11_PIN=\n".getBytes());

		o.write("\n".getBytes());

		o.write(("./pkitool \"" + cn + "\"\n").getBytes());

		o.flush();

		o.close();
		p.waitFor();

		return p.exitValue();

	}
}
