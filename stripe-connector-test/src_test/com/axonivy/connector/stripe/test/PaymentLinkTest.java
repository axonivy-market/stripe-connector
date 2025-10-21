package com.axonivy.connector.stripe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.stripe.api.client.Item;
import com.stripe.api.client.PaymentLink;
import com.stripe.api.client.PaymentLinksResourceListLineItems;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.engine.client.sub.SubProcessCallResult;

public class PaymentLinkTest extends BaseTest {

  private static final BpmProcess PAYMENT_LINK_PROCESS = BpmProcess.path("paymentLink");
  private static final BpmElement CREATE_PAYMENT_LINK_START = PAYMENT_LINK_PROCESS.elementName("createPaymentLink(String,Number)");
  private static final BpmElement RETRIEVE_PAYMENT_LINK_START = PAYMENT_LINK_PROCESS.elementName("retrievePaymentLink(String)");
  private static final BpmElement RETRIEVE_PAYMENT_LINK_LINE_ITEMS_START = PAYMENT_LINK_PROCESS.elementName("retrievePaymentLinkLineItems(String)");
  private static final BpmElement SET_PAYMENT_LINK_ACTIVE_START = PAYMENT_LINK_PROCESS.elementName("setPaymentLinkActive(String,Boolean)");

  // REST client call elements to mock
  private static final BpmElement CREATE_PAYMENT_LINK_REST = PAYMENT_LINK_PROCESS.elementName("Create payment link");
  private static final BpmElement RETRIEVE_PAYMENT_LINK_REST = PAYMENT_LINK_PROCESS.elementName("Retrieve payment link");
  private static final BpmElement RETRIEVE_LINE_ITEMS_REST = PAYMENT_LINK_PROCESS.elementName("Retrieve payment link's line items");
  private static final BpmElement UPDATE_PAYMENT_LINK_REST = PAYMENT_LINK_PROCESS.elementName("Update payment link");

  private static final String MOCK_PRICE_ID = "price_1QeSG6LaeAomYD3LfEHlcjEr";
  private static final Long MOCK_QUANTITY = 1L;
  private static final String MOCK_PAYMENT_LINK_ID = "plink_test123";
  private static final String MOCK_PAYMENT_LINK_URL = "https://checkout.stripe.com/pay/plink_test123";

