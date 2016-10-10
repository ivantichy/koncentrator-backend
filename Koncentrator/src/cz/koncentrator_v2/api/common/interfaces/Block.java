package cz.koncentrator_v2.api.common.interfaces;

import org.json.JSONObject;

public interface Block extends TypedInterface {

	abstract public JSONObject block(JSONObject json) throws Exception;
	

}
