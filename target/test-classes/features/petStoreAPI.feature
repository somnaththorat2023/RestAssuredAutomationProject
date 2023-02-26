@petStore
Feature: API Test for pets store
Description: The purpose of these tests for Access to Petstore orders APIs

  Create User swagger URL: https://petstore.swagger.io/
  Background: User generates token for authorization
    Given Retrieve dbdata from database "jquery" 
  
  @Post_PetOrderPlaced
  Scenario Outline: order placed for purchasing the pet
   Given Request to retrieve order placed for purchasing the pet with "<fileName>"
   Then validate the status code 
   Examples: 
      | fileName |
      | petStore.json |

  @Get_ReturnMapStatus
  Scenario Outline: Returns a map of status codes to quantities
   Given Fetch the details of returns a map of status codes to quantities
   Then validate the status code for map status
   


