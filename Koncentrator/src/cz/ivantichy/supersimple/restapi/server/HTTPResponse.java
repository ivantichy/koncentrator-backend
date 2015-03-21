package cz.ivantichy.supersimple.restapi.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class HTTPResponse {

	static SimpleDateFormat sdf = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

	static {

		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public static synchronized String getTime() {

		return sdf.format(Calendar.getInstance().getTime());

	}

	public static String getResponse(String data) {

		return "HTTP/1.1 200 OK\r\n" +

		"Pragma: no-cache\r\n" +

		"Cache-Control: no-cache\r\n" + "Connection: close\r\n" +

		"Content-Type: text/plain\r\n" +

		"Date: " + getTime() + "\r\n" +

		"Content-Length: " + data.length() + "\r\n" + "\r\n" + data;

	}

	public static String getResponse500(String data) {

		return "HTTP/1.1 500 Internal Server Error\r\n" +

		"Pragma: no-cache\r\n" +

		"Cache-Control: no-cache\r\n" + "Connection: close\r\n" +

		"Content-Type: text/plain\r\n" +

		"Date: " + getTime() + "\r\n" +

		"Content-Length: " + data.length() + "\r\n" + "\r\n" + data;

	}

	public static String getEmpty200() {

		return "HTTP/1.1 200 OK\r\n" +

		"Pragma: no-cache\r\n" +

		"Cache-Control: no-cache\r\n" +

		"Connection: close\r\n" +

		"Content-Type: text/plain\r\n" +

		"Date: " + getTime() + "\r\n" +

		"Content-Length: 0\r\n" + "\r\n";

	}

	public static String getEmpty404() {

		return "HTTP/1.1 404 Not Found\r\n" +

		"Pragma: no-cache\r\n" +

		"Cache-Control: no-cache\r\n" + "Content-Type: text/plain\r\n" +

		"Date: " + getTime() + "\r\n" +

		"Content-Length: 0\r\n" +

		"Connection: close\r\n" + "\r\n";

	}

	public static String getEmpty500() {

		return "HTTP/1.1 500 Internal Server Error\r\n" +

		"Pragma: no-cache\r\n" +

		"Cache-Control: no-cache\r\n" + "Content-Type: text/plain\r\n" +

		"Date: " + getTime() + "\r\n" +

		"Content-Length: 0\r\n" +

		"Connection: close\r\n" + "\r\n";

	}
}
