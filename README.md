### Mobile Automation using Appium

**Feature:** **Create a recurring meeting using Native Android Samsung Calendar App.**

**Scenario Outline:** Create a new recurring(3 days a week) meeting, and make
sure it doesn't repeat on successive days

**prerequisite**

1. Install Android SDK, appium
2. Samsung S8 mobile with calendar app.
3. Launch appium server with capabilities mentioned in androidDevice.json

**Steps**
1. Use "mvn clean test" to execute tests.
2. Results - target/cucumber-reports