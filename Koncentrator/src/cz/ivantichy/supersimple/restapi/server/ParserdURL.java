package cz.ivantichy.supersimple.restapi.server;

class ParsedURL {

	public ParsedURL(String decoratedurl, String getparams) {
		super();
		this.decoratedurl = decoratedurl;
		this.getparams = getparams;
		// this.protocol = protocol;
	}

	private String decoratedurl;
	private String getparams;

	// String protocol;

	public String getDecoratedurl() {
		return decoratedurl;
	}

	public String getGetparams() {
		return getparams;
	}

}