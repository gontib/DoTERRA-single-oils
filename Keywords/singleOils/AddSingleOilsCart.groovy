package singleOils
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.exception.StepFailedException
import utils.WebUtilities


class AddSingleOilsCart {
	/**
	 * Navigate to the Single Oils page from the main menu
	 */
	@Keyword
	def navigateToSingleOilsPageFromMainMenu() {
		def WebUtilities WU = new WebUtilities()
		WebUI.mouseOver(WU.findByTestObject("menuSelectors/menuShop"))
		WebUI.mouseOver(WU.findByTestObject("menuSelectors/subMenuEssentialOils"))
		WebUI.click(WU.findByTestObject("menuSelectors/buttonSingleOils"))
	}

	/**
	 * Add Single Oil to Cart
	 * @param oilName Name of the single oil to add to the cart
	 * @param quantity The quantity of the single oil to add to the cart
	 */
	@Keyword
	def addSingleOil(String oilName, String quantity) {
		def WebDriver webDriver = DriverFactory.getWebDriver()
		def WebUtilities WU = new WebUtilities()
		def Integer count = 0
		def WebElement oilsTable = null
		def WebElement oilNameElement = null

		while (true) {
			oilsTable = WU.findByWebDriver("Table of Single Oils", By.xpath("//div[@id='content_body']//div[@class='col-xs-12'][2]"), false, false, 5)
			def oilParentElements = WU.findChildElement("Parent Element of Each Single Oil", By.xpath("//div[@class='grid-item grid-product']"), oilsTable, true, false, 5)
			for (oil in oilParentElements) {
				// Check the name of the oil
				oilNameElement = WU.findChildElement("Oil Name Text", By.className("title"), oil, false, false, 5)
				if (oilNameElement.text == oilName) {
					// Determine if the oil is unavailable
					if (WU.findChildElement("Temporarily Unavailable Banner", By.className("prod-image"), oil, false, false, 5).getAttribute("style").indexOf("temp-unavailable") >= 0) {
						throw new StepFailedException("The ${oilName} single oil is currently unavailable.")
					}
					// Set the desired quantity then add to cart
					def WebElement quantityButton = WU.findChildElement("Oil Quantity Button", By.className("current-qty"), oil)
					if (quantityButton == null) {
						// The quantity button is missing so the oil is currently unavailable
						throw new StepFailedException("The ${oilName} single oil is currently unavailable.")
					}
					if (quantityButton.text != quantity) {
						quantityButton.click()
						def quantityNumberElements = WU.findChildElement("Oil Quantity List Number Button", By.className("qty-option"), oil, true, true, 5)
						def quantityFound = false
						for (quantityNumber in quantityNumberElements) {
							if (quantityNumber.text == quantity) {
								quantityFound = true
								quantityNumber.click()
								break
							}
						}
						if (!quantityFound) {
							throw new StepFailedException("There aren't ${quantity} items of ${oilName} single oil currently unavailable for purchase.")
						}
					}
					def WebElement addButton = WU.findChildElement("Add Oil Button", By.id("buyButton"), oil)
					addButton.click()
					return
				}
			}
			// Didn't find the oil on current page, moving to next page and trying again
			try {
				WebUI.verifyElementPresent(findTestObject("navigationSelectors/nextPage"), 5)
				WebUI.click(WU.findByTestObject("navigationSelectors/nextPage"))
			} catch (StepFailedException e) {
				println e
				break
			}
			catch (WebElementNotFoundException e) {
				println e
				break
			}
		}

		// Failed to find the specified single oil. Cleaning up.
		webDriver.quit()
		throw new StepFailedException("The ${oilName} single oil wasn't found.")
	}

	/**
	 * Verify the quantity of oils in the cart matches the quantity that was added
	 * @param quantity The quantity of the single oil added to the cart
	 */
	@Keyword
	def verifyQuantityInCart(String oilName, String quantity) {
		def WebUtilities WU = new WebUtilities()
		def WebElement addedOilName = null
		// Get the number of items in the cart and verify it is equal to the desired quantity
		WebUI.mouseOver(WU.findByTestObject("menuSelectors/miniCartTotal"))
		WU.findByTestObject("menuSelectors/miniCartWindow")
		def cartItems = WU.findByWebDriver("Items in the Cart Parent Element", By.xpath("//li[@id='nav-bag']//a[@class='product product__cart']"), true, true, 5)
		for (cartItem in cartItems) {
			addedOilName = WU.findChildElement("Name of the Oil Added to Cart", By.className("product__title"), cartItem)
			if (addedOilName.text == oilName) {
				def addedOilQuantity = WU.findChildElement("Quantity of the Oil Added to Cart", By.className("product__qty"), cartItem)
				assert addedOilQuantity.text == "Qty: "+ quantity : "The added single oil quantity was'${addedOilQuantity.text}' instead of '${quantity}'"
				return
			}
		}
		throw new StepFailedException("The ${oilName} single oil failed to be added to the cart.")
	}
}