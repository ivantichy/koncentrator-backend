package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Create extends TypedInterface {

	abstract public JSONObject create(JSONObject json) throws Exception;

}
