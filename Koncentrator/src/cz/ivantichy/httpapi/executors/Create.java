package cz.ivantichy.httpapi.executors;

import java.io.IOException;

import org.json.JSONObject;

public interface Create {

	public abstract JSONObject createForTunBasic(JSONObject json)
			throws IOException;

	public abstract JSONObject createForTapAdvanced(JSONObject json)
			throws IOException;
}
