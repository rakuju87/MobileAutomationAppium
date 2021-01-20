@progression
Feature: Create a recurring meeting using native calendar app

  Scenario Outline: Create a new recurring(3 days a week) meeting and make sure it doesn't repeat
    Given I have launched the Calendar App
    When I create a event with <eventName> name
    Then I book meeting from <startTime> to <endTime>
    And I add a recurring 3 day a week meeting and non repeated
    Then I add <attendees> to the meeting
    And I save the meeting
    Then I Check if the meeting is created as expected

  Examples:
    |eventName              |startTime|endTime|attendees                              |
    |Recurring-Team Catch Up|5:30 am  |8:30 am|testuser1@gmail.com,testuser2@gmail.com|