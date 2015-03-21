package cz.ivantichy.koncentrator.simple.iptables;

import cz.ivantichy.koncentrator.simple.IPUtils.IPUtil;

public class IPTablesRulesGen {

	private static long actip;

	public static String createRule4Net(String device, String net) {

		return "iptables -A FORWARD -s " + net + " -d " + net + " -i " + device
				+ " -j ACCEPT";

	}

	public static void main(String[] args) throws Exception {

		// test
		System.out.println(createRule4Net("tun0", "172.17.0.0/24"));

		if (args.length != 3) {
			System.out
					.println("Parametres START_IP COUNT_OF_ADDRESSES SIZE_OF_NET");
		}

		// test
		IPUtil.LongToIP(IPUtil.IPtoLong("245.235.225.215"));

		actip = IPUtil.IPtoLong(args[0]);
		for (int i = 0; i < Integer.valueOf(args[1]); i++) {

			System.out.println(createRule4Net("tun0", IPUtil.LongToIP(actip)
					+ "/" + args[2]));
			System.out.println(createRule4Net("tun1", IPUtil.LongToIP(actip)
					+ "/" + args[2]));

			shiftIP(Integer.parseInt(args[2]));

		}

	}

	private static void shiftIP(int size) {
		// TODO Auto-generated method stub
		actip += Math.pow(2, 32 - size);

	}

}
