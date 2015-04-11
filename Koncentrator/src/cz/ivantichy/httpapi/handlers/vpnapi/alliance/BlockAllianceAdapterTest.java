package cz.ivantichy.httpapi.handlers.vpnapi.alliance;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.fileutils.FileWork;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.PUTRequest;

public class BlockAllianceAdapterTest {

	@Test()
	public static void main(String[] args) throws IOException {

		BlockAllianceAdapter baa = new BlockAllianceAdapter();

		String json = FileWork.readFile(args[0]);

		FileWork.saveFile(args[0],
				baa.handlePOST(new POSTRequest(null, null, null, json, null))
						.toString());

	}
}
