
Feature: Flight Search

  Background:
    Given I am on the booking website

  Scenario Outline: Search for flights and find the one with the longest duration and cheapest price
    When I search for flights from "<fromLocation>" to "<toLocation>"
    Then I should see the flight with the longest duration
    And I should see the flight with the cheapest price

    Examples:
      | fromLocation | toLocation |
      | DFW          | PHL        |
      | NYC          | LAX        |
      | JFK          | LAX        |
      | ORD          | SFO        |
