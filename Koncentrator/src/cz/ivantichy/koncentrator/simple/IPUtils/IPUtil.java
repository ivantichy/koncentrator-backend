package cz.ivantichy.koncentrator.simple.IPUtils;

public class IPUtil {

	public static long IPtoLong(String ip) throws Exception {

		ip = ip.trim();
		String[] parts = ip.split("[.]");
		if (parts.length != 4)
			throw new Exception("Invalid IP: " + ip);

//		System.out.println("IP parts " + parts[0] + " . " + parts[1] + " . "
	//			+ parts[2] + " . " + parts[3]);

		long out = Integer.parseInt(parts[3].trim());

		out += Long.parseLong(parts[2].trim()) * 256;
		out += Long.parseLong(parts[1].trim()) * 256 * 256;
		out += Long.parseLong(parts[0].trim()) * 256 * 256 * 256;
	//	System.out.println("To long IP: " + out);
		return out;

	}

	public static String LongToIP(long ip) {

	//	System.out.println("Long IP: " + ip);

		// can be written effectively.. but I like it this way

		String out = String.valueOf(ip / (256 * 256 * 256)) + ".";
		out += String.valueOf((ip % (256 * 256 * 256)) / (256 * 256)) + ".";
		out += String.valueOf((ip % (256 * 256)) / (256)) + ".";
		out += String.valueOf(ip % (256));

	//	System.out.println("Reverted to real IP: " + out);
		return out;

	}

}
