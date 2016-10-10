package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Generate

extends TypedInterface {

	abstract public JSONObject generate(JSONObject json) throws Exception;

}
