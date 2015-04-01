package cz.ivantichy.koncentrator.simple.certgen;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class DeleteCa extends CommandExecutor {

	public static synchronized String deleteCa(String type, String name)
			throws Exception {
		String path = location + slash + Static.INSTANCESFOLDER + slash + type
				+ slash + name;
		FileWork.deleteFolder(path);
		return "";

	}

}
