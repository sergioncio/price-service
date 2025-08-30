Feature: Price query
  As a client
  I want to query the applicable price
  So that I can see the correct price for a product and brand at a date

  Scenario Outline: Example tests from the statement
    Given an application date "<date>"
    And a productId 35455
    And a brandId 1
    When I GET /prices
    Then the response has status 200
    And the priceList is <priceList>
    And the price is "<price>"

    Examples:
      | date                | priceList | price |
      | 2020-06-14T10:00:00 | 1         | 35.50 |
      | 2020-06-14T16:00:00 | 2         | 25.45 |
      | 2020-06-14T21:00:00 | 1         | 35.50 |
      | 2020-06-15T10:00:00 | 3         | 30.50 |
      | 2020-06-16T21:00:00 | 4         | 38.95 |
