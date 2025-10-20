package com.axonivy.connector.stripe.test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.axonivy.connector.stripe.service.PaymentService;
import com.axonivy.connector.stripe.test.context.MultiEnvironmentContextProvider;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.stripe.api.client.PaymentLink;
import com.stripe.api.client.PaymentLinksResourceListLineItems;
import com.stripe.exception.StripeException;

import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.Ivy;

@ExtendWith(MultiEnvironmentContextProvider.class)
@IvyProcessTest(enableWebServer = true)
public class ProcessTest {

	private static final String CHECKOUT_SESSION = "/stripe-connector-test/19565E5AC96A55B3/start.ivp?priceId=price_1QeSG6LaeAomYD3LfEHlcjEr&quantity=2&secret=%s&publicKey=%s";

	private static final String LOG_IN = "/stripe-connector-test/1946E968E7BAB355/logInUser.ivp?username=Developer&password=Developer";

	@BeforeAll
	public static void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-web-security");
		Configuration.reportsFolder = null;
		Configuration.savePageSource = false;
		Configuration.screenshots = false;
		Configuration.browserCapabilities = options;
		Configuration.browser = "chrome";
	}

	@AfterAll
	public static void cleanup() {
		Ivy.var().reset("stripe.auth.secretKey");
		Ivy.var().reset("stripe.auth.publishableKey");
	}

	@TestTemplate
	public void testCreateCheckoutSession() {
		String processPath = CHECKOUT_SESSION.formatted(System.getProperty("secretKey"), System.getProperty("publishableKey"));
		open(EngineUrl.createProcessUrl(LOG_IN));
		open(EngineUrl.createProcessUrl(processPath));
		$(By.id("form:resquest-button")).shouldBe(enabled).click();

		SelenideElement iframe = $(By.tagName("iframe")).shouldBe(visible, Duration.ofSeconds(300));
		Selenide.switchTo().frame(iframe);

		clickAndInputValue("email", "Octopus@gmail.com");
		clickAndInputValue("cardNumber", "4242424242424242");
		clickAndInputValue("cardExpiry", "04/31");
		clickAndInputValue("cardCvc", "115");
		clickAndInputValue("billingName", "test");

		$("#billingCountry").shouldBe(visible).selectOption("Vietnam");
		$(By.className("SubmitButton")).shouldBe(enabled).click();

		WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(300));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

		$(By.className("PaymentSuccess")).shouldBe(visible);
	}

	@Test
	public void testPaymentLink() throws StripeException {
		// Test creating a payment link with valid parameters
		PaymentService paymentService = PaymentService.getInstance();
        PaymentLink paymentLink = paymentService.createPaymentLink(System.getProperty("priceId"), 1L);

		assertThat(paymentLink).isNotNull();
		assertThat(paymentLink.getId()).isNotNull();
		assertThat(paymentLink.getUrl()).isNotNull();
		assertThat(paymentLink.isActive()).isNotNull();

		// Now retrieve the payment link
		PaymentLink retrievedLink = paymentService.retrievePaymentLink(paymentLink.getId());

		assertThat(retrievedLink).isNotNull();
		assertThat(retrievedLink.getId()).isEqualTo(paymentLink.getId());
		assertThat(retrievedLink.getUrl()).isNotNull();
		assertThat(retrievedLink.isActive()).isNotNull();

		// Now retrieve the line items
		PaymentLinksResourceListLineItems lineItems = paymentService.retrievePaymentLinkLineItems(paymentLink.getId());

		assertThat(lineItems).isNotNull();
		assertThat(lineItems.getData()).isNotEmpty();

		// Test deactivating the payment link
		PaymentLink deactivatedLink = paymentService.setPaymentLinkActive(paymentLink.getId(), false);

		assertThat(deactivatedLink).isNotNull();
		assertThat(deactivatedLink.getId()).isEqualTo(paymentLink.getId());
		assertThat(deactivatedLink.isActive()).isFalse();

		// Test reactivating the payment link
		PaymentLink reactivatedLink = paymentService.setPaymentLinkActive(paymentLink.getId(), true);

		assertThat(reactivatedLink).isNotNull();
		assertThat(reactivatedLink.getId()).isEqualTo(paymentLink.getId());
		assertThat(reactivatedLink.isActive()).isTrue();
	}

	private void clickAndInputValue(String id, String value) {
		$(By.id(id)).click();
		$(By.id(id)).sendKeys(value);
	}
}
