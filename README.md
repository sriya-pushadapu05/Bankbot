# 💳 BankBot – Selenium Java Automation Framework

A Selenium-based test automation framework built using Java and TestNG, designed with the Page Object Model (POM) approach for testing the Guru99 Banking Demo application.

---

## 📂 Project Layout
bankbot-framework/
├── src/
│ └── test/
│ ├── java/
│ │ ├── core/
│ │ │ ├── BasePage.java
│ │ │ └── BaseTest.java
│ │ ├── pages/
│ │ │ ├── LoginPage.java
│ │ │ ├── CustomerPage.java
│ │ │ ├── AccountPage.java
│ │ │ └── TransferPage.java
│ │ ├── tests/
│ │ │ ├── LoginTests.java
│ │ │ ├── CustomerTests.java
│ │ │ ├── AccountTests.java
│ │ │ └── TransferTests.java
│ │ └── helpers/
│ │ ├── ConfigUtil.java
│ │ ├── ReportManager.java
│ │ ├── ScreenshotHelper.java
│ │ └── TestWatcher.java
│ └── resources/
│ └── config.properties
├── output/
│ └── report.html
├── images/
├── testng.xml
└── pom.xml

---

## ⚙️ Requirements

| Software | Version |
|----------|--------|
| Java     | 11+    |
| Maven    | 3.6+   |
| Chrome   | Latest |
| Firefox  | Optional |

✔ WebDriverManager handles browser drivers automatically.

--
#
cd bankbot-framework
2. Configure the run
Edit src/test/resources/config.properties:

browser=chrome          # or firefox
baseUrl=https://demo.guru99.com/V4/index.php
timeout=10
3. Run the full test suite
mvn test
This runs all tests via testng.xml using the Maven Surefire Plugin.

4. View the report
Open reports/extent-report.html in a browser after the run.

🧪 Test Modules
Module	Test Class	Scenarios
Login	LoginTest	Valid login, invalid login (alert validation), screenshot demo
Customer	CustomerTest	Create new customer, verify Customer ID and success message
Account	AccountTest	Create two accounts linked to a customer, verify Account IDs
Fund Transfer	FundTransferTest	Transfer funds between accounts, verify success message
🏗️ Framework Design
Page Object Model
Every page has a dedicated Page class under pages/
Locators and action methods live only inside Page classes
Test classes call Page methods only — no findElement or click in test code
BaseTest
@BeforeMethod: Launches browser, navigates to base URL
@AfterMethod: Quits browser
Browser selected from config.properties — no hardcoded values
BasePage
waitForElement(By) – waits for visibility
waitForClickable(By) – waits for clickability
click(), type(), getText(), jsClick(), scrollIntoView() – shared utilities
Wait Strategy
All waits use WebDriverWait with ExpectedConditions
Thread.sleep() is not used in production test code
Screenshot on Failure
TestListener implements ITestListener
On onTestFailure, captures a screenshot via ScreenshotUtil
Filename format: <testName>_yyyyMMdd_HHmmss.png
Saved to screenshots/ folder
Reporting
ExtentReportManager manages a singleton ExtentReports instance
TestListener logs pass/fail status and embeds failure screenshots
Report saved to reports/extent-report.html
Config Management
config.properties holds all environment-level values
ConfigReader exposes typed getters: getBrowser(), getBaseUrl(), getTimeout()
📦 Maven Dependencies
selenium-java       4.21.0
testng              7.9.0
webdrivermanager    5.8.0
extentreports       5.1.1
⚠️ Known Gaps / Recommended Improvements
These items are flagged for improvement — see the Test Design Document for full details.

Thread.sleep(1500) in LoginTest.testScreenshotDemo() — violates the no-sleep rule; replace with WebDriverWait.
Hardcoded credentials in test classes (mngr658981 / avEgYzU) — move to config.properties.
@DataProvider missing — LoginTest should use a @DataProvider for valid/invalid credential sets.
FormValidationTest not implemented — Test Module 5 (empty fields, non-numeric input, future DOB) is absent.
implicitlyWait + WebDriverWait mixed — BaseTest sets an implicit wait alongside explicit waits, which can cause unpredictable timeout behaviour; remove the implicit wait.
Test ordering via System.setProperty — AccountTest and FundTransferTest depend on custId/accId set by prior tests; tests should be independent or use TestNG dependsOnMethods.
testng.xml lacks preserve-order="true" — test execution order is not guaranteed.
🤝 Team Contribution
Member	Contribution
—	BasePage, BaseTest
—	LoginPage, LoginTest
—	NewCustomerPage, CustomerTest
—	NewAccountPage, AccountTest
—	FundTransferPage, FundTransferTest, TestListener
📄 License
This project is developed for academic/hackathon purposes under the GUVI BankBot challenge.
