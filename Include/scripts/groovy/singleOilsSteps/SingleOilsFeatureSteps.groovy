package singleOilsSteps
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import cucumber.api.java.After

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

import utils.WebUtilities
import singleOils.AddSingleOilsCart



class SingleOilsFeatureSteps {
	@Given("I open a browser to DoTERRA")
	public void i_open_a_browser_to_DoTERRA() {
		WebUtilities WU = new WebUtilities()
		WU.openBrowserToDoterra()
	}

	@Given("I navigate to the single oils page")
	public void i_navigate_to_the_single_oils_page() {
		AddSingleOilsCart SO = new AddSingleOilsCart()
		SO.navigateToSingleOilsPageFromMainMenu()
	}

	@When("I add (.*) of (.*) to the cart")
	public void i_add_single_oil_to_the_cart(String quantity, String name) {
		AddSingleOilsCart SO = new AddSingleOilsCart()
		SO.addSingleOil(name, quantity)
	}

	@Then("I verify that the (.*) single oil with a quantity of (.*) is in the cart")
	public void i_verify_the_correct_is_in_the_cart(String oilName, String quantity) {
		AddSingleOilsCart SO = new AddSingleOilsCart()
		SO.verifyQuantityInCart(oilName, quantity)
	}
	
	@After
	public void close_browser_and_quit_driver() {
		def WebDriver webDriver = DriverFactory.getWebDriver()
		webDriver.close()
	}
}