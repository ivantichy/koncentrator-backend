package cz.ivantichy.supersimple.restapi.server;

import java.util.HashMap;

public class PUTRequest {

	// TODO Abstraltni req.

	public HashMap<String, String> getparams;
	public HashMap<String, String> headers;
	public String decoratedurl;
	public String protocol;
	public String putdata;

	public PUTRequest(HashMap<String, String> getparams,
			HashMap<String, String> headers, String decoratedurl,
			String putdata, String protocol) {
		super();
		this.getparams = getparams;
		this.headers = headers;
		this.decoratedurl = decoratedurl;
		this.putdata = putdata;
		this.protocol = protocol;
	}

}
