package com.axonivy.connector.stripe;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.stripe.api.client.PaymentLink;


public class PaymentLinkDeserializer extends JsonDeserializer<PaymentLink> {
	private ObjectMapper mapper;

	public PaymentLinkDeserializer(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public PaymentLink deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JacksonException {
		JsonNode node = parser.readValueAsTree();
		return new PaymentLink();
	}
	
	@Override
	public PaymentLink deserializeWithType(JsonParser parser, DeserializationContext ctxt, TypeDeserializer typeDeserializer)
			throws IOException, JacksonException {
		JsonNode node = parser.readValueAsTree();
//		return new PaymentLink();
//		mapper.readValue(parser, PaymentLink.class);
		PaymentLink result =  new PaymentLink();
		result.setUrl(node.get("url").asText());
		
		return result;
//		return new PaymentLink();
	}

}
