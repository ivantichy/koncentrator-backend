package cz.koncentrator_v2.api.common;

import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class PathConfigCERT_TUN_basic extends PathConfig {

	public static PathConfigCERT_TUN_basic instance = new PathConfigCERT_TUN_basic();

	public String getSourcePath(JSONObject json) {

		return FileWork.replaceDoubleSlashes(Static.RSALOCATION
				+ Static.FOLDERSEPARATOR + Static.GENERATEFOLDER
				+ Static.FOLDERSEPARATOR + json.get(Static.SUBVPN_TYPE));

	}

	public String getDestinationPath(JSONObject json) {

		return FileWork.replaceDoubleSlashes(Static.RSALOCATION
				+ Static.FOLDERSEPARATOR + Static.INSTANCESFOLDER
				+ Static.FOLDERSEPARATOR + json.get(Static.SUBVPN_TYPE)
				+ Static.FOLDERSEPARATOR + json.get(Static.CA_NAME));

	}

}
