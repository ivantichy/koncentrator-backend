package cz.ivantichy.supersimple.restapi.scripts;

import java.io.InputStream;
import java.io.InputStreamReader;

public class StringPump implements Runnable {

	public StringPump(InputStream istrm, StringBuffer ostrm) {
		istrm_ = new InputStreamReader(istrm);
		ostrm_ = ostrm;
	}

	public void run() {
		try {
			final char[] buffer = new char[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1;) {

				ostrm_.append(buffer, 0, length);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final StringBuffer ostrm_;
	private final InputStreamReader istrm_;
}