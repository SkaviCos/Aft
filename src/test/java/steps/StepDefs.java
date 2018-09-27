package steps;

import cucumber.api.java.en.Given;
import pages.CryptMainPage;

public class StepDefs extends BaseSteps{


    @Given("^пользователь авторизуется под логином (.+) и паролем (.+)$")
    public void user_authorize_via_login_and_password(String login, String password) {
        CryptMainPage page = new CryptMainPage(getDriverInstanceFor("optimus"));
        page.authorize(login, password);
    }
}
