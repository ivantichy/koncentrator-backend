package cz.ivantichy.supersimple.restapi.handlers.interfaces;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public interface POSTHandlerInterface {

	public Response handlePOST(POSTRequest req) throws IOException;

}
