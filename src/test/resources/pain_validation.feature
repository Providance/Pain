Feature: PAIN.001 Validation

  Background:
    Given I have loaded the pain.xml file

  Scenario: Validate debtor total amount has at least 2 digits
    When I parse the document
    And I extract the debtor total amount
    Then the debtor total amount should be at least 2 digits

  Scenario: Validate debtor amount equals sum of all credits
    When I parse the document
    And I extract the debtor total amount
    And I calculate the sum of all credit amounts
    Then the debtor total amount should equal the sum of all credits

  Scenario: Validate all IBANs in the payment
    When I parse the document
    And I validate all IBANs in the payment
    Then all IBANs should be valid

  Scenario: Validate transaction date is not in the future
    When I parse the document
    And I extract the payment execution date
    Then the payment execution date should not be in the future

  Scenario: Validate XML can be processed with no invalid data according to ISO 20022
    When I parse the document
    And I validate the document structure is complete
    And I validate all required fields are present
    And I validate all party names are not empty
    And I validate all amounts are positive and numeric
    And I validate all IBANs are properly formatted
    And I validate all BICs are properly formatted
