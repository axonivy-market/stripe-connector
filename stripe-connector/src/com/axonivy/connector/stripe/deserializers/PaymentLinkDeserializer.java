package com.axonivy.connector.stripe.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.stripe.api.client.PaymentLink;

public class PaymentLinkDeserializer extends JsonDeserializer<PaymentLink> {

  public PaymentLinkDeserializer() {
    super();
  }

  @Override
  public PaymentLink deserialize(JsonParser parser, DeserializationContext context)
      throws IOException, JacksonException {
    return parsePaymentLink(parser);
  }

  @Override
  public PaymentLink deserializeWithType(JsonParser parser, DeserializationContext ctxt,
      TypeDeserializer typeDeserializer) throws IOException {
    return parsePaymentLink(parser);
  }

  private PaymentLink parsePaymentLink(JsonParser parser) throws IOException {
    JsonNode node = parser.readValueAsTree();
    PaymentLink result = new PaymentLink();
    result.setId(node.get("id").asText());
    result.setUrl(node.get("url").asText());
    result.setActive(node.get("active").asBoolean());
    return result;
  }

}
