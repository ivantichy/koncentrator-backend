package cz.ivantichy.supersimple.restapi.server;

import java.util.HashMap;

public class GETRequest {

	public HashMap<String, String> getparams;
	public HashMap<String, String> headers;
	public String decoratedurl;
	public String protocol;

	public GETRequest(HashMap<String, String> getparams,
			HashMap<String, String> headers, String decoratedurl,
			String protocol) {
		super();
		this.getparams = getparams;
		this.headers = headers;
		this.decoratedurl = decoratedurl;
		this.protocol = protocol;
	}

}
