package cz.ivantichy.supersimple.restapi.handlers;

import java.io.File;
import java.util.HashMap;

import cz.ivantichy.supersimple.restapi.config.ConfigElement;
import cz.ivantichy.supersimple.restapi.scripts.UnixScriptExecutor;

public class VPNAPIScriptHandler extends UnixScriptExecutor {

	public VPNAPIScriptHandler(ConfigElement config) {
		super(config);

	}

	@Override
	protected String updatePath(HashMap<String, String> params,
			ConfigElement config) throws Exception {

		String s;
		if (params.get("subvpn_type") == null) {

			s = super.updatePath(params, config)
					.replaceAll(
							"[{]path[}]",
							params.get("subvpn_type1") + "/"
									+ params.get("subvpn_name1"));

		} else {
			s = super.updatePath(params, config)
					.replaceAll(
							"[{]path[}]",
							params.get("subvpn_type") + "/"
									+ params.get("subvpn_name"));
		}

		File f = new File(s);

		if ((!f.exists()) || (f.isDirectory())) {
			throw new Exception("file does not exist: " + s);

		}

		return s;
	}
}
