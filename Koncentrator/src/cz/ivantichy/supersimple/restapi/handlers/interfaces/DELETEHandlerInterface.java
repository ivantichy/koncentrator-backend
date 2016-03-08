package cz.ivantichy.supersimple.restapi.handlers.interfaces;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.server.DELETERequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public interface DELETEHandlerInterface {

	public Response handleDELETE(DELETERequest req) throws IOException;

}
