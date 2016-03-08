package cz.ivantichy.supersimple.restapi.handlers;

import cz.ivantichy.supersimple.restapi.handlers.interfaces.GETHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.GETRequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public class GETEchoHandler implements GETHandlerInterface {

	@Override
	public Response handleGET(GETRequest req) {
		return new Response("Echo: " + req.decoratedurl, true);
	}

}
