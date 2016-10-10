package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Delete extends TypedInterface {

	abstract public JSONObject delete(JSONObject json) throws Exception;

}
