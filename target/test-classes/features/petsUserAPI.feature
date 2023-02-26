@petUser
Feature: API Test for pets store
Description: The purpose of these tests for Access to Petstore orders APIs

  Create User swagger URL: https://petstore.swagger.io/
  Background: User generates token for authorization
    Given Retrieve dbdata from database "jquery"

  @Post_CreateWithArray
  Scenario Outline: Creates list of users with given input array
   Given Request Retriev to list of user object with "<fileName>"
   Then validate the status code for list of user object
      Examples: 
      | fileName |
      | petUserCreateWithArray.json |

  @Post_CreateWithList
  Scenario Outline: Creates list of users with given input arraylist
   Given Request Retriev to Arraylist of user object with "<fileName>"
   Then validate the status code for Arraylist of user object
      Examples: 
      | fileName |
      | petUserCreateWithList.json |