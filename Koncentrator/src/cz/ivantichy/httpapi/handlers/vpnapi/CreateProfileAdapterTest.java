package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class CreateProfileAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		CreateProfileAdapter cpa = new CreateProfileAdapter();

		String json = FileWork.readFile(args[0]);

		FileWork.saveFile(args[0], cpa.handlePUT(new PUTRequest(null, null, null, json, null)).toString());

	}
}
