@smoke @authorization @QA-23
Feature: Authorization

  Scenario: Positive
    Given DataIntent: Login
      | <Login>     | <Password> | <Pin> | <LoginType> |
      | 70000020335 | 12345a     | 1111  | phone       |

    And user on Onboard screen taps on Close
    Then user on MainPage screen verifies Iban_Transfer is displayed