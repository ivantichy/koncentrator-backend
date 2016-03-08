package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;

public class BlockProfileAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		BlockProfileAdapter bpa = new BlockProfileAdapter();

		String json = FileWork.readFile(args[0]);
		bpa.handlePOST(new POSTRequest(null, null, null, json, null));

	}
}
