package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class GenerateServer {

	static String location = Constants.location;

	public static synchronized String generateServer(String type, String name,
			String domain, int days, String common_name) throws Exception {
		String path = "instances/" + type + "/" + name;
		exec(path, type, name, domain, days, common_name);
		return createServerToJSON(common_name, path);

	}

	public static String createServerToJSON(String common_name, String path)
			throws IOException {

		String conf = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/server.conf")));

		String ca = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + "ca.crt")));

		String key = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + common_name + ".key")));

		String crt = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + common_name + ".crt")));

		String ta = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + "ta.key")));

		String dh = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/" + "dh2048.pem")));

		Files.delete(Paths.get(location + "/" + path + "/keys/" + common_name
				+ ".key"));
		Files.delete(Paths.get(location + "/" + path + "/keys/" + common_name
				+ ".crt"));
		Files.delete(Paths.get(location + "/" + path + "/keys/" + common_name
				+ ".csr"));

		conf = conf.replaceAll("[{]ca[}]", ca);
		conf = conf.replaceAll("[{]key[}]", key);
		conf = conf.replaceAll("[{]cert[}]", crt);
		conf = conf.replaceAll("[{]ta[}]", ta);
		conf = conf.replaceAll("[{]dh[}]", dh);

		return "{" + "\"server_conf_base64\" : \""
				+ Base64.encode(conf.getBytes()) + "\"}";

	}

	public static int exec(String path, String type, String name,
			String domain, int days, String common_name) throws Exception {

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
		o.write(("export KEY_CN=\"" + common_name + "\"\n").getBytes());
		o.write("export KEY_NAME=\n".getBytes());
		o.write("export KEY_OU=\n".getBytes());
		o.write("export PKCS11_MODULE_PATH=\n".getBytes());
		o.write("export PKCS11_PIN=\n".getBytes());
		o.write(("./generateserver.sh " + type + " " + name + " " + common_name + " \n")
				.getBytes());

		o.flush();
		o.close();
		p.waitFor();

		return p.exitValue();

	}
}
