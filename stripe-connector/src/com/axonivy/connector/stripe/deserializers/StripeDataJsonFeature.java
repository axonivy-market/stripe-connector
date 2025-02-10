package com.axonivy.connector.stripe.deserializers;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

public class StripeDataJsonFeature extends JsonFeature {

  @Override
  public boolean configure(FeatureContext context) {
    JacksonJsonProvider provider = new JaxRsClientJson();
    
    // Ensure parent configuration is applied first
    super.configure(provider, context.getConfiguration());

    // Register the custom provider with proper priority
    context.register(provider, Priorities.ENTITY_CODER);
    
    return true;
  }

  public static class JaxRsClientJson extends JacksonJsonProvider {
    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      // Create a new ObjectMapper instance to avoid modifying shared mappers
      ObjectMapper mapper = new ObjectMapper();

      // Register existing configurations (optional: inherit from default)
      ObjectMapper defaultMapper = super.locateMapper(type, mediaType);
      defaultMapper.setVisibility(defaultMapper.getVisibilityChecker());
      defaultMapper.setDateFormat(defaultMapper.getDateFormat());
      if (defaultMapper != null) {
//        mapper.setSerializationInclusion(defaultMapper.());
//        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setVisibility(defaultMapper.getVisibilityChecker());
        mapper.setDateFormat(defaultMapper.getDateFormat());
      }

      // Register the Stripe-specific module
      defaultMapper.registerModule(new StripeDataTypeCustomizations());

      return defaultMapper;
    }
  }
}
