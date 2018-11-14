@registration @smoke @QA-22
Feature: Registration

  @QA-22_1
  Scenario: Sign Up - country from whitelist

    Given user on local Stash put "User phone number" value "Api -> Free phone number"
    Given user on local Stash put "User email" value "Api -> Free email"

    When user on AuthPage screen taps on SignUp
    And user on SignupPage screen types on CountryOfResidence value Api -> Country from whitelist
    And user on SignupPage screen taps on Country
    Then user on SignupPage screen verifies Phone is displayed

    When user on SignupPage screen types on Phone value Stash -> Local : User phone number
    And user on SignupPage screen taps on AcceptTerms
    And user on SignupPage screen taps on Next
    Then user on SignupPage screen verifies VerificationCode is displayed

    When user enters verification code for phone number Stash -> Local : User phone number
    Then user on SignupPage screen verifies FirstName is displayed

    When user on SignupPage screen types on FirstName value TestFirstName
    And user on SignupPage screen types on LastName value TestLastName
    And user on SignupPage screen taps on Next
    Then user on SignupPage screen verifies Email is displayed

    When user on SignupPage screen types on Email value Stash -> Local : User email
    And user on SignupPage screen taps on Next
    Then user on SignupPage screen verifies VerificationCode is displayed

    When user enters verification code for email Stash -> Local : User email
    And user on SignupPage screen types on Password value 12345a
    And user on SignupPage screen types on PasswordConfirm value 12345a

    And user on SignupPage screen taps on Next
    And Intent:Enter and confirm PIN
    Then user on SignupPage screen verifies Finish is displayed
    And user on SignupPage screen taps on Finish

  @QA-22_2
  Scenario: Sign Up - country from blacklist
    When user on AuthPage screen taps on SignUp
    Then user on SignupPage screen verifies CountryOfResidence is displayed

    When user on SignupPage screen types on CountryOfResidence value Api -> Country from blacklist
    And user on SignupPage screen taps on Country
    Then user on SignupPage screen verifies CloseButton is displayed

    When user on SignupPage screen taps on CloseButton
    Then user on SignupPage screen verifies CountryOfResidence is displayed
    And user on SignupPage screen verifies Phone is not displayed

    When user on SignupPage screen types on CountryOfResidence value Api -> Country from blacklist
    And user on SignupPage screen taps on Country
    Then user on SignupPage screen verifies ContinueButton is displayed

    When user on SignupPage screen taps on ContinueButton
    Then user on SignupPage screen verifies CountryOfResidence is displayed
    And user on SignupPage screen verifies Phone is not displayed

