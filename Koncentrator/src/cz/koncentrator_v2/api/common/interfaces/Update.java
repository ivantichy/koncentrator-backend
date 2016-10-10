package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Update extends TypedInterface {

	abstract public JSONObject update(JSONObject json) throws Exception;

}