  @Test
  void createPaymentLink_mockedApi_returnsMockedResponse(BpmClient bpmClient) {
    // Prepare mock PaymentLink
    PaymentLink mockPaymentLink = createMockPaymentLink(MOCK_PAYMENT_LINK_ID, MOCK_PAYMENT_LINK_URL, true);

    // Mock the REST client call
    bpmClient.mock().element(CREATE_PAYMENT_LINK_REST).with(in -> {
      try {
        // Verify input parameters
        assertEquals(in.get("priceId"), MOCK_PRICE_ID);
        assertEquals(in.get("quantity"), MOCK_QUANTITY);

        // Set mock response
        in.set("paymentLink", mockPaymentLink);
        return in;
      } catch (NoSuchFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Execute the subprocess
    SubProcessCallResult result = bpmClient.start()
      .subProcess(CREATE_PAYMENT_LINK_START)
      .execute(MOCK_PRICE_ID, MOCK_QUANTITY)
      .subResult();

    // Assert the result
    PaymentLink resultPaymentLink = result.param("paymentLink", PaymentLink.class);
    assertNotNull(resultPaymentLink);
    assertEquals(MOCK_PAYMENT_LINK_ID, resultPaymentLink.getId());
    assertEquals(MOCK_PAYMENT_LINK_URL, resultPaymentLink.getUrl());
    assertTrue(resultPaymentLink.isActive());
  }

  @Test
  void retrievePaymentLink_mockedApi_returnsMockedResponse(BpmClient bpmClient) {
    // Prepare mock PaymentLink
    PaymentLink mockPaymentLink = createMockPaymentLink(MOCK_PAYMENT_LINK_ID, MOCK_PAYMENT_LINK_URL, true);

    // Mock the REST client call
    bpmClient.mock().element(RETRIEVE_PAYMENT_LINK_REST).with(in -> {
      try {
        // Verify input parameter
        assertEquals(in.get("paymentLinkId"), MOCK_PAYMENT_LINK_ID);

        // Set mock response
        in.set("paymentLink", mockPaymentLink);
        return in;
      } catch (NoSuchFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Execute the subprocess
    SubProcessCallResult result = bpmClient.start()
      .subProcess(RETRIEVE_PAYMENT_LINK_START)
      .execute(MOCK_PAYMENT_LINK_ID)
      .subResult();

    // Assert the result
    PaymentLink resultPaymentLink = result.param("paymentLink", PaymentLink.class);
    assertNotNull(resultPaymentLink);
    assertEquals(MOCK_PAYMENT_LINK_ID, resultPaymentLink.getId());
    assertEquals(MOCK_PAYMENT_LINK_URL, resultPaymentLink.getUrl());
    assertTrue(resultPaymentLink.isActive());
  }

  @Test
  void retrievePaymentLinkLineItems_mockedApi_returnsMockedResponse(BpmClient bpmClient) {
    // Prepare mock line items
    PaymentLinksResourceListLineItems mockLineItems = createMockLineItems();

    // Mock the REST client call
    bpmClient.mock().element(RETRIEVE_LINE_ITEMS_REST).with(in -> {
      try {
        // Verify input parameter
        assertEquals(in.get("paymentLinkId"), MOCK_PAYMENT_LINK_ID);

        // Set mock response
        in.set("paymentLinkLineItems", mockLineItems);
        return in;
      } catch (NoSuchFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Execute the subprocess
    SubProcessCallResult result = bpmClient.start()
      .subProcess(RETRIEVE_PAYMENT_LINK_LINE_ITEMS_START)
      .execute(MOCK_PAYMENT_LINK_ID)
      .subResult();

    // Assert the result
    PaymentLinksResourceListLineItems resultLineItems = result.param("paymentLinkLineItems", PaymentLinksResourceListLineItems.class);
    assertNotNull(resultLineItems);
    assertNotNull(resultLineItems.getData());
    assertTrue(resultLineItems.getData().size() > 0);
    assertEquals(2, resultLineItems.getData().size());
  }

  @Test
  void setPaymentLinkActive_deactivate_mockedApi_returnsMockedResponse(BpmClient bpmClient) {
    // Prepare mock PaymentLink (deactivated)
    PaymentLink mockPaymentLink = createMockPaymentLink(MOCK_PAYMENT_LINK_ID, MOCK_PAYMENT_LINK_URL, false);

    // Mock the REST client call
    bpmClient.mock().element(UPDATE_PAYMENT_LINK_REST).with(in -> {
      try {
        // Verify input parameters
        assertEquals(in.get("paymentLinkId"), MOCK_PAYMENT_LINK_ID);
        assertEquals(in.get("active"), false);

        // Set mock response
        in.set("paymentLink", mockPaymentLink);
        return in;
      } catch (NoSuchFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Execute the subprocess
    SubProcessCallResult result = bpmClient.start()
      .subProcess(SET_PAYMENT_LINK_ACTIVE_START)
      .execute(MOCK_PAYMENT_LINK_ID, false)
      .subResult();

    // Assert the result
    PaymentLink resultPaymentLink = result.param("paymentLink", PaymentLink.class);
    assertNotNull(resultPaymentLink);
    assertEquals(MOCK_PAYMENT_LINK_ID, resultPaymentLink.getId());
    assertFalse(resultPaymentLink.isActive());
  }

  @Test
  void setPaymentLinkActive_activate_mockedApi_returnsMockedResponse(BpmClient bpmClient) {
    // Prepare mock PaymentLink (activated)
    PaymentLink mockPaymentLink = createMockPaymentLink(MOCK_PAYMENT_LINK_ID, MOCK_PAYMENT_LINK_URL, true);

    // Mock the REST client call
    bpmClient.mock().element(UPDATE_PAYMENT_LINK_REST).with(in -> {
      try {
        // Verify input parameters
        assertEquals(in.get("paymentLinkId"), MOCK_PAYMENT_LINK_ID);
        assertEquals(in.get("active"), true);

        // Set mock response
        in.set("paymentLink", mockPaymentLink);
        return in;
      } catch (NoSuchFieldException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Execute the subprocess
    SubProcessCallResult result = bpmClient.start()
      .subProcess(SET_PAYMENT_LINK_ACTIVE_START)
      .execute(MOCK_PAYMENT_LINK_ID, true)
      .subResult();

    // Assert the result
    PaymentLink resultPaymentLink = result.param("paymentLink", PaymentLink.class);
    assertNotNull(resultPaymentLink);
    assertEquals(MOCK_PAYMENT_LINK_ID, resultPaymentLink.getId());
    assertTrue(resultPaymentLink.isActive());
  }

  // Helper methods
  private PaymentLink createMockPaymentLink(String id, String url, boolean active) {
    PaymentLink paymentLink = new PaymentLink();
    paymentLink.setId(id);
    paymentLink.setUrl(url);
    paymentLink.setActive(active);
    return paymentLink;
  }

  private PaymentLinksResourceListLineItems createMockLineItems() {
    PaymentLinksResourceListLineItems lineItems = new PaymentLinksResourceListLineItems();
    java.util.List<Item> mockData = Arrays.asList(
      createMockLineItem("item1", "Test Item 1"),
      createMockLineItem("item2", "Test Item 2")
    );
    lineItems.setData(mockData);
    return lineItems;
  }

  private Item createMockLineItem(String id, String description) {
    Item item = new Item();
    item.setId(id);
    item.setDescription(description);
    item.setQuantity(1);
    item.amountTotal(1000); // $10.00 in cents
    item.setAmountSubtotal(1000);
    item.setAmountTax(0);
    item.setAmountDiscount(0);
    item.setCurrency("usd");
    return item;
  }
}
