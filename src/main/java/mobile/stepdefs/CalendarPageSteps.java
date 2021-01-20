package mobile.stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mobile.models.Calendar;
import mobile.pageobjects.CalendarPage;
import runner.TestRunner;

public class CalendarPageSteps extends TestRunner {
  CalendarPage calendarPage;
  Calendar calendarDetails = new Calendar();
  @Given("^I have launched the Calendar App$")
  public void iHaveLaunchedTheCalendarApp() throws Exception {
    calendarPage = new CalendarPage(driver);
    calendarPage.launchEvent();
  }
  @When("I create a event with (.*) name")
  public void createEvent (String eventName) {
    calendarDetails.setEventName(eventName);
    calendarPage.createEvent(calendarDetails);
  }

  @Then("I book meeting from (.*) to (.*)")
  public void bookMeeting (String startTime, String endTime) {
    calendarDetails.setStartTime(startTime);
    calendarDetails.setEndTime(endTime);
    calendarPage.bookMeeting(calendarDetails);
  }

  @And("I add a recurring 3 day a week meeting and non repeated")
  public void addRecurringMeeting () {
    calendarPage.addEvent();
  }

  @Then("I add (.*) to the meeting")
  public void addAttendees (String attendeesList) {
    calendarDetails.setAttendees(attendeesList);
    calendarPage.addAttendees(calendarDetails);
  }

  @And("I save the meeting")
  public void saveTheMeeting () {
   calendarPage.saveMeeting();
  }

  @Then("I Check if the meeting is created as expected")
  public void verifyTheMeeting () throws Exception {
    calendarPage.verifyMeeting(calendarDetails);
  }

}
