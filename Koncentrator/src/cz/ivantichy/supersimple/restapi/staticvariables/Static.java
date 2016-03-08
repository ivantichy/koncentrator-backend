package cz.ivantichy.supersimple.restapi.staticvariables;

import java.util.regex.Pattern;

import org.json.JSONObject;

public class Static {

	public static final Pattern STRING_TYPE_CHECK = Pattern
			.compile("([a-zA-Z0-9_-]|[.])*");
	public static final Pattern SAFE_STRING_TYPE_CHECK = Pattern
			.compile("[a-zA-Z0-9-_]*");

	public static final Pattern BASE64_STRING_TYPE_CHECK = Pattern
			.compile("[a-zA-Z0-9=]*");
	public static final Pattern IP_ADDRESS_TYPE_CHECK = Pattern
			.compile("[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}([/][0-9]{1,2}){0,1}");

	public static final Pattern NUMBER_TYPE_CHECK = Pattern
			.compile("[-,.0-9]*");
	public static final Pattern SEPARATOR = Pattern.compile("[\t]|[ ]");
	public static final Pattern COLON = Pattern.compile("[:]");
	public static final Pattern ALLOWEDTYPES = Pattern
			.compile("(string)|(number)|(safe_string)|(ip_address)|(base64_safe_string)");
	public static final String STRING_TYPE = "string";
	public static final String SAFE_STRING_TYPE = "safe_string";
	public static final String BASE64_SAFE_STRING_TYPE = "base64_safe_string";
	public static final String IP_ADDRESS_TYPE = "ip_address";
	public static final String NUMBER_TYPE = "number";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String CMDSPACE = " ";
	public static final Pattern COMMENT = Pattern.compile("^[#;].*");
	public static final String OUTPUT_NONE = "NONE";
	public static final String OUTPUT_CODE = "CODE";
	public static final String OUTPUT_TEXT = "TEXT";
	public static final String OUTPUT_JSON = "JSON";

	public static final long EXECUTIONTIMEOUT = 180000;

	public static final Pattern SPACE = Pattern.compile("[ ]");
	public final static int BACKLOG = 20;
	public final static int TIMEOUT = 180000;
	public final static String ENCODING = "UTF-8";
	public final static String OPENVPNLOCATION = "/etc/openvpn/";
	public final static String FOLDERSEPARATOR = "/";
	public final static String GENERATEFOLDER = "generate/";
	public final static String INSTANCESFOLDER = "instances/";
	public static final String RSALOCATION = "/etc/openvpn/easy-rsa/2.0/";
	public static final String TUN_BASIC_TYPE = "tun-basic";
	public static final String TAP_ADVANCED_TYPE = "tap-advanced";
}
