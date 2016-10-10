package archive;

import java.io.IOException;

import org.json.JSONObject;

public interface Create {

	public abstract JSONObject create(JSONObject json) throws IOException;

	@Deprecated
	public abstract JSONObject createForTunBasic(JSONObject json)
			throws IOException;

	@Deprecated
	public abstract JSONObject createForTapAdvanced(JSONObject json)
			throws IOException;
}
