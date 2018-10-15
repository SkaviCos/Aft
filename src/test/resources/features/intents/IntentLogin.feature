Feature: Login

  @Intent
  Scenario: LoginAsSupervisor
    Given DataIntent:Login
      | <Login> | <Password> |
      | ivan    | ivan       |

  @Intent
  Scenario Outline: Login_iOs
    Given user on AuthPage screen types on Login value <Login>
    And user on AuthPage screen types on Password value <Password>

    When user on PinPage screen types on Pin value 1111
    And user on PinPage screen types on PinConfirmation value 1111
    Then user decline iOS suggestions

    Examples:
      | Login                   | Password         |
      | testalexbeta1@gmail.com | 1qaz!QAZ2wsx@WSX |

  @Intent
  Scenario Outline: Login_Android
    Given user on AuthPage screen types on Login value <Login>
    And user on AuthPage screen types on Password value <Password>
    And user on AuthPage screen taps on Submit

    When user on PinPage screen types on Pin value 1111
    And user on PinPage screen types on PinConfirmation value 1111

    Examples:
      | Login                   | Password         |
      | testalexbeta1@gmail.com | 1qaz!QAZ2wsx@WSX |
