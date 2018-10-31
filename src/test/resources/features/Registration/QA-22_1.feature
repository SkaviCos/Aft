@registration @smoke
Feature: Регистрация

  @QA-22_1
  Scenario: Sign Up - country from whitelist

    Given user on local Stash put "User phone number" value "Api -> Free phone number"

    When user on AuthPage screen taps on SignUp
    Then user on SignupPage screen verifies CountryOfResidence is displayed

    When user on SignupPage screen types on CountryOfResidence value Api -> Country from whitelist
    And user on SignupPage screen taps on Country
    Then user on SignupPage screen verifies Phone is displayed

    When user on SignupPage screen types on Phone value Stash -> Local : User phone number
    And user on SignupPage screen taps on AcceptTerms
    And user on SignupPage screen taps on Next

    Then user on SignupPage screen verifies VerificationCode is displayed

    Then user enters verification code for phone number Stash -> Local : User phone number

