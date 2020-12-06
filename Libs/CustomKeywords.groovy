
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import com.kms.katalon.core.testobject.TestObject

import com.applitools.eyes.RectangleSize

import com.applitools.eyes.selenium.Eyes



def static "com.kms.katalon.keyword.applitools.BasicKeywords.checkWindow"(
    	String testName	) {
    (new com.kms.katalon.keyword.applitools.BasicKeywords()).checkWindow(
        	testName)
}


def static "com.kms.katalon.keyword.applitools.BasicKeywords.checkTestObject"(
    	TestObject testObject	
     , 	String testName	) {
    (new com.kms.katalon.keyword.applitools.BasicKeywords()).checkTestObject(
        	testObject
         , 	testName)
}

/**
	 * Navigate to the Single Oils page from the main menu
	 */
def static "singleOils.AddSingleOilsCart.navigateToSingleOilsPageFromMainMenu"() {
    (new singleOils.AddSingleOilsCart()).navigateToSingleOilsPageFromMainMenu()
}

/**
	 * Add Single Oil to Cart
	 * @param oilName Name of the single oil to add to the cart
	 * @param quantity The quantity of the single oil to add to the cart
	 */
def static "singleOils.AddSingleOilsCart.addSingleOil"(
    	String oilName	
     , 	String quantity	) {
    (new singleOils.AddSingleOilsCart()).addSingleOil(
        	oilName
         , 	quantity)
}

/**
	 * Verify the quantity of oils in the cart matches the quantity that was added
	 * @param quantity The quantity of the single oil added to the cart
	 */
def static "singleOils.AddSingleOilsCart.verifyQuantityInCart"(
    	String oilName	
     , 	String quantity	) {
    (new singleOils.AddSingleOilsCart()).verifyQuantityInCart(
        	oilName
         , 	quantity)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesOpen"(
    	String testName	
     , 	RectangleSize viewportSize	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesOpen(
        	testName
         , 	viewportSize)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesInit"() {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesInit()
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesOpenWithBaseline"(
    	String baselineName	
     , 	String testName	
     , 	RectangleSize viewportSize	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesOpenWithBaseline(
        	baselineName
         , 	testName
         , 	viewportSize)
}


def static "com.kms.katalon.keyword.applitools.EyesKeywords.eyesClose"(
    	Eyes eyes	) {
    (new com.kms.katalon.keyword.applitools.EyesKeywords()).eyesClose(
        	eyes)
}
