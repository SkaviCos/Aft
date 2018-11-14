package steps;

import com.testvagrant.commons.exceptions.OptimusException;
import com.testvagrant.stepdefs.exceptions.NoSuchEventException;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import utils.CommonFunctions;
import utils.CrypteriumIntent;
import utils.Stash;

import java.io.IOException;

import static com.testvagrant.stepdefs.core.Tapster.tapster;

public class GenericSteps extends steps.BaseSteps {



    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)\\s+value\\s+(.*)$")
    public void consumerOnScreenPerformsActionOnElementWithValue(String consumer, String screen, String action, String element, String value) throws NoSuchEventException, OptimusException, IOException {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .onElement(element)
                .doAction(action)
                .withValue(formattedValue)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)\\s+values \\s+(.*),\\s+(.*)$")
    public void consumerOnScreenPerformsActionOnElementWithTwoParams(String consumer, String screen, String action, String element, String value1, String value2) throws NoSuchEventException, OptimusException, IOException {
        String formattedValue1 = CommonFunctions.getValueByTypeAndName(consumer, value1);
        String formattedValue2 = CommonFunctions.getValueByTypeAndName(consumer, value2);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .onElement(element)
                .doAction(action)
                .withValues(formattedValue1, formattedValue2)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)\\s+values \\s+(.*),\\s+(.*),\\s+(.*)$")
    public void consumerOnScreenPerformsActionOnElementWithTwoParams(String consumer, String screen, String action, String element, String value1, String value2, String value3) throws NoSuchEventException, OptimusException, IOException {
        String formattedValue1 = CommonFunctions.getValueByTypeAndName(consumer, value1);
        String formattedValue2 = CommonFunctions.getValueByTypeAndName(consumer, value2);
        String formattedValue3 = CommonFunctions.getValueByTypeAndName(consumer, value3);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .onElement(element)
                .doAction(action)
                .withValues(formattedValue1, formattedValue2, formattedValue3)
                .serve();
    }

    @And("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)$")
    public void consumerOnScreenPerformsActionOnElement(String consumer, String screen, String action, String element) throws OptimusException, NoSuchEventException, IOException {

        tapster().useDriver(getDriverInstanceFor(consumer))
                .onScreen(screen)
                .asConsumer(consumer)
                .onElement(element)
                .doAction(action)
                .serve();
    }

    @And("^(\\w+)\\s+on\\s+(\\w+)\\sscreen verifies\\s+(\\w+)\\s+is\\s+(.*)$")
    public void assertelement(String consumer, String screen, String element, String action) throws NoSuchEventException, IOException, OptimusException {
        tapster().useDriver(getDriverInstanceFor(consumer))
                .onScreen(screen)
                .asConsumer(consumer)
                .onElement(element)
                .doAction(action)
                .serve();
    }

    @And("^(\\w+)\\s+on\\s+(\\w+)\\sscreen verifies\\s+(\\w+)\\s+has\\s+(\\w+)\\s+value\\s+(.*)$")
    public void assertelementwithvalue(String consumer, String screen, String element, String action, String value) throws OptimusException, NoSuchEventException, IOException {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);

        tapster().useDriver(getDriverInstanceFor(consumer))
                .onScreen(screen)
                .asConsumer(consumer)
                .onElement(element)
                .doAction(action)
                .withValue(formattedValue)
                .serve();
    }

    @And("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)\\s+with index as\\s+(\\w+)\\s+and value is\\s+(.*)$")
    public void consumerOnScreenPerformsActionOnListOfElementsWithValue(String consumer, String screen, String action, String element, int index, String value) throws NoSuchEventException, OptimusException, IOException, InterruptedException {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);

        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .onElement(element)
                .doAction(action)
                .withIndex(index)
                .withValue(formattedValue)
                .serve();
    }

    @And("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+(\\w+)\\s+on\\s+(\\w+)\\s+with index as\\s+(\\w+)$")
    public void consumerOnScreenPerformsActionOnListOfElements(String consumer, String screen, String action, String element, int index) throws NoSuchEventException, OptimusException, IOException, InterruptedException {
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .onElement(element)
                .doAction(action)
                .withIndex(index)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen(?:\\s+perform|)\\s+\\((.*)\\)$")
    public void consumerOnScreenPerformsAction(String consumer, String screen, String action) throws NoSuchEventException, IOException, OptimusException {
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .doAction(action)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+\\((.*)\\)\\s+with value\\s+\"(.*)\"$")
    public void consumerOnScreenPerformsActionWithOneParam(String consumer, String screen, String action, String value) throws NoSuchEventException, IOException, OptimusException {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .doAction(action)
                .withValue(formattedValue)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+\\((.*)\\)\\s+with values\\s+\"(.*)\",\\s+\"(.*)\"$")
    public void consumerOnScreenPerformsActionWithTwoParam(String consumer, String screen, String action, String value1, String value2) throws NoSuchEventException, IOException, OptimusException {
        String formattedValue1 = CommonFunctions.getValueByTypeAndName(consumer, value1);
        String formattedValue2 = CommonFunctions.getValueByTypeAndName(consumer, value2);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .doAction(action)
                .withValues(formattedValue1, formattedValue2)
                .serve();
    }

    @Given("^(\\w+)\\s+on\\s+(\\w+)\\s+screen\\s+performs\\s+(.*)\\s+with values\\s+\"(.*)\",\"(.*)\",\"(.*)\"$")
    public void consumerOnScreenPerformsActionWithTwoParam(String consumer, String screen, String action, String value1, String value2, String value3) throws NoSuchEventException, IOException, OptimusException {
        String formattedValue1 = CommonFunctions.getValueByTypeAndName(consumer, value1);
        String formattedValue2 = CommonFunctions.getValueByTypeAndName(consumer, value2);
        String formattedValue3 = CommonFunctions.getValueByTypeAndName(consumer, value3);
        tapster().useDriver(getDriverInstanceFor(consumer))
                .asConsumer(consumer)
                .onScreen(screen)
                .doAction(action)
                .withValues(formattedValue1, formattedValue2, formattedValue3)
                .serve();
    }

    @Given("^(?:Intent):(.*)$")
    public void intent(String intentId) throws Throwable {
        new CrypteriumIntent().run(intentId, null);

    }

    @Given("^(?:DataIntent):(.*)$")
    public void dataIntent(String intentId, DataTable table) throws Throwable {
        new CrypteriumIntent().run(intentId.trim(), table);
    }

    @Given("^(\\w+) on (global|local) Stash put \"([^\"]*)\" value \"([^\"]*)\"$")
    public void putToStash(String consumer, String area, String key, String value) {
        String formattedValue = CommonFunctions.getValueByTypeAndName(consumer, value);
        System.out.println(formattedValue);
        switch (area) {
            case "global":
                Stash.putGlobal(key, formattedValue);
                break;
            case "local":
                Stash.putLocal(consumer, key, formattedValue);
                break;
        }
    }

}
