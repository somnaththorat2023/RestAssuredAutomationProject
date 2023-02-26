package com.api.stepDefns.books;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.service.request.BaseRequest;
import com.api.service.request.RequestFactory;
import com.api.stepDefns.databaseStepDefn;
import com.api.utils.Config;
import com.api.utils.JsonDataReader;
import com.api.utils.Log;
import com.api.utils.RequestResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class pestStoreStepDefn {
	 private static final Logger LOG = LoggerFactory.getLogger(pestStoreStepDefn.class);
	    private String scenarioName;
	    private final RequestResponse requestResponse;
	    private final BaseRequest request;
	    private final RequestSpecification requestSpec;
	    private final Config config;
	    private String reqbody;
	    private static String jsonString;
	    private static String token;
	    private static String bookId;
	    private static final String USER_ID = "138532ce-baac-4606-9f05-d66e027877e0";
	    public static HashMap<String, Object> bookdb = databaseStepDefn.databaseResult;
	    //Scenario scenario;


	    public pestStoreStepDefn(RequestResponse requestResponse, Config config) {
	        this.requestResponse = requestResponse;
	        request = RequestFactory.makePetStore(config);
	        requestSpec = request.getRequest();
	        this.config = config;
	    }

	    @Before
	    public void before(Scenario scenario) {
	        this.scenarioName = scenario.getName();
	    }



	    @Given("Request to retrieve order placed for purchasing the pet with {string}")
	    public void petStoreOrderPlace(String fileName) {
	        //Construct the URL from base url
	        Log.startTestCase("petStoreOrderPlace");
	        try {
	        	this.reqbody = JsonDataReader.readFromFile(fileName);
	        	requestSpec.body(reqbody);
	        	Log.info(reqbody);
	            final String book_list = config.petStore_endpoint + "/v2/store/order";
	            //Log.info(scenarioName + ": " + "Sending POST request to");
	            requestResponse.response = requestSpec.post(book_list).then().extract().response();
	            Log.info(scenarioName + ": " + "POST  Response returned: " + requestResponse.response.getBody().asString());
	            Log.info(scenarioName + ": " + "POST  Response status code is: " + requestResponse.response.statusCode());
	            //jsonString = requestResponse.response.asString();
	            Log.endTestCase("petStoreOrderPlace");
	        } catch (Exception e) {
	            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
	            fail("Unexpected error: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }


	    @Then("validate the status code")
	    public void ValidateStatusCode() {
	    	Assert.assertEquals(200, requestResponse.response.getStatusCode());
	    }

	    @Given("Fetch the details of returns a map of status codes to quantities")
	    public void Gettingreturnsmapstatus() {
	        //Construct the URL from base url
	        Log.startTestCase("Gettingreturnsmapstatus");
	        try {
	            final String book_list = config.petStore_endpoint + "/v2/store/inventory";
	            //Log.info(scenarioName + ": " + "Sending POST request to");
	            requestResponse.response = requestSpec.get(book_list).then().extract().response();
	            Log.info(scenarioName + ": " + "GET  Response returned: " + requestResponse.response.getBody().asString());
	            Log.info(scenarioName + ": " + "GET  Response status code is: " + requestResponse.response.statusCode());
	            //jsonString = requestResponse.response.asString();
	            Log.endTestCase("Gettingreturnsmapstatus");
	        } catch (Exception e) {
	            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
	            fail("Unexpected error: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }


	    @Then("validate the status code for map status")
	    public void ValidateStatusCodemapstatus() {
	    	Assert.assertEquals(200, requestResponse.response.getStatusCode());
	    }
	    
	    @Given("User fetached the ID needs to be pet")
	    public void GettingPurchaseOrderId() {
	        //Construct the URL from base url
	        Log.startTestCase("GettingPurchaseOrderId");
	        try {
	        	requestSpec.pathParam("orderid", config.petsStore_orderId);
	            final String book_list = config.petStore_endpoint + "/v2/store/order/";
	            Log.info(scenarioName + ": " + "Sending POST request to : "+book_list);
	            requestResponse.response = requestSpec.get(book_list).then().extract().response();
	            Log.info(scenarioName + ": " + "GET  Response returned: " + requestResponse.response.getBody().asString());
	            Log.info(scenarioName + ": " + "GET  Response status code is: " + requestResponse.response.statusCode());
	            //jsonString = requestResponse.response.asString();
	            Log.endTestCase("GettingPurchaseOrderId");
	        } catch (Exception e) {
	            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
	            fail("Unexpected error: " + e.getMessage());
	            throw new RuntimeException(e);
	        }
	    }


	    @Then("validate the status code of purchase order id")
	    public void ValidatePurchaseOrderId() {
	    	Assert.assertEquals(200, requestResponse.response.getStatusCode());
	    }
}
