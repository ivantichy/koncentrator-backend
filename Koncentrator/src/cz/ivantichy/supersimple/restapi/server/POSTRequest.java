package cz.ivantichy.supersimple.restapi.server;

import java.util.HashMap;

public class POSTRequest {

	// TODO Abstraltni req.

	public HashMap<String, String> getparams;
	public HashMap<String, String> headers;
	public String decoratedurl;
	public String protocol;
	public String postdata;

	public POSTRequest(HashMap<String, String> getparams,
			HashMap<String, String> headers, String decoratedurl,
			String postdata, String protocol) {
		super();
		this.getparams = getparams;
		this.headers = headers;
		this.decoratedurl = decoratedurl;
		this.postdata = postdata;
		this.protocol = protocol;
	}

}
