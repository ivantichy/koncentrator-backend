package cz.ivantichy.supersimple.restapi.config;

import java.util.LinkedHashMap;

public class ConfigElement {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public LinkedHashMap<String, String> getAruments() {
		return aruments;
	}

	public void setAruments(LinkedHashMap<String, String> aruments) {
		this.aruments = aruments;
	}

	private String url;
	private String output;
	private String script;

	private LinkedHashMap<String, String> aruments;

	// zalezi na poradi !!!

	public ConfigElement(String type, String url, String output, String script,
			LinkedHashMap<String, String> aruments) {
		super();
		this.type = type;
		this.url = url;
		this.output = output;
		this.script = script;
		this.aruments = aruments;
	}

	@Override
	public String toString() {
		return "ConfigElement [type=" + type + ", url=" + url + ", output="
				+ output + ", script=" + script + ", aruments=" + aruments
				+ "]";
	}

}
