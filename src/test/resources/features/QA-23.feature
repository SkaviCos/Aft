@smoke @authorization @QA-23
Feature: Authorization

  Scenario: Positive
    Given DataIntent: Login
      | <Login>     | <Password> | <Pin> | <LoginType> |
      | 70000020335 | 12345a     | 1111  | phone       |

    Then user on MainPage screen verifies TotalHint is displayed
