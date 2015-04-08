package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class CreateSubVPNAdapterTest2 {

	@Test()
	public static void main(String[] args) throws IOException {

		CreateSubVPNAdapter csvpna = new CreateSubVPNAdapter();

		String json = FileWork.readFile(args[0]);

		FileWork.saveFile(args[0], csvpna.handlePUT(new PUTRequest(null, null, null, json, null)).toString());

	}
}
