Feature: API Test for books store
Description: The purpose of these tests are to cover Book store mode APIs

Book Store swagger URL: https://bookstore.toolsqa.com/swagger/index.html
  Background: User generates token for authorization
    Given Retrieve dbdata from database "jquery"
    Given I am an authorized valid user
    Then authorize the user


  @Sanity
  Scenario Outline: Authorized user is able to Add or Remove a book
    Given A list of books are available
    When I add a book to my reading list
    Then The book is added
    When I remove a book from my reading list "<fileName>"
    Then The book is removed
    Examples:
      | fileName        |
      | deletebook.json |



