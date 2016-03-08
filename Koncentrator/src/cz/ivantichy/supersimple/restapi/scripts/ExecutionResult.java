package cz.ivantichy.supersimple.restapi.scripts;

public class ExecutionResult {

	private int code;
	private String text;

	public String getText() {
		return text;
	}

	public int getCode() {
		return code;
	}

	public ExecutionResult(int code, String text) {
		super();
		this.code = code;
		this.text = text;
	}

}
