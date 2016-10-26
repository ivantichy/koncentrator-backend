package cz.koncentrator_v2.api.common;

import org.json.JSONObject;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;

abstract public class PathConfig {

	abstract String getDestinationPath(JSONObject json);

	abstract String getSourcePath(JSONObject json);

	public String getCAJsonPath(JSONObject json) {

		return getDestinationPath(json) + Static.FOLDERSEPARATOR
				+ json.getString(Static.CA_NAME) + ".json";

	}

	public String getServerJsonPath(JSONObject json) {

		return getDestinationPath(json) + Static.FOLDERSEPARATOR
				+ json.getString(Static.SERVER_NAME) + ".json";

	}

	public String getProfileJsonPath(JSONObject json) {

		return getDestinationPath(json) + Static.FOLDERSEPARATOR
				+ json.getString(Static.COMMON_NAME) + ".json";

	}
}
