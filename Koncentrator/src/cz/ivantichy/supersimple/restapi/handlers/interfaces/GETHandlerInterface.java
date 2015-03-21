package cz.ivantichy.supersimple.restapi.handlers.interfaces;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public interface GETHandlerInterface {

	public Response handleGET(GETRequest req) throws IOException;

}
