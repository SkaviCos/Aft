Feature: Login

  @Intent
  Scenario Outline: Enter and confirm PIN
    When user on PinPage screen types on PinInput value <Pin>
    And user on PinPage screen types on PinConfirmationInput value <Pin>
    Examples:
      | Pin  |
      | 1111 |

#  @Intent
#  Scenario Outline: Login_iOs
#    Given user on AuthPage screen types on LoginInput value <Login>
#    And user on AuthPage screen types on PasswordInput value <Password>
#    And user enters verification code for <LoginType> <Login>
#    Given DataIntent: Enter and confirm PIN
#      | <Pin> |
#      | <Pin> |
#
#    Then user decline iOS suggestions
#
#    Examples:
#      | Login                   | Password         | Pin  | LoginType |
#      | testalexbeta1@gmail.com | 1qaz!QAZ2wsx@WSX | 1111 | email     |

  @Intent
  Scenario Outline: Login
    Given user on AuthPage screen types on LoginInput value <Login>
    And user on AuthPage screen types on PasswordInput value <Password>
    And user on AuthPage screen taps on SubmitButton
    And user enters verification code for <LoginType> <Login>

    Given DataIntent: Enter and confirm PIN
      | <Pin> |
      | <Pin> |

    Examples:
      | Login                   | Password         | Pin  | LoginType |
      | testalexbeta1@gmail.com | 1qaz!QAZ2wsx@WSX | 1111 | email     |
