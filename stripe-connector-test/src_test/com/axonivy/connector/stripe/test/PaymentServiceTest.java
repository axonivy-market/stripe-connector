package com.axonivy.connector.stripe.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.axonivy.connector.stripe.test.context.MultiEnvironmentContextProvider;
import com.axonivy.connector.stripe.test.utils.StripeUtils;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;

@ExtendWith(MultiEnvironmentContextProvider.class)
@IvyProcessTest
public class PaymentServiceTest {

  private static final String CHECKOUT_SESSION = "/stripe-connector-demo/194EE3B279B2B3BD/start.ivp";
  private static final String PAYMENTLINK = "/stripe-connector-demo/194ED891756337A7/start.ivp";
  private static final String LOG_IN =
      "/stripe-connector-test/1946E968E7BAB355/logInUser.ivp?username=Developer&password=Developer";

  @BeforeEach
  void setup(AppFixture appFixture) {
    StripeUtils.setUpConfigForApiTest(appFixture);
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless"); // Run in headless mode (no GUI)
    options.addArguments("--disable-web-security"); // Disable web security (for cross-origin tests)
    Configuration.browserCapabilities = options;
    Configuration.browser = "chrome";
  }


  @TestTemplate
  void testCreateCheckoutSession() {
	  open(EngineUrl.createProcessUrl(LOG_IN));
	  open(EngineUrl.createProcessUrl(CHECKOUT_SESSION));

	  $(By.id("form:price")).sendKeys("price_1QeSG6LaeAomYD3LfEHlcjEr");
	  $(By.id("form:quantity_input")).sendKeys("2");
	  $(By.id("form:resquest-button")).click();

	  SelenideElement iframe = $(By.tagName("iframe")).shouldBe(visible, Duration.ofSeconds(10));
      Selenide.switchTo().frame(iframe);

	  clickAndInputValue("email", "Octopus@gmail.com");
	  clickAndInputValue("cardNumber", "4242424242424242");
	  clickAndInputValue("cardExpiry", "04/31");
	  clickAndInputValue("cardCvc", "115");
	  clickAndInputValue("billingName", "test");

	  $("#billingCountry").shouldBe(visible).selectOption("Vietnam");
	  $(By.className("SubmitButton")).shouldBe(enabled).click();
	  
	  WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
	  Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	  alert.accept();
	  
	  $(By.className("PaymentSuccess")).shouldBe(visible);
  }

  private void clickAndInputValue(String id, String value) {
    $(By.id(id)).click();
    $(By.id(id)).sendKeys(value);
  }

  @TestTemplate
  void testCreatePaymentLink() {
    open(EngineUrl.createProcessUrl(PAYMENTLINK));
    $(By.id("form:price")).sendKeys("price_1QeSG6LaeAomYD3LfEHlcjEr");
    $(By.id("form:quantity_input")).sendKeys("2");
    $(By.id("form:createPaymentLink")).click();
    Selenide.sleep(2000);
    assertNotNull($(By.id("form:result")).val());
  }

}
