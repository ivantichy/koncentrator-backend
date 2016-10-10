package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Unblock extends TypedInterface {

	abstract public JSONObject unblock(JSONObject json) throws Exception;

}
