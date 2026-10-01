package com.axonivy.connector.stripe.controller;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.exception.StripeException;

@Path("/stripe")
public class StripeApiEndpoint {

  @POST
  @Path("/create-checkout-session/{priceId}/{quantity}")
  @Produces("application/json")
  public Response createCheckoutSession(@PathParam("priceId") String priceId,
      @PathParam("quantity") Long quantity) throws StripeException {
    String clientSecret = PaymentService.getInstance().getClientSecret(priceId, quantity);
    return Response.ok("{\"clientSecret\":\"" + clientSecret + "\"}").build();
  }
}
