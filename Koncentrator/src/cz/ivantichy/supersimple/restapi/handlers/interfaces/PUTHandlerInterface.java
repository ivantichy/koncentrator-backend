package cz.ivantichy.supersimple.restapi.handlers.interfaces;

import java.io.IOException;

import cz.ivantichy.supersimple.restapi.server.PUTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public interface PUTHandlerInterface {

	public Response handlePUT(PUTRequest req) throws IOException;

}
