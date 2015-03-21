package cz.ivantichy.supersimple.restapi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cz.ivantichy.supersimple.restapi.handlers.GETEchoHandler;
import cz.ivantichy.supersimple.restapi.handlers.POSTEchoHandler;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.DELETEHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.GETHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.handlers.interfaces.PUTHandlerInterface;
import cz.ivantichy.supersimple.restapi.staticvariables.Static;

public class Server {

	private static final Logger log = LogManager.getLogger(Server.class
			.getName());

	private static RequestParser requestparser = new RequestParser();

	private HashMap<String, POSTHandlerInterface> posthandlers = new HashMap<String, POSTHandlerInterface>();
	private HashMap<String, GETHandlerInterface> gethandlers = new HashMap<String, GETHandlerInterface>();
	private HashMap<String, DELETEHandlerInterface> deletehandlers = new HashMap<String, DELETEHandlerInterface>();
	private HashMap<String, PUTHandlerInterface> puthandlers = new HashMap<String, PUTHandlerInterface>();
	private static ServerSocket welcomeSocket;

	public synchronized void registerPOSTHandler(String url,
			POSTHandlerInterface h) throws IOException {
		posthandlers.put(requestparser.parseURL(url).getDecoratedurl(), h);
		log.info("Registering POST: " + url);
	}

	public synchronized void registerGETHandler(String url,
			GETHandlerInterface h) throws IOException {

		log.info("Registering GET: " + url);
		gethandlers.put(requestparser.parseURL(url).getDecoratedurl(), h);
	}

	public synchronized void registerPUTHandler(String url,
			PUTHandlerInterface h) throws IOException {

		log.info("Registering PUT: " + url);
		puthandlers.put(requestparser.parseURL(url).getDecoratedurl(), h);
	}

	public synchronized void registerDELETEHandler(String url,
			DELETEHandlerInterface h) throws IOException {
		deletehandlers.put(requestparser.parseURL(url).getDecoratedurl(), h);
		log.info("Registering DELETE: " + url);
	}

	private synchronized GETHandlerInterface getGETHandler(String decoratedurl) {

		return gethandlers.get(decoratedurl);

	}

	private synchronized POSTHandlerInterface getPOSTHandler(String decoratedurl) {

		return posthandlers.get(decoratedurl);

	}

	private synchronized DELETEHandlerInterface getDELETEHandler(
			String decoratedurl) {
		return deletehandlers.get(decoratedurl);

	}

	private synchronized PUTHandlerInterface getPUTHandler(String decoratedurl) {
		return puthandlers.get(decoratedurl);

	}

	private String handleGET(String url, String headers, String protocol) {
		try {

			log.debug("handleGET URL: " + url);
			ParsedURL parsedurl = requestparser.parseURL(url);
			GETHandlerInterface h;
			if ((h = getGETHandler(parsedurl.getDecoratedurl())) != null) {

				log.info("GETHandler found: " + parsedurl.getDecoratedurl());
				Response response = h.handleGET(requestparser.parseGETRequest(
						parsedurl, headers, protocol));
				log.debug("GETHandler - response: " + response.text);

				if (!response.ok) {
					log.error("Response object has false OK status, returning HTTP 500");
					return HTTPResponse.getResponse500(response.text);

				}
				return HTTPResponse.getResponse(response.text);
			}

			log.warn("GETHandler - appropriate handler not found, returning HTTP 404, for:'"
					+ parsedurl.getDecoratedurl() + "'");

			return HTTPResponse.getEmpty404();
		} catch (Exception e) {
			log.error("GET request handling failed, returning HTTP 500, Exception: "
					+ e.getMessage());
			return HTTPResponse.getEmpty500();

		}

	}

	private synchronized String handlePOST(String url, String headers,
			String postload, String protocol) {
		try {
			log.info("handlePOST url: " + url);
			ParsedURL parsedurl = requestparser.parseURL(url);
			POSTHandlerInterface h;
			if ((h = getPOSTHandler(parsedurl.getDecoratedurl())) != null) {

				log.info("POSTHandler found: " + parsedurl.getDecoratedurl());
				log.debug("POSTHandler - postload: " + postload);

				Response response = h.handlePOST(requestparser
						.parsePOSTRequest(parsedurl, headers, postload,
								protocol));

				log.debug("POSTHandler - response: " + response.text);

				if (!response.ok) {
					log.error("Response object has false OK status, returning HTTP 500");
					return HTTPResponse.getResponse500(response.text);

				}

				return HTTPResponse.getResponse(response.text);
			}

			log.warn("POSTHandler - appropriate handler not found, returning HTTP 404, for:'"
					+ parsedurl.getDecoratedurl() + "'");

			return HTTPResponse.getEmpty404();
		} catch (Exception e) {
			log.error("POST request handling failed, returning HTTP 500, Exception: "
					+ e.getMessage());
			return HTTPResponse.getEmpty500();

		}

	}

