package cz.ivantichy.httpapi.handlers.certapi.tun_basic;

import java.io.IOException;

import org.json.JSONObject;

import cz.ivantichy.supersimple.restapi.staticvariables.Static;
import cz.koncentrator_v2.api.common.commandexecution.CommandExecutor;

public class ServiceRestarter extends CommandExecutor {

	public static void restartAllProcesses(JSONObject json) throws IOException {

		clear();
		appendLine(Static.STARTOPENVPNSERVICE);
		exec(json);
	}
}
