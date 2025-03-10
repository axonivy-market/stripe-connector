package com.axonivy.connector.stripe.deserializers;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

public class StripeDataJsonFeature extends JsonFeature {

  @Override
  public boolean configure(FeatureContext context) {
    JacksonJsonProvider provider = new JaxRsClientJson();
    configure(provider, context.getConfiguration());
    context.register(provider, Priorities.ENTITY_CODER);
    return true;
  }

  public static class JaxRsClientJson extends JacksonJsonProvider {
    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
      mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      mapper.setSerializationInclusion(Include.NON_NULL);
      mapper.registerModule(new StripeDataTypeCustomizations());
      return mapper;
    }
  }
}