	private String handlePUT(String url, String headers, String putload,
			String protocol) {

		try {
			log.info("handlePUT url: " + url);
			ParsedURL parsedurl = requestparser.parseURL(url);
			PUTHandlerInterface h;
			if ((h = getPUTHandler(parsedurl.getDecoratedurl())) != null) {

				log.info("PUTHandler found: " + parsedurl.getDecoratedurl());
				log.debug("PUTHandler - postload: " + putload);

				Response response = h.handlePUT(requestparser.parsePUTRequest(
						parsedurl, headers, putload, protocol));

				log.debug("PUTHandler - response: " + response.text);

				if (!response.ok) {
					log.error("Response object has false OK status, returning HTTP 500");
					return HTTPResponse.getResponse500(response.text);

				}

				return HTTPResponse.getResponse(response.text);
			}

			log.warn("PUTHandler - appropriate handler not found, returning HTTP 404, for:'"
					+ parsedurl.getDecoratedurl() + "'");

			return HTTPResponse.getEmpty404();
		} catch (Exception e) {
			log.error("PUT request handling failed, returning HTTP 500, Exception: "
					+ e.getMessage());
			return HTTPResponse.getEmpty500();

		}

	}

	private String handleDELETE(String url, String headers, String protocol) {

		try {

			log.debug("handleDELETE full URL: " + url);
			ParsedURL parsedurl = requestparser.parseURL(url);
			DELETEHandlerInterface h;
			if ((h = getDELETEHandler(parsedurl.getDecoratedurl())) != null) {

				log.info("DELETEHandler found: " + parsedurl.getDecoratedurl());
				Response response = h.handleDELETE(requestparser
						.parseDELETERequest(parsedurl, headers, protocol));
				log.debug("DELETEHandler - response: " + response.text);

				if (!response.ok) {
					log.error("Response object has false OK status, returning HTTP 500");
					return HTTPResponse.getResponse500(response.text);

				}
				return HTTPResponse.getResponse(response.text);
			}

			log.warn("DELETEHandler - appropriate handler not found, returning HTTP 404, for:'"
					+ parsedurl.getDecoratedurl() + "'");

			return HTTPResponse.getEmpty404();

		} catch (Exception e) {
			log.error("DELETE request handling failed, returning HTTP 500, Exception: "
					+ e.getMessage());
			return HTTPResponse.getEmpty500();

		}

	}

	public void exec(Socket connectionSocket) throws IOException {
		connectionSocket.setSoTimeout(Static.TIMEOUT);
		log.info("Timeout set to: " + Static.TIMEOUT);
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(
				connectionSocket.getInputStream()));

		OutputStreamWriter outToClient = new OutputStreamWriter(
				connectionSocket.getOutputStream());
		String firstline = inFromClient.readLine();
		log.info("Firstline: " + firstline);

		StringBuffer headers = new StringBuffer();
		String line;
		long contentlenght = 0;

		line = inFromClient.readLine();
		if (line == null)
			return;
		while (line.length() > 0) {
			headers.append(line);
			if (line.toLowerCase().startsWith("content-length:")) {

				contentlenght = Long.parseLong(line.split(":")[1].trim());
				log.info("Contenth length: " + contentlenght);
			}
			line = inFromClient.readLine();
		}

		if (firstline.toLowerCase().startsWith("post")) {
			log.info("Handling POST");
			StringBuffer post = new StringBuffer();

			while (contentlenght > 0) {
				contentlenght--;
				post.append((char) inFromClient.read());
			}
			String[] spl = Static.SPACE.split(firstline);

			outToClient.write(handlePOST(spl[1], headers.toString(),
					post.toString(), spl[2]));

			outToClient.flush();

		}
		if (firstline.toLowerCase().startsWith("put")) {
			log.info("Handling PUT");
			StringBuffer put = new StringBuffer();

			while (contentlenght > 0) {
				contentlenght--;
				put.append((char) inFromClient.read());
			}
			String[] spl = Static.SPACE.split(firstline);

			outToClient.write(handlePUT(spl[1], headers.toString(),
					put.toString(), spl[2]));

			outToClient.flush();

		}
		if (firstline.toLowerCase().startsWith("get")) {
			log.info("Handling GET");

			String[] spl = Static.SPACE.split(firstline);
			outToClient.write(handleGET(spl[1], headers.toString(), spl[2]));
			outToClient.flush();
		}

		if (firstline.toLowerCase().startsWith("delete")) {
			log.info("Handling DELETE");

			String[] spl = Static.SPACE.split(firstline);
			outToClient.write(handleDELETE(spl[1], headers.toString(), spl[2]));
			outToClient.flush();
		}

		try {
			inFromClient.close();
		} catch (Exception e) {
			log.error("Failed to close inputstream. " + e.toString(), e);
		}
		try {
			outToClient.close();
		} catch (Exception e) {
			log.error("Failed to close outputstream. " + e.toString(), e);

		}
		try {
			connectionSocket.close();
		} catch (Exception e) {
			log.error("Failed to close socket. " + e.toString(), e);
		}

	}

	public void listen(int port) throws IOException {

		welcomeSocket = new ServerSocket(port, Static.BACKLOG);
		while (true) {

			try {
				log.debug("Going to listen on port " + port);
				Socket connectionSocket = welcomeSocket.accept();
				exec(connectionSocket);

			}

			catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public static void main(String argv[]) throws Exception {

		Server server = new Server();
		server.registerGETHandler("/echo", new GETEchoHandler());
		server.registerPOSTHandler("/echo", new POSTEchoHandler());

		server.listen(6789);

	}
}
