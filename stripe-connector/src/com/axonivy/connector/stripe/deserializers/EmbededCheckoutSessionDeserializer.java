package com.axonivy.connector.stripe.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.stripe.api.client.CheckoutSession;

import ch.ivyteam.ivy.environment.Ivy;

public class EmbededCheckoutSessionDeserializer extends JsonDeserializer<CheckoutSession> {

  public EmbededCheckoutSessionDeserializer() {
    super();
  }

  @Override
  public CheckoutSession deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
    return parseCheckoutSession(parser);
  }

  @Override
  public CheckoutSession deserializeWithType(JsonParser parser, DeserializationContext ctxt,
      TypeDeserializer typeDeserializer) throws IOException {
    return (CheckoutSession) typeDeserializer.deserializeTypedFromObject(parser, ctxt);
  }

  private CheckoutSession parseCheckoutSession(JsonParser parser) throws IOException {
    JsonNode node = parser.readValueAsTree();
    CheckoutSession result = new CheckoutSession();
    if (node.has("client_secret") && !node.get("client_secret").isNull()) {
      result.setClientSecret(node.get("client_secret").asText());
    } else {
      Ivy.log().warn("client_secret field is missing or null in JSON");
    }

    return result;
  }
}
