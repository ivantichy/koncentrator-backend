package cz.koncentrator_v2.api.cert.CreateCa;

import org.json.JSONObject;

public interface Create {

	abstract public String implementedType();

	abstract public JSONObject create(JSONObject json);

}
