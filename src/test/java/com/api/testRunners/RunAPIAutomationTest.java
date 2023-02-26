package com.api.testRunners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Listeners;

@CucumberOptions(
        plugin = {
                "html:target/reports/cucumber-html-report.html",
                "json:target/reports/cucumber.json",
                "junit:target/reports/cucumber-junit-report.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "pretty"
        },
        features = {"src/test/resources"},
        glue = {"com/api/stepDefns"},
        tags = ("@Post_PetOrderPlaced"),
        monochrome = true, //displays console out put in proper format
       dryRun = false //check the mapping is proper between feature and step def file
)

@Listeners(com.api.utils.TestListener.class)
public class RunAPIAutomationTest extends AbstractTestNGCucumberTests {
}
