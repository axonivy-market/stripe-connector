package com.axonivy.connector.stripe.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import com.axonivy.connector.stripe.test.context.MultiEnvironmentContextProvider;
import com.axonivy.connector.stripe.test.utils.StripeUtils;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;

@ExtendWith(MultiEnvironmentContextProvider.class)
@IvyProcessTest
public class PaymentServiceTest {

  @BeforeEach
  public static void setup(AppFixture appFixture) {
    StripeUtils.setUpConfigForApiTest(appFixture);
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless"); // Run in headless mode (no GUI)
    options.addArguments("--disable-web-security"); // Disable web security (for cross-origin tests)
    Configuration.browserCapabilities = options;
    Configuration.browser = "chrome";
  }

  @TestTemplate
  void testCreateCheckoutSession() {
    open(EngineUrl.createProcessUrl(
        "/stripe-connector-test/1946E968E7BAB355/logInUser.ivp?username=Developer&password=Developer"));
    open(EngineUrl.createProcessUrl("/stripe-connector-demo/1943FFA7A8AA30F4/start.ivp"));

    $(By.id("form:price")).sendKeys("price_1QeSG6LaeAomYD3LfEHlcjEr");
    $(By.id("form:quantity_input")).sendKeys("2");
    $(By.id("form:resquest-button")).click();
    Selenide.sleep(5000);
    Selenide.switchTo().frame($(By.tagName("iframe")));
    clickAndInputValue("email", "Octopus@gmail.com");
    clickAndInputValue("cardNumber", "4242424242424242");
    clickAndInputValue("cardExpiry", "04/31");
    clickAndInputValue("cardCvc", "115");
    clickAndInputValue("billingName", "test");
    $("#billingCountry").selectOption("Vietnam");
    $(By.className("SubmitButton")).click();
    Selenide.sleep(5000);
    Selenide.switchTo().alert().accept();
    $(By.className("PaymentSuccess")).shouldBe(visible);
  }

  private void clickAndInputValue(String id, String value) {
    $(By.id(id)).click();
    $(By.id(id)).sendKeys(value);
  }

  @TestTemplate
  void testCreatePaymentLink() {
    open(EngineUrl.createProcessUrl("/stripe-connector-demo/1943F079FC4B1728/start.ivp"));
    $(By.id("form:price")).sendKeys("price_1QeSG6LaeAomYD3LfEHlcjEr");
    $(By.id("form:quantity_input")).sendKeys("2");
    $(By.id("form:createPaymentLink")).click();
    Selenide.sleep(2000);
    assertNotNull($(By.id("form:result")).val());
  }

}
