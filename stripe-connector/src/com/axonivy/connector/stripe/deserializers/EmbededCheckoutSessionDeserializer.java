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
    Ivy.log().warn("deserialize() called (No Type Info)");
    return deserializeWithType(parser, ctxt, null);
  }

  @Override
  public CheckoutSession deserializeWithType(JsonParser parser, DeserializationContext ctxt,
      TypeDeserializer typeDeserializer) throws IOException {
    Ivy.log().warn("deserializeWithType() called (With Type Info)");
    return parseCheckoutSession(parser);
  }

  private CheckoutSession parseCheckoutSession(JsonParser parser) throws IOException {
    JsonNode node = parser.readValueAsTree();
    CheckoutSession result = new CheckoutSession();

    if (node.has("client_secret") && !node.get("client_secret").isNull()) {
      result.setClientSecret(node.get("client_secret").asText());
    } else {
      Ivy.log().warn("Warning: 'client_secret' field is missing or null in JSON");
    }

    return result;
  }
}
