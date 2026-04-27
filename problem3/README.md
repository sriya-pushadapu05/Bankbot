# BankBot – Selenium-Java Test Automation Framework

A Page Object Model (POM) based Selenium-Java automation framework for the [Guru99 Demo Banking Portal](https://demo.guru99.com/V4/index.php), built with TestNG, WebDriverManager, and ExtentReports.

---

## 📁 Project Structure

```
bankbot-framework/
├── src/
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   ├── BasePage.java          # Shared WebDriver utilities (waitForElement, click, type…)
│       │   │   └── BaseTest.java          # Browser setup/teardown (@BeforeMethod / @AfterMethod)
│       │   ├── pages/
│       │   │   ├── LoginPage.java         # Manager login actions and alert handling
│       │   │   ├── NewCustomerPage.java   # Customer creation and verification
│       │   │   ├── NewAccountPage.java    # Account creation and verification
│       │   │   └── FundTransferPage.java  # Fund transfer actions
│       │   ├── tests/
│       │   │   ├── LoginTest.java         # Login test scenarios
│       │   │   ├── CustomerTest.java      # Customer management tests
│       │   │   ├── AccountTest.java       # Account management tests
│       │   │   └── FundTransferTest.java  # Fund transfer tests
│       │   └── utils/
│       │       ├── ConfigReader.java       # Reads config.properties
│       │       ├── ExtentReportManager.java # ExtentReports singleton
│       │       ├── ScreenshotUtil.java     # Screenshot capture utility
│       │       └── TestListener.java       # ITestListener – screenshot + report on failure
│       └── resources/
│           └── config.properties          # Browser, URL, timeout config
├── reports/
│   └── extent-report.html                 # Auto-generated HTML report
├── screenshots/                           # Failure screenshots (timestamped)
├── testng.xml                             # TestNG suite definition
└── pom.xml                                # Maven dependencies and build config
```

---

## ⚙️ Prerequisites

| Tool | Version |
|------|---------|
| Java | 11 or higher |
| Maven | 3.6+ |
| Chrome | Latest stable |
| Firefox | Latest stable (optional) |

> **WebDriverManager** handles browser driver binaries automatically — no manual driver setup needed.

---

## 🚀 Setup & Run

### 1. Clone the repository
```bash
git clone https://github.com/hemakakarlapudi12345/bankbot-framework.git
cd bankbot-framework
```

### 2. Configure the run
Edit `src/test/resources/config.properties`:
```properties
browser=chrome          # or firefox
baseUrl=https://demo.guru99.com/V4/index.php
timeout=10
```

### 3. Run the full test suite
```bash
mvn test
```

This runs all tests via `testng.xml` using the Maven Surefire Plugin.

### 4. View the report
Open `reports/extent-report.html` in a browser after the run.

---

## 🧪 Test Modules

| Module | Test Class | Scenarios |
|--------|-----------|-----------|
| Login | `LoginTest` | Valid login, invalid login (alert validation), screenshot demo |
| Customer | `CustomerTest` | Create new customer, verify Customer ID and success message |
| Account | `AccountTest` | Create two accounts linked to a customer, verify Account IDs |
| Fund Transfer | `FundTransferTest` | Transfer funds between accounts, verify success message |

---

## 🏗️ Framework Design

### Page Object Model
- Every page has a dedicated Page class under `pages/`
- Locators and action methods live only inside Page classes
- Test classes call Page methods only — no `findElement` or `click` in test code

### BaseTest
- `@BeforeMethod`: Launches browser, navigates to base URL
- `@AfterMethod`: Quits browser
- Browser selected from `config.properties` — no hardcoded values

### BasePage
- `waitForElement(By)` – waits for visibility
- `waitForClickable(By)` – waits for clickability
- `click()`, `type()`, `getText()`, `jsClick()`, `scrollIntoView()` – shared utilities

### Wait Strategy
- All waits use `WebDriverWait` with `ExpectedConditions`
- `Thread.sleep()` is **not used** in production test code

### Screenshot on Failure
- `TestListener` implements `ITestListener`
- On `onTestFailure`, captures a screenshot via `ScreenshotUtil`
- Filename format: `<testName>_yyyyMMdd_HHmmss.png`
- Saved to `screenshots/` folder

### Reporting
- `ExtentReportManager` manages a singleton `ExtentReports` instance
- `TestListener` logs pass/fail status and embeds failure screenshots
- Report saved to `reports/extent-report.html`

### Config Management
- `config.properties` holds all environment-level values
- `ConfigReader` exposes typed getters: `getBrowser()`, `getBaseUrl()`, `getTimeout()`

---

## 📦 Maven Dependencies

```xml
selenium-java       4.21.0
testng              7.9.0
webdrivermanager    5.8.0
extentreports       5.1.1
```

---

## ⚠️ Known Gaps / Recommended Improvements

> These items are flagged for improvement — see the Test Design Document for full details.

1. **`Thread.sleep(1500)` in `LoginTest.testScreenshotDemo()`** — violates the no-sleep rule; replace with `WebDriverWait`.
2. **Hardcoded credentials in test classes** (`mngr658981` / `avEgYzU`) — move to `config.properties`.
3. **`@DataProvider` missing** — `LoginTest` should use a `@DataProvider` for valid/invalid credential sets.
4. **`FormValidationTest` not implemented** — Test Module 5 (empty fields, non-numeric input, future DOB) is absent.
5. **`implicitlyWait` + `WebDriverWait` mixed** — `BaseTest` sets an implicit wait alongside explicit waits, which can cause unpredictable timeout behaviour; remove the implicit wait.
6. **Test ordering via `System.setProperty`** — `AccountTest` and `FundTransferTest` depend on `custId`/`accId` set by prior tests; tests should be independent or use TestNG `dependsOnMethods`.
7. **`testng.xml` lacks `preserve-order="true"`** — test execution order is not guaranteed.

---

## 🤝 Team Contribution

| Member | Contribution |
|--------|-------------|
| — | `BasePage`, `BaseTest` |
| — | `LoginPage`, `LoginTest` |
| — | `NewCustomerPage`, `CustomerTest` |
| — | `NewAccountPage`, `AccountTest` |
| — | `FundTransferPage`, `FundTransferTest`, `TestListener` |

---

## 📄 License

This project is developed for academic/hackathon purposes under the GUVI BankBot challenge.
