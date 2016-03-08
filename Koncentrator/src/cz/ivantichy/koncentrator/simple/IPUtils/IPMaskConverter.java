package cz.ivantichy.koncentrator.simple.IPUtils;

import java.io.IOException;

public class IPMaskConverter {

	public static String rangeToMask(String ip_range) throws IOException {

		String mask = ip_range.split("[/]")[1];
		return shortToLong(mask);

	}

	public static String rangeToIPAddress(String ip_range) throws IOException {

		return ip_range.split("[/]")[0];

	}

	public static String maskToRange(String ip_adress, String ip_mask) throws IOException {

		return ip_adress + "/" + longToShort(ip_mask);
	}

	private static String shortToLong(String s) throws IOException {

		switch (s.trim()) {

		case "32":
			return "255.255.255.255";
		case "30":
			return "255.255.255.252";
		case "28":
			return "255.255.255.240";
		case "26":
			return "255.255.255.192";
		case "25":
			return "255.255.255.128";
		case "24":
			return "255.255.255.0";
		case "16":
			return "255.255.0.0";
		case "12":
			return "255.240.0.0";

		}

		throw new IOException("wrong mask: " + s);

	}

	private static String longToShort(String l) throws IOException {

		switch (l.trim()) {

		case "255.255.255.255":
			return "32";
		case "255.255.255.252":
			return "30";
		case "255.255.255.240":
			return "28";
		case "255.255.255.192":
			return "26";
		case "255.255.255.128":
			return "25";
		case "255.255.255.0":
			return "24";
		case "255.255.0.0":
			return "16";
		case "255.240.0.0":
			return "12";

		}

		throw new IOException("wrong mask: " + l);

	}
}
