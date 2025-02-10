package com.axonivy.connector.stripe.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.api.client.PaymentLink;

public class PaymentLinkDeserializer extends JsonDeserializer<PaymentLink> {

  public PaymentLinkDeserializer() {
    super();
  }

  @Override
  public PaymentLink deserialize(JsonParser parser, DeserializationContext context)
      throws IOException, JacksonException {
    JsonNode node = parser.readValueAsTree();
    PaymentLink result = new PaymentLink();
    result.setUrl(node.get("url").asText());
    return result;
  }

}
