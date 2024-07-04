Feature: Bus Booking Location Search
  @sanity
  Scenario Outline: Entering Locations and date for bus booking
    Given the user is on the MakeMyTrip Buses booking page
    When user enters a valid city name <fromLocation> and select from the dropdown suggestions
    Then user able to see the from field populated with input <fromLocation>
    When user enters a valid destination city name <toLocation> and select from the dropdown suggestions
    Then user able to see the 'To' field populated with input <toLocation>
    When the user selects a valid travel date
    Then user able to see the 'date' field filled by entered date
    When user clicks the search button
    Then the user should be navigated to the search results page
  Examples:
    |fromLocation|toLocation|
    |Coimbatore  |Chennai   |
    |Chennai    |Coimbatore|


  @sanity
  Scenario: Apply filters to the search results
    Given the user has searched for buses from "Coimbatore" to "Chennai"
    When the user applies a filter for  buses
    And user sorts the search based on price
    And user selects the bus and select the seats
    And clicks the continue button
    Then user navigated to complete your Booking page

