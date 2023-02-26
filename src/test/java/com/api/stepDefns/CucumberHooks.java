package com.api.stepDefns;


import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.testng.Assert;
import org.testng.SkipException;

import com.api.utils.Log;

import io.cucumber.core.gherkin.Step;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
//import gherkin.formatter.model.Step;

public class CucumberHooks {

    public CucumberHooks() {

    }

    private Scenario scenario;
    private String scenarioName;

    @Before
    public void start(Scenario scenario) {
        this.scenario = scenario;
        this.scenarioName = scenario.getName();
        String msg = "START SCENARIO: " + scenario.getName();
        Log.info(msg);
    }

    @Before("@skip_scenario")
    public void skipscenario(Scenario scenario)  {
        this.scenario = scenario;
        this.scenarioName = scenario.getName();
        Log.info("SKIP SCENARIO: " + scenario.getName());




        String a ="Skip / Ignore Test";
        if(a.equals("Skip / Ignore Test")){
            throw new SkipException("Skipping / Ignoring - Script not Ready for Execution ");
        }

        try
        {

            Assume.assumeTrue(false);
            // throw new SkipException("Skipping this test");
        }
        catch (AssumptionViolatedException e)
        {

            // clearing stack trace, so it will keep logs clear, just print the name of exception
            e.setStackTrace(new StackTraceElement[] {});
            throw e;
        }

    }

    @After
    public void after(Scenario scenario) {
        String msg = "END SCENARIO: " + scenario.getName();
        Log.info(msg);
    }


//	  @BeforeStep public void beforeStep(Step step) {
//		  if (scenario == null || scenario.isFailed()) {
//			  Log.info("STEP SKIPPED: " + step.getLocation());
//	  return;
//	  }
//		  }


//	@AfterStep
//	public void afterStep(Step step) {
//		if (scenario == null || scenario.isFailed()) {
//
//			String msg = "END STEP: " + step.getName();
//
//			Log.info(msg);
//			return;
//
//		}
//	}

}

