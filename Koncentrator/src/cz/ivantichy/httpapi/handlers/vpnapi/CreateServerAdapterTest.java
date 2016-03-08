package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class CreateServerAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		CreateServerAdapter csa = new CreateServerAdapter();

		String json = FileWork.readFile(args[0]);

		FileWork.saveFile(args[0], csa.handlePUT(new PUTRequest(null, null, null, json, null)).toString());

	}
}
