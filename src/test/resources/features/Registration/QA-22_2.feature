@registration @smoke
Feature: Регистрация

  @QA-22_1
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
