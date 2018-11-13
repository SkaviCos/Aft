@smoke @authorization @QA-25
Feature: Feature's access on dashboard dependencies on KYC level and region
  Current test checks whether features are enabled for user with specific KYC level and country

  @QA-25_1
  Scenario: KYC0 - Not Australian
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000000065 | 12345a     | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled |
      | BankTransfer     | false   |
      | SendCrypto       | true    |
      | ReceiveCrypto    | true    |
      | ByyCRPTForCrypto | true    |
      | BuyCRPTForEuro   | false   |
      | TopUp            | false   |

    And user on MainPage screen verifies BankTransfers is not displayed on page
    And user on MainPage screen verifies BPay is not displayed on page


  @QA-25_2
  Scenario: KYC1 - Not Australian
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000000045 | 1234567    | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled | beta |
      | BankTransfer     | true    |      |
      | SendCrypto       | true    |      |
      | ReceiveCrypto    | true    |      |
      | ByyCRPTForCrypto | true    |      |
      | BuyCRPTForEuro   | false   | true |
      | TopUp            | true    |      |

    And user on MainPage screen verifies BankTransfers is not displayed on page
    And user on MainPage screen verifies BPay is not displayed on page

  @QA-25_3
  Scenario: KYC2 - Not Australian
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000000055 | 1234567    | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled | beta |
      | BankTransfer     | true    |      |
      | SendCrypto       | true    |      |
      | ReceiveCrypto    | true    |      |
      | ByyCRPTForCrypto | true    |      |
      | BuyCRPTForEuro   | false   | true |
      | TopUp            | true    |      |

    And user on MainPage screen verifies BankTransfers is not displayed on page
    And user on MainPage screen verifies BPay is not displayed on page

  @QA-25_4
  Scenario: KYC0 - Australian
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000056765 | 12345a     | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled |
      | BankTransfer     | false   |
      | SendCrypto       | true    |
      | ReceiveCrypto    | true    |
      | ByyCRPTForCrypto | true    |
      | BuyCRPTForEuro   | false   |
      | TopUp            | false   |
      | BankTransfers    | false   |
      | BPay             | false   |

  @QA-25_5
  Scenario: KYC1 - Australian
    When test
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000017772 | 12345a     | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled | beta |
      | BankTransfer     | true    |      |
      | SendCrypto       | true    |      |
      | ReceiveCrypto    | true    |      |
      | ByyCRPTForCrypto | true    |      |
      | BuyCRPTForEuro   | false   | true |
      | TopUp            | true    |      |
      | BankTransfers    | true    |      |
      | BPay             | true    |      |

  @QA-25_6
  Scenario: KYC2 - Australian
    Given DataIntent: Login
      | <Login>     | <Password> | <LoginType> |
      | 70000017773 | 12345a     | phone       |

    Then user on MainPage screen verifies TotalHint is displayed

    Then user checks features access
      | name             | enabled | beta |
      | BankTransfer     | true    |      |
      | SendCrypto       | true    |      |
      | ReceiveCrypto    | true    |      |
      | ByyCRPTForCrypto | true    |      |
      | BuyCRPTForEuro   | false   | true |
      | TopUp            | true    |      |
      | BankTransfers    | true    |      |
      | BPay             | true    |      |