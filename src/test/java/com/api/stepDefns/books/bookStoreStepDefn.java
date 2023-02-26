package com.api.stepDefns.books;

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
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

public class bookStoreStepDefn {

    private static final Logger LOG = LoggerFactory.getLogger(bookStoreStepDefn.class);
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


    public bookStoreStepDefn(RequestResponse requestResponse, Config config) {
        this.requestResponse = requestResponse;
        request = RequestFactory.makeBookRequest(config);
        requestSpec = request.getRequest();
        this.config = config;
    }

    @Before
    public void before(Scenario scenario) {
        this.scenarioName = scenario.getName();
    }

    @Test
    @Given("I am an authorized valid user")
    public void iAmAnAuthorizedvalidUser() {
        //Construct the URL from base url
        Log.startTestCase("iAmAnAuthorizedvalidUser");
        try {
           // reqbody = "{ \"userName\":\"" + config.book_username + "\", \"password\":\"" + config.book_password + "\"}";
            reqbody = "{ \"userName\":\"" + bookdb.get("USERNAME") + "\", \"password\":\"" + bookdb.get("PASSWORD") + "\"}";
            requestSpec.body(reqbody);
            final String get_token = config.book_endpoint + "/Account/v1/GenerateToken";
            //Log.info(scenarioName + ": " + "Sending POST request to");
            requestResponse.response = requestSpec.post(get_token).then().extract().response();
            Log.info(scenarioName + ": " + "POST  Response returned: " + requestResponse.response.getBody().asString());
            //Log.info(scenarioName + ": " + "POST  Response status code is: " + requestResponse.response.statusCode());
            final JsonPath tokenvalue = requestResponse.response.jsonPath();
            Log.info(tokenvalue.get("token"));
            token = tokenvalue.get("token");
            Log.endTestCase("iAmAnAuthorizedvalidUser");
        } catch (Exception e) {
            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
            fail("Unexpected error: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Given("A list of books are available")
    public void listOfBooksAreAvailable() {
        //Construct the URL from base url
        Log.startTestCase("listOfBooksAreAvailable");
        try {

            final String book_list = config.book_endpoint + "/BookStore/v1/Books";
            //Log.info(scenarioName + ": " + "Sending POST request to");
            requestResponse.response = requestSpec.get(book_list).then().extract().response();
            Log.info(scenarioName + ": " + "GET  Response returned: " + requestResponse.response.getBody().asString());
            Log.info(scenarioName + ": " + "GET  Response status code is: " + requestResponse.response.statusCode());
            jsonString = requestResponse.response.asString();
            List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
            Assert.assertTrue(books.size() > 0);

            bookId = books.get(0).get("isbn");
            Log.info("listOfBooksAreAvailable-bookID"+bookId);

            Log.endTestCase("listOfBooksAreAvailable");
        } catch (Exception e) {
            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
            fail("Unexpected error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Then("authorize the user")
    public void authorized() {
        requestSpec.header("Authorization", "Bearer " + token);
    }


    @When("I add a book to my reading list")
    public void addBookInList() {

        //Construct the URL from base url
        Log.startTestCase("addBookInList");
        try {
           // reqbody = "{ \"userId\":\"" + config.book_username + "\", \"password\":\"" + config.book_password + "\"}";
            Log.info("tokenvalue is"+token);
          // requestSpec.header("Authorization", "Basic QVBJLVRlc3Q6VGVzdEBAMTIz");
          //  requestSpec.header("Authorization", "Bearer " + token);
            //String sbook = "1234";
            reqbody = "{\n" +
                    "  \"userId\": \"" + USER_ID + "\",\n" +
                    "  \"collectionOfIsbns\": [\n" +
                    "    {\n" +
                    "      \"isbn\": \""+ bookId + "\" " +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            requestSpec.body(reqbody);
            Log.info(scenarioName + ": " + "Sending POST request body as"+reqbody);

            final String addBook = config.book_endpoint + "/BookStore/v1/Books";
            Log.info(scenarioName + ": " + "Sending POST request to"+addBook);
            requestResponse.response = requestSpec.post(addBook).then().extract().response();
            Log.info(scenarioName + ": " + "POST  Response returned: " + requestResponse.response.getBody().asString());
            Log.info(scenarioName + ": " + "POST  Response status code is: " + requestResponse.response.statusCode());

            Log.endTestCase("addBookInList");
        } catch (Exception e) {
            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
            fail("Unexpected error: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Then("The book is added")
    public void bookIsAdded() {
        Log.info("bookIsAdded method before assert");
        Assert.assertEquals(201, requestResponse.response.getStatusCode());
    }

    @When("I remove a book from my reading list {string}")
    public void removeBookFromList(String fileName) {
        //Construct the URL from base url
        Log.startTestCase("removeBookFromList");
        try {

           // requestSpec.header("Authorization", "Bearer " + token);
           // reqbody = "{ \"isbn\": \"" + bookId + "\", \"userId\": \"" + USER_ID + "\"}";
            this.reqbody = JsonDataReader.readFromFile(fileName);
            requestSpec.body(reqbody);
            JsonObject jsonObject = new Gson().fromJson(reqbody, JsonObject.class);
            jsonObject.addProperty("isbn", bookId);
            this.reqbody = jsonObject.toString();
            requestSpec.body(reqbody);

            Log.info(reqbody);
            final String delBook = config.book_endpoint + "/BookStore/v1/Book";
            Log.info(scenarioName + ": " + "Sending delete request to"+delBook);
            requestResponse.response = requestSpec.delete(delBook).then().extract().response();
            Log.info(scenarioName + ": " + "DELETE  Response returned: " + requestResponse.response.getBody().asString());
            Log.info(scenarioName + ": " + "DELETE  Response status code is: " + requestResponse.response.statusCode());

            Log.endTestCase("removeBookFromList");
        } catch (Exception e) {
            Log.info(scenarioName + ": " + ExceptionUtils.getStackTrace(e));
            fail("Unexpected error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Then("The book is removed")
    public void bookIsRemoved() {

        Assert.assertEquals(204, requestResponse.response.getStatusCode());

    }



















}
