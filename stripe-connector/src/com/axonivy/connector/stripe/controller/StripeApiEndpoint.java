package com.axonivy.connector.stripe.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.exception.StripeException;

@Path("/stripe")
public class StripeApiEndpoint {

  @POST
  @Path("/create-checkout-session/openapi/{priceId}/{quantity}")
  @Produces("application/json")
  public Response createCheckoutSessionViaOpenApi(@PathParam("priceId") String priceId,
      @PathParam("quantity") Long quantity) throws StripeException {
    String clientSecret = PaymentService.getInstance().getClientSecretViaOpenApi(priceId, quantity);
    return Response.ok("{\"clientSecret\":\"" + clientSecret + "\"}").build();
  }
}
