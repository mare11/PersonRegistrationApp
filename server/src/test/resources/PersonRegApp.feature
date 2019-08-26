Feature: PersonRegApp Cucumber

  Scenario: Retrieving all persons
    When client make GET call to /persons
    Then client receives status code of 200
    And the response should contain:
    """
    [{"id":"5d2d922cbefb6045e4a242ec","username":"peca","firstName":"Petar","lastName":"Petrovic","age":28},{"id":"5d2dd940befb604bec8621a1","username":"mika","firstName":"Milan","lastName":"Milanovic","age":56},{"id":"5d2eea4e6b1c623f4818ba1e","username":"joca","firstName":"Jovan","lastName":"Jovanovic","age":35}]
    """

  Scenario: Retrieving one person
    When  client make GET call to /persons/username with username "mika"
    Then client receives status code of 200
    And the response should contain:
    """
    [{"id":"5d2dd940befb604bec8621a1","username":"mika","firstName":"Milan","lastName":"Milanovic","age":56}]
    """
