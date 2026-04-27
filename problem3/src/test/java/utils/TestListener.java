package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.WebDriver;


import base.BaseTest;

public class TestListener implements ITestListener {

    ExtentReports extent = ExtentReportManager.getReportInstance();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());

        // ✅ Get driver from test class
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();

        // ✅ Take screenshot
        String path = ScreenshotUtil.captureScreenshot(driver, result.getName());

        test.addScreenCaptureFromPath(path);
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}