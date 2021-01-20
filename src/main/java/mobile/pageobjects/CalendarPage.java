package mobile.pageobjects;

import static logger.LoggingManager.logMessage;

import Actions.Page;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import java.util.List;
import mobile.models.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import utils.Utils;


public class CalendarPage extends Page {

   WebDriver driver;

   MobileElement mobileElement;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/floating_action_button")
  private WebElement addEvent;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/title")
  private WebElement tileText;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/start_date_time")
  private WebElement startDateButton;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/end_date_time")
  private WebElement endDateButton;

  @AndroidFindBy(xpath = "//android.widget.NumberPicker[1]/android.widget.EditText")
  private WebElement startHrs;

  String editText = "android.widget.EditText";

  String numberpicker_input = "com.samsung.android.calendar:id/numberpicker_input";

  @AndroidFindBy(xpath = "//*[@text = 'End']")
  private WebElement endDate;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/repeats_selected_item")
  private WebElement eventRepeatSelect;

  @AndroidFindBy(xpath = "//*[@text = 'Custom']")
  private WebElement eventRepeatCustom;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/repeat_custom_spinner")
  private WebElement repeatSpinner;

  @AndroidFindBy(xpath = "//*[@text = 'Weekly']")
  private WebElement repeatWeekly;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekSun")
  private WebElement repeatDaySun;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekMon")
  private WebElement repeatDayMon;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekTue")
  private WebElement repeatDayTue;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekWed")
  private WebElement repeatDayWed;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekThr")
  private WebElement repeatDayThr;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekFri")
  private WebElement repeatDayFri;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/weekSat")
  private WebElement repeatDaySat;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/button1")
  private WebElement doneButton;

  @AndroidFindBy(className = "android.widget.ImageButton")
  private WebElement repeatBackButton;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/attendees")
  private WebElement attendees;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/add_button")
  private WebElement addButton;

  @AndroidFindBy(id = "com.samsung.android.calendar:id/smallLabel")
  private WebElement saveButton;

  @AndroidFindBy(xpath = "//*[@text = 'Recurring-Team Catch Up']")
  private WebElement savedEvent;

  String inviteeName = "com.samsung.android.calendar:id/name";

  public CalendarPage(WebDriver driver) throws InterruptedException {
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }

  public void launchEvent() throws Exception {
    clickElement(addEvent);
    logMessage("Clicked on Add Event button");
  }

  public void createEvent (Calendar calendarDetails){
    sendKeys(tileText,calendarDetails.getEventName());
    logMessage("Enter event title name as " + calendarDetails.getEventName());
  }

  public void bookMeeting(Calendar calendarDetails){
    clickElement(startDateButton);
    clickElement(startHrs);
    List<WebElement> startTimeHrsMins = driver.findElements(By.className(editText));
    sendKeys(startTimeHrsMins.get(0),calendarDetails.getStartTime().split(":")[0]);
    sendKeys(startTimeHrsMins.get(1),calendarDetails.getStartTime().split(":")[1].split(" ")[0]);
    List<WebElement> amPm = driver.findElements(By.id(numberpicker_input));
    if(!amPm.get(2).getText().contains(calendarDetails.getStartTime().split(":")[1].split(" ")[1])){
      driver.findElement(By.className("android.widget.Button")).click();
    }
    clickElement(endDate);
    clickElement(startHrs);
    startTimeHrsMins = driver.findElements(By.className(editText));
    sendKeys(startTimeHrsMins.get(0),calendarDetails.getEndTime().split(":")[0]);
    sendKeys(startTimeHrsMins.get(1),calendarDetails.getEndTime().split(":")[1].split(" ")[0]);
    amPm = driver.findElements(By.id(numberpicker_input));
    if(!amPm.get(2).getText().contains(calendarDetails.getEndTime().split(":")[1].split(" ")[1])){
      driver.findElement(By.className("android.widget.Button")).click();
    }
    clickElement(doneButton);
    logMessage("Booking Event completed");
  }

  public void addEvent(){
    clickElement(eventRepeatSelect);
    clickElement(eventRepeatCustom);
    clickElement(repeatSpinner);
    clickElement(repeatWeekly);
    selectMonWedFri();
    clickElement(doneButton);
    clickElement(repeatBackButton);
    logMessage("Add custom event repeat completed");
  }

  public void addAttendees(Calendar calendarDetails){
    clickElement(attendees);
    for (String attendee : calendarDetails.getAttendees().split(",")) {
      sendKeys(attendees, attendee);
      clickElement(addButton);
    }
    logMessage("Add attendees to the event completed");
  }

  public void saveMeeting(){
    clickElement(saveButton);
    logMessage("Event saving completed");
  }

  public void verifyMeeting(Calendar calendarDetails){
    clickElement(driver.findElement(By.xpath("//*[@text = '" + Utils.removeExtraZeroDate(Utils.getNextMondayDate("yyyy-MM-dd"),"-") + "']")));
    clickElement(driver.findElement(By.xpath("//*[@text = '"+ calendarDetails.getEventName() +"']")));
    SoftAssert assertt = new SoftAssert();
    assertt.assertEquals(calendarDetails.getEventName(),tileText.getText(),"Event title entered doesn't match ");
    assertt.assertTrue(startDateButton.getText().contains(calendarDetails.getStartTime()),"Start time doesn't match");
    assertt.assertTrue(endDateButton.getText().contains(calendarDetails.getEndTime()),"End time doesn't match");
    assertt.assertEquals(eventRepeatSelect.getText(),"Weekly (every Monday, Wednesday, Friday)", "Event repeat doesn't match");
    List<WebElement> inviteeNames = driver.findElements(By.id(inviteeName));
    for(WebElement inviteeName : inviteeNames){
      String inviteName = inviteeName.getText();
      assertt.assertTrue(calendarDetails.getAttendees().contains(inviteName),inviteName + " doesn't exist");
    }
    assertt.assertAll();
    logMessage("Event verification completed");
  }

  private void selectMonWedFri(){
    if(repeatDaySun.getAttribute("content-desc").contains("Selected")){
      clickElement(repeatDaySun);
    }
    if(repeatDayMon.getAttribute("content-desc").contains("Not selected")){
      clickElement(repeatDayMon);
    }
    if(repeatDayTue.getAttribute("content-desc").contains("Selected")){
      clickElement(repeatDayTue);
    }
    if(repeatDayWed.getAttribute("content-desc").contains("Not selected")){
      clickElement(repeatDayWed);
    }
    if(repeatDayThr.getAttribute("content-desc").contains("Selected")){
      clickElement(repeatDayThr);
    }
    if(repeatDayFri.getAttribute("content-desc").contains("Not selected")){
      clickElement(repeatDayFri);
    }
    if(repeatDaySat.getAttribute("content-desc").contains("Selected")){
      clickElement(repeatDaySat);
    }
  }
}
