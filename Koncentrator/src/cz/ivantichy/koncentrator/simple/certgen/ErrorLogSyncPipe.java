package cz.ivantichy.koncentrator.simple.certgen;

import java.io.InputStream;

import org.apache.logging.log4j.Logger;

public class ErrorLogSyncPipe implements Runnable {

	public ErrorLogSyncPipe(InputStream istrm, Logger log) {
		istrm_ = istrm;
		this.log = log;
	}

	public void run() {
		try {
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
				log.error(new String(buffer, 0, length));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("ErrorLogSyncPipe thread exiting.");
	}

	private final Logger log;
	private final InputStream istrm_;
}