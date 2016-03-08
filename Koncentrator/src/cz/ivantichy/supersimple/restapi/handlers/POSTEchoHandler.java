package cz.ivantichy.supersimple.restapi.handlers;

import cz.ivantichy.supersimple.restapi.handlers.interfaces.POSTHandlerInterface;
import cz.ivantichy.supersimple.restapi.server.POSTRequest;
import cz.ivantichy.supersimple.restapi.server.Response;

public class POSTEchoHandler implements POSTHandlerInterface {

	@Override
	public Response handlePOST(POSTRequest req) {

		return new Response("Echo: " + req.postdata, true);
	}
}
