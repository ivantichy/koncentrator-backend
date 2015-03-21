package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class CreateCa {

	static String location = Constants.location;

	public static synchronized String createCa(String type, String name,
			String domain, int days) throws Exception {
		String path = "generate/" + type;
		
		exec(path, type, name, domain, days);
		path = "instances/" + type + "/" + name;
		return createCaToJSON(path);

	}

	public static String createCaToJSON(String path) throws IOException {

		String ca = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ca.crt")));
		String ta = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/ta.key")));

		String dh = new String(Files.readAllBytes(Paths.get(location + "/"
				+ path + "/keys/dh2048.pem")));

		return "{" + "\"ca_crt_base64\" : \"" + Base64.encode(ca.getBytes())
				+ "\", \"ta_key_base64\" : \"" + Base64.encode(ta.getBytes())
				+ "\", \"dh_pem_base64\" : \"" + Base64.encode(dh.getBytes())
				+ "\"}";

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
