package utils
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.exception.StepFailedException


class WebUtilities {
	/**
	 * Open browser to https://www.doterra.com/US/en/
	 */
	@Keyword
	def openBrowserToDoterra() {
		WebUI.openBrowser("https://www.doterra.com/US/en/")
	}

	/**
	 * Wait for the page to finish loading before proceeding
	 * @param timeout The length of time to wait
	 */
	@Keyword
	def waitForPage(Integer timeout) {
		WebUI.waitForPageLoad(timeout)
		WebUI.waitForJQueryLoad(timeout)
	}

	/**
	 * 
	 * @param locatorDescription Description of the element trying to be found
	 * @param locatorValue How to find the element
	 * @param findMultipleElements Whether to use findElements() instead of findElement()
	 * @param isVisible Whether to check if the element is visible
	 * @param timeout The length of time to wait
	 * @return WebElement
	 */
	@Keyword
	def findByWebDriver(String locatorDescription, By locatorValue, Boolean findMultipleElements = false, Boolean isVisible = true, Integer timeout = 5) {
		waitForPage(timeout)
		def webDriver = DriverFactory.getWebDriver()
		if (findMultipleElements) {
			return webDriver.findElements(locatorValue)
		}
		//WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(timeout)).until(ExpectedConditions.presenceOfElementLocated(locatorValue))
		def WebElement element = webDriver.findElement(locatorValue)
		if (isVisible) {
			// Scroll to element
			Actions actions = new Actions(webDriver)
			actions.moveToElement(element)
			actions.perform()

			// Check if visible
			//element = new WebDriverWait(webDriver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(element))
			if (!element.isDisplayed() {
				throw new StepFailedException("The '${locatorDescription}' element is not visible.")
			}
		}
		return element
	}

	/**
	 * Find a child element from its parent
	 * @param locatorDescription Description of the element trying to be found
	 * @param locatorValue How to find the element
	 * @param parentElement Element used to find a child element
	 * @param findMultipleElements Whether to use findElements() instead of findElement()
	 * @param isVisible Whether to check if the element is visible
	 * @param timeout The length of time to wait
	 * @return WebElement(s)
	 */
	@Keyword
	def findChildElement(String locatorDescription, By locatorValue, WebElement parentElement, Boolean findMultipleElements = false, Boolean isVisible = true, Integer timeout = 5) {
		waitForPage(timeout)
		def WebDriver webDriver = DriverFactory.getWebDriver()
		if (findMultipleElements) {
			return parentElement.findElements(locatorValue)
		}
		def childElement = null
		try {
			return parentElement.findElement(locatorValue)
		}
		catch (Exception e) {
			println "Failed to find the '#{locatorDescription}' element."
		}
	}

	/**
	 * Find the element using a TestObject
	 * @param locatorID The locatorID of the element to find
	 * @param isVisible Whether to check if the element is visible
	 * @param timeout The length of time to wait
	 */
	@Keyword
	def findByTestObject(String locatorID, Boolean isVisible = true, Integer timeout = 5) {
		waitForPage(timeout)
		def locator = findTestObject(locatorID)
		if (!WebUI.waitForElementPresent(locator, timeout)) {
			throw new WebElementNotFoundException("The '${locator.objectId}' element wasn't found.")
		}
		if (isVisible) {
			waitForTestObject(locator, timeout)
		}
		return locator
	}

	/**
	 * Wait for element before interacting with it
	 * @param element The TestObject needing to be interacted with
	 * @param timeout The length of time to wait
	 */
	@Keyword
	def waitForTestObject(TestObject element, Integer timeout = 5) {
		WebUI.scrollToElement(element, timeout)
		if (!WebUI.waitForElementVisible(element, timeout)) {
			throw new StepFailedException("The '${element.objectId}' element is not visible.")
		}
		WebUI.waitForElementClickable(element, timeout)
	}
}


