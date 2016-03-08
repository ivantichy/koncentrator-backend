package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;

public class UpdateProfileAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		UpdateServerAdapter usa = new UpdateServerAdapter();

		String json = FileWork.readFile(args[0]);
		usa.handlePOST(new POSTRequest(null, null, null, json, null));

	}
}
