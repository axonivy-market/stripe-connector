package com.axonivy.connector.stripe.test.context;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.axonivy.connector.stripe.test.constant.StripeCommonConstants;


public class MultiEnvironmentContextProvider implements TestTemplateInvocationContextProvider {

  @Override
  public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
    return Stream.of(new TestEnvironmentInvocationContext(StripeCommonConstants.REAL_CALL_CONTEXT_DISPLAY_NAME));
  }

  @Override
  public boolean supportsTestTemplate(ExtensionContext context) {
    return true;
  }
}
