package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Server;

import cz.ivantichy.fileutils.*;

public class CreateServerAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		CreateSubVPNAdapter csvpna = new CreateSubVPNAdapter();

		String json = FileWork.readFile(args[0]);

		csvpna.handlePUT(new PUTRequest(null, null, null, json, null));

	}
}
