package com.api.stepDefns;

import com.api.service.request.BaseRequest;
import com.api.service.request.RequestFactory;
import com.api.utils.RequestResponse;
import com.api.utils.*;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.fail;

/**
 * basic request/response step functionality
 * all calls are done with no headers, if required then these should be
 * done in the relevant Step class
 */
public class BasicRequestResponseStepDefn {

    private static final Logger LOG = LoggerFactory.getLogger(BasicRequestResponseStepDefn.class);
    private final RequestResponse requestResponse;
    private final BaseRequest request;
    private final RequestSpecification requestSpec; //shortcut to request
    private final Config config;
    private String scenarioName;

    public BasicRequestResponseStepDefn(final RequestResponse requestResponse, Config config) {
        this.requestResponse = requestResponse;
        this.config = config;
        this.request= RequestFactory.makeNoHeaderRequest(config);
        this.requestSpec=this.request.getRequest();
    }

    @Before
    public void before(Scenario scenario) {
        this.scenarioName = scenario.getName();
    }

    //REUSABLE basic curl test to the chosen component
    @Given("I send basic GET request to component {string} with {int} second timeout")
    public void iSendBasicGetRequestToComponentWithTimeout(String component, int timeout) {

        String urlToTest = "";
                //config.getUIUrl(component);
        LOG.info(scenarioName + ": " + "Testing UI response for URL " + urlToTest);
        if (urlToTest == null) {
            fail(String.format("No UI URL could be found for component: %s", component));
        }
        try {
            // Send basic get request
            LOG.info(scenarioName + ": " + "Sending GET request to: " + urlToTest);
            requestResponse.response = requestSpec.get(urlToTest);
            LOG.info(scenarioName + ": " + "BASIC GET (" + component + ") Response status code is: " + requestResponse.response.statusCode());
            LOG.info(scenarioName + ": " + "BASIC GET (" + component + ") Response time in milliSec : " + requestResponse.response.getTime());
            LOG.info(scenarioName + ": " + "Get request Executing Time of (" + component + ")  : " + Config.dateTimeFormatter.format(LocalDateTime.now()));
        } catch (Exception e) {
            LOG.error("{}: {}", scenarioName, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // REUSABLE checks status code equal to a string e.g. "200"
    @Then("validate the status code be {string}")
    public void validateTheStatusCodeBe(String stscode) {
        LOG.info(scenarioName + ": " + "Asserting status code should be: " + stscode);
        String faultString=String.format("Expected %s. Response details: %s%s",stscode,System.lineSeparator(),requestResponse.toString());
        Assert.assertEquals(faultString, Integer.parseInt(stscode), requestResponse.response.statusCode());
    }

    @Then("validate the result contains {string}")
    public void validateTheResultContains(String msg) {
        LOG.debug(scenarioName + ": " + "Asserting result should contain: " + msg);
        Assert.assertTrue(requestResponse.response.asString().contains(msg));
    }

    @Then("I validate Content type and Header count of {string} for component {string}")
    public void iValidateContentTypeAndHeaderCount(String headerCount, String component) throws IOException {
        LOG.info(scenarioName + ": " + "Content type of (" + component + ") is: " + requestResponse.response.contentType());
        LOG.info(scenarioName + ": " + "Header count of (" + component + ") is: " + requestResponse.response.headers().size());
        Assert.assertEquals(Integer.parseInt(headerCount), requestResponse.response.headers().size());
    }
}
