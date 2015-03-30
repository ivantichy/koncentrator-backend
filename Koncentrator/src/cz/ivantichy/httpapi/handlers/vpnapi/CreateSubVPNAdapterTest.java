package cz.ivantichy.httpapi.handlers.vpnapi;

import java.io.IOException;

import org.testng.annotations.Test;

import cz.ivantichy.supersimple.restapi.handlers.CreateCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.DeleteCaAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateProfileAdapter;
import cz.ivantichy.supersimple.restapi.handlers.GenerateServerAdapter;
import cz.ivantichy.supersimple.restapi.server.Server;

public class CreateSubVPNAdapterTest {

	static class Runner extends Thread {

		Server s;
		int port;

		public Runner(Server s, int port) {
			this.s = s;
			this.port = port;
		}

		public void run() {
			try {
				s.listen(port);
			} catch (IOException e) {

				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Test()
	public static void main(String[] args) throws IOException,
			InterruptedException {
		System.out.println("main");

		Server s = new Server();

		s.registerPUTHandler("/test/", new CreateSubVPNAdapter());

		int port;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);

		} else {

			port = 10001;

		}

		(new Runner(s, port)).start();

		Thread.sleep(10000);
		System.exit(0);

	}
}
