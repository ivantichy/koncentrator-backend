package cz.ivantichy.fileutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.koncentrator.simple.certgen.E_rrorLogSyncPipe;
import cz.ivantichy.koncentrator.simple.certgen.LogSyncPipe;

public class FileWork {

	public static String readFile(String file) throws IOException {

		return FileUtils.readFileToString(new File(file));

	}

	public static void saveFile(String file, String data) throws IOException {

		FileUtils.writeStringToFile(new File(file), data);
	}

	public static void deleteFile(String file) {

		(new File(file)).delete();
	}

	private static final Logger log = LogManager.getLogger(FileWork.class
			.getName());

	public static void deleteFolder(String path) throws IOException {
		log.info("Deleting " + path);
		if (!new File(path).exists())
			throw new IOException("Deleting - path does not exist");

		FileUtils.deleteDirectory(new File(path));

	}

	public static void checkFolder(String source, String destination)
			throws IOException {

		log.info("Testing source: " + source);
		log.info("Testing destination: " + destination);

		File d = new File(destination);
		File s = new File(source);

		if (d.exists() && d.isDirectory())
			throw new IOException("destination path exists!");

		if (!s.exists() || !s.isDirectory()) {
			throw new IOException("souce path does not exists");
		}

	}

	public static void copyFolder(String source, String destination)
			throws IOException {

		log.info("Copy source: " + source);
		log.info("Copy destination: " + destination);
		Runtime r = Runtime.getRuntime();

		Process p = r.exec("bash");
		new Thread(new E_rrorLogSyncPipe(p.getErrorStream(), log)).start();
		new Thread(new LogSyncPipe(p.getInputStream(), log)).start();

		OutputStream o = p.getOutputStream();

		o.write(("set -ex \n").getBytes());
		o.flush();
		o.write(("mkdir -p " + destination + " \n").getBytes());
		o.flush();
		o.write(("cp -r -f " + source + "/* " + destination + "\n").getBytes());
		o.flush();
		o.write(("cd " + destination + "\n").getBytes());
		o.write(("exit \n").getBytes());
		o.close();
		try {
			p.waitFor();
		} catch (InterruptedException e) {

			e.printStackTrace();
			throw new IOException(e.getMessage());
		}

		if (p.exitValue() != 0) {
			log.error("Copy process exited with non zero code " + p.exitValue());
			throw new IOException("Copy process exited with non zero code "
					+ p.exitValue());
		}

		log.info("Copy process exit code " + p.exitValue());

	}
}
