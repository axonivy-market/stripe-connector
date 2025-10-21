package com.axonivy.connector.stripe.deserializers;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.stripe.api.client.Item;
import com.stripe.api.client.PaymentLinksResourceListLineItems;

public class PaymentLinksResourceListLineItemsDeserializer extends JsonDeserializer<PaymentLinksResourceListLineItems> {

  public PaymentLinksResourceListLineItemsDeserializer() {
    super();
  }

  @Override
  public PaymentLinksResourceListLineItems deserialize(JsonParser parser, DeserializationContext context)
      throws IOException, JacksonException {
    return parsePaymentLinksResourceListLineItems(parser);
  }

  @Override
  public PaymentLinksResourceListLineItems deserializeWithType(JsonParser parser, DeserializationContext ctxt,
      TypeDeserializer typeDeserializer) throws IOException {
    return parsePaymentLinksResourceListLineItems(parser);
  }

  private PaymentLinksResourceListLineItems parsePaymentLinksResourceListLineItems(JsonParser parser) throws IOException {
    JsonNode node = parser.readValueAsTree();
    PaymentLinksResourceListLineItems result = new PaymentLinksResourceListLineItems();
    JsonNode firstItem = node.get("data").get(0);
    Item item = new Item();
    item.setId(firstItem.get("id").asText());
    item.setQuantity(firstItem.get("quantity").asInt());
    item.amountTotal(firstItem.get("amount_total").asInt());
    item.setAmountSubtotal(firstItem.get("amount_subtotal").asInt());
    item.setAmountTax(firstItem.get("amount_tax").asInt());
    item.setAmountDiscount(firstItem.get("amount_discount").asInt());
    item.setCurrency(firstItem.get("currency").asText());
    item.setDescription(firstItem.get("description").asText());
    result.setData(Arrays.asList(item));
    return result;
  }

}
