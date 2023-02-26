package com.api.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
       // Log.info(result.getClass().getName());
        Log.info(result.getMethod().getMethodName() + " - Started");
        Log.info(result.getMethod().getDescription());
       /* for (Feature.Features feature : Feature.Features.values()) {
            if (result.getMethod().getGroups(
            ).contains(feature.name()) && !Feature.isFeatureEnabled(
                    feature)) {
                Log.warn("Skipping " + result.getInstanceName() + ", as feature " + feature + " is disabled");
                result.setStatus(ITestResult.SKIP);
                throw new SkipException("Skipping as feature " + feature + " is disabled.");
            }
        }*/
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.info("Skipped beacuse of -"+result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //TBD
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.info(result.getMethod().getMethodName() + " - Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.info("Failed because of -"+result.getThrowable());
    }
}
