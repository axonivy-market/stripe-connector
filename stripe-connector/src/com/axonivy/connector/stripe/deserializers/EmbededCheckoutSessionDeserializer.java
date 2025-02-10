package com.axonivy.connector.stripe.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.stripe.api.client.CheckoutSession;


public class EmbededCheckoutSessionDeserializer extends JsonDeserializer<CheckoutSession> {

  public EmbededCheckoutSessionDeserializer() {
    super();
  }

  @Override
  public CheckoutSession deserialize(JsonParser parser, DeserializationContext arg1)
      throws IOException, JacksonException {
    JsonNode node = parser.readValueAsTree();
    CheckoutSession result = new CheckoutSession();
    result.setClientSecret(node.get("client_secret").asText());
    return result;
  }

//  @Override
//  public CheckoutSession deserializeWithType(JsonParser parser, DeserializationContext ctxt,
//      TypeDeserializer typeDeserializer) throws IOException, JacksonException {
//    JsonNode node = parser.readValueAsTree();
//    CheckoutSession result = new CheckoutSession();
//    result.setClientSecret(node.get("client_secret").asText());
//    return result;
//  }
}
