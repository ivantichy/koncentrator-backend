package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class CreateAllianceAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		CreateAllianceAdapter caa = new CreateAllianceAdapter();

		String json = FileWork.readFile(args[0]);

		FileWork.saveFile(args[0],
				caa.handlePUT(new PUTRequest(null, null, null, json, null))
						.toString());

	}
}
