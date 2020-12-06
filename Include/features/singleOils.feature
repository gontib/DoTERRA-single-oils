Feature: Single Oils
	As a user I should be able to add a single oil to the cart

Scenario Outline: Add single oil with specified quantity to cart
	Given I open a browser to DoTERRA
	And I navigate to the single oils page
	When I add <quantity> of <singleOil> to the cart
	Then I verify that the <singleOil> single oil with a quantity of <quantity> is in the cart
	
	Examples:
		| singleOil | quantity |
		| Peppermint | 20			 |