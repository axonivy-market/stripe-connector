package com.axonivy.connector.stripe.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
  
  @GET
  @Path("/read")
  @Produces(MediaType.APPLICATION_JSON)
  public String readFile(@QueryParam("name") String fileName) throws IOException {
      File file = new File("/app/data/" + fileName);
      return new String(Files.readAllBytes(file.toPath()));
  }
  
}
