package com.axonivy.connector.stripe.test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.axonivy.connector.stripe.test.context.MultiEnvironmentContextProvider;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

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

	private void clickAndInputValue(String id, String value) {
		$(By.id(id)).click();
		$(By.id(id)).sendKeys(value);
	}

}
