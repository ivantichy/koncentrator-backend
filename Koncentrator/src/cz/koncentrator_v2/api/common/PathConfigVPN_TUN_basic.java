package cz.koncentrator_v2.api.common;

import org.json.JSONObject;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class PathConfigVPN_TUN_basic extends PathConfig {

	public static PathConfigVPN_TUN_basic instance = new PathConfigVPN_TUN_basic();

	public String getDestinationPath(JSONObject json) {

		// TODO cache results

		return FileWork.replaceDoubleSlashes(Static.OPENVPNLOCATION
				+ Static.INSTANCESFOLDER + Static.FOLDERSEPARATOR
				+ json.getString(Static.SUBVPN_TYPE) + Static.FOLDERSEPARATOR
				+ json.getString(Static.SERVER_NAME) + Static.FOLDERSEPARATOR);
	}

	public String getSourcePath(JSONObject json) {
		return FileWork.replaceDoubleSlashes(Static.OPENVPNLOCATION
				+ Static.FOLDERSEPARATOR + Static.TEMPLATEFOLDER
				+ Static.FOLDERSEPARATOR + json.getString(Static.SUBVPN_TYPE)
				+ Static.FOLDERSEPARATOR);
	}

	public String getConfigTemplatePath(JSONObject json) {

		return getDestinationPath(json) + Static.FOLDERSEPARATOR
				+ "server-template.conf";
	}

	public String getConfigInstancePath(JSONObject json) {

		return Static.OPENVPNLOCATION + Static.FOLDERSEPARATOR
				+ json.get(Static.SUBVPN_TYPE) + "_"
				+ json.get(Static.SERVER_NAME) + ".conf";

	}

	/*
	 * public static final String getCMDspath(JSONObject json) {
	 * 
	 * // TODO co tu kurva dela cd? najit v puvodnim kodu
	 * 
	 * return "cd " + getDestionationPath(json) + Static.FOLDERSEPARATOR +
	 * "cmds"; }
	 */

}
