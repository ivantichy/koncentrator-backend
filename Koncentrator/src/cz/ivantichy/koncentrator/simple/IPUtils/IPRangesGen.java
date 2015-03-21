package cz.ivantichy.koncentrator.simple.IPUtils;

public class IPRangesGen {

	public static void main(String[] args) throws Exception {

		long l = IPUtil.IPtoLong("172.17.0.0");
		int ip_range_id = 1;
		int ip_address_id = 1;

		for (int subvpntype_id = 1; subvpntype_id < 6; subvpntype_id++) {

			for (int i = 1; i < 1025; i++) {

				String ip_range = IPUtil.LongToIP(l) + "/26";
				System.out
						.println("insert into ip_range (id, ip_range, subvpntype_id) values ("
								+ ip_range_id
								+ ",'"
								+ ip_range
								+ "',"
								+ subvpntype_id + ");");

				long l2 = l;
				for (int j = 0; j < 16; j++) {
					l2++;
					String ip_address_remote = IPUtil.LongToIP(l2);
					l2++;
					String ip_address_local = IPUtil.LongToIP(l2);
					l2 += 2;
					System.out
							.println("insert into ip_address (id, ip_address_remote, ip_address_local, ip_range_id) values ("
									+ ip_address_id++
									+ ",'"
									+ ip_address_remote
									+ "','"
									+ ip_address_local
									+ "',"
									+ ip_range_id
									+ ");");

				}

				l = l + 64;
				ip_range_id++;

			}
		}

	}
}
