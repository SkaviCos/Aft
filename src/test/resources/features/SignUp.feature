Feature: Регистрация

  @signUp
  Scenario: Sign Up
    When user on AuthPage screen taps on SignUp
    Then user on SignupPage screen verifies CountryOfResidence is displayed

    When user on SignupPage screen types on CountryOfResidence value Sri Lanka
    And user on SignupPage screen taps on Country
    And user on SignupPage screen types on Phone value 70000000058
    And user on SignupPage screen taps on AcceptTerms
    Then user on SignupPage screen verifies VerificationCode is displayed

    Then user enters verification code for phone number 70000000058

