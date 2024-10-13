Feature: Validate user completed task percentage

  Scenario: All users of the city FanCode should have more than half of their todos completed
    Given User has the todo tasks
    And User belongs to the city FanCode
    Then User Completed task percentage should be greater than 50%
