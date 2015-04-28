package cz.ivantichy.httpapi.executors;

import java.io.IOException;

import org.json.JSONObject;

public interface Create {

	public JSONObject createForTunBasic(JSONObject json) throws IOException;

	public JSONObject createForTapAdvanced(JSONObject json) throws IOException;
}
