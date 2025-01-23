package com.axonivy.connector.stripe.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.axonivy.connector.stripe.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@Path("/stripe")
public class StripeApiEndpoint {
  @POST
  @Path("/create-checkout-session/{priceId}/{quantity}")
  @Produces("application/json")
  public Response createCheckoutSession(@PathParam("priceId") String priceId, @PathParam("quantity") Long quantity)
      throws StripeException {
    Session session = PaymentService.getInstance().createCheckoutSession(priceId, quantity);
    return Response.ok("{\"clientSecret\":\"" + session.getClientSecret() + "\"}").build();
  }
}
