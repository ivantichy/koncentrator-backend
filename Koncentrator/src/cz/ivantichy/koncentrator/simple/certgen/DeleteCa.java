package cz.ivantichy.koncentrator.simple.certgen;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class DeleteCa {

	static String location = Constants.location;

	public static synchronized String deleteCa(String type, String name)
			throws Exception {
		String path = "instances/" + type + "/" + name;
		exec(path);
		return "";

	}

	public static int exec(String path) throws Exception {

		if (!new File(location + "/" + path).exists())
			throw new Exception("path does not exist");

		FileUtils.deleteDirectory(new File(location + "/" + path));

		return 0;

	}
}
