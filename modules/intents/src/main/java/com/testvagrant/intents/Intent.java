package com.testvagrant.intents;


import com.testvagrant.intents.builders.MethodExecutorBuilder;
import com.testvagrant.intents.core.MethodExecutor;
import com.testvagrant.intents.core.Seeker;
import com.testvagrant.intents.core.SeekerImpl;
import com.testvagrant.intents.exceptions.IntentException;
import com.testvagrant.intents.exceptions.NoMatchingStepFoundException;
import com.testvagrant.intents.utils.FeatureFinder;
import cucumber.api.DataTable;
import cucumber.runtime.table.DataTableDiff;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.Step;
import gherkin.ast.TableCell;
import gherkin.ast.TableRow;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * An Intent is a small reusable action, similar to a scenario. However it has no purpose, if it isn't used in a context.
 * <br>
 * <b>Eg:</b>
 * <pre>
 *     <code>
 *         {@literal @}Intent
 *          <b>Scenario:</b> Login
 *          <b>Given</b> User enters username as JohnNash
 *          <b>And</b> User enters password as NashJohn
 *          <b>When</b> User clicks on submit
 *          <b>Then</b> User is navigated to HomeScreen
 *     </code>
 * </pre>
 * <br>
 * <b>Usage:</b>
 * <pre>
 *     <code>
 *         <b>Scenario:</b> Book a cab
 *         <b>Given </b> <b>Intent:</b> Login
 *         <b>When</b>  I enter to and from locations
 *         <b>And</b>   I click on BookACab
 *         <b>Then</b>  I should receive a booking confirmation
 *     </code>
 * </pre>
 * <p>
 * Sometimes it is needed to override default values as in the above example user would like to login other than 'JohnNash'.
 * <p>
 * It is possible to modify the default values of Intent as below.
 * <b>Usage:</b>
 * <pre>
 *     <code>
 *         <b>Scenario:</b> Book a cab
 *         <b>Given </b>  <b>DataIntent:</b> Login
 *         |Admin|pass@123|
 *         <b>When</b> I enter to and from locations
 *         <b>And</b>  I click on BookACab
 *         <b>Then</b> I should receive a booking confirmation
 *     </code>
 * </pre>
 *
 * <b>Using intent in code:</b>
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         intentRunner(intentId).run();
 *     </code>
 * </pre>
 *
 * <br>
 * If the default data is overridden, pass the datatable object to intent runner.
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         intentRunner(intentId).useDatatable(datatable).run();
 *     </code>
 * </pre>
 * <br>
 * If the step definitions are created in a package apart form the standard <b>com.testvagrant.intents.steps</b> package, you can let Intent know about it as below.
 * <pre>
 *     <code>
 *         String intentId = "Login";
 *         String stepDefinitionsPackage = "com.example.exampleStepsPackage";
 *         intentRunner(intentId)
 *         .useDatatable(datatable)
 *         .locateStepDefinitionsAt(stepDefinitionsPackage)
 *         .run();
 *     </code>
 * </pre>
 */

public class Intent {

    private static List<gherkin.ast.Feature> features = new ArrayList<>();
    private String intentId;
    private Optional<DataTable> dataTable;
    private Optional<String> stepDefinitionPackage;
    private Optional<String> jarStepDefinitionPackage;
    private Map<String, String> params = new HashMap<>();

    public Intent() {
        dataTable = Optional.empty();
        stepDefinitionPackage = Optional.empty();
        jarStepDefinitionPackage = Optional.empty();
        findFeatures();

    }

    public Intent(String stepDefsPackage) {
        stepDefinitionPackage = Optional.of(stepDefsPackage);
    }

    public Intent useDatatable(DataTable datatable) {
        dataTable = Optional.of(datatable);
        return this;
    }

    public Intent locateStepDefinitionsAt(String packageName) {
        stepDefinitionPackage = Optional.of(packageName);
        return this;
    }

    public Intent locateJarStepDefinitionsAt(String packageName) {
        jarStepDefinitionPackage = Optional.of(packageName);
        return this;
    }

    public void run(String intentId) throws IntentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Seeker seeker = new SeekerImpl(intentId);
        gherkin.ast.Feature feature = seeker.seekFeature(features);
        ScenarioDefinition elements = seeker.seekScenario(feature);
        MethodExecutor executor = new MethodExecutorBuilder()
                .withDataTable(dataTable)
                .withJarStepDefinitionPackageName(jarStepDefinitionPackage)
                .withStepDefinationPackageName(stepDefinitionPackage)
                .build();
        executor.findPatterns();
        dataTable.ifPresent(dataTable1 -> params = dataTable1.transpose().asMap(String.class, String.class));

        for (Step step : elements.getSteps()) {

            try {
                if (step.getArgument() != null && step.getArgument() instanceof gherkin.ast.DataTable) {
                    List<TableRow> rows = ((gherkin.ast.DataTable) step.getArgument()).getRows();
                    List<List<String>> table = rows.stream()
                            .map(e -> e.getCells().stream()
                                    .map(cell -> {
                                        if (cell.getLocation().getLine() > 0
                                                && cell.getValue().startsWith("<")
                                                && cell.getValue().endsWith(">")) {
                                            return params.getOrDefault(cell.getValue(), cell.getValue());
                                        }
                                        return cell.getValue();
                                    })
                                    .collect(Collectors.toList()))
                            .collect(Collectors.toList());
                    DataTable dataTable = DataTable.create(table);
                    executor.exec(step.getText(), dataTable);
                } else {
                    executor.exec(step.getText());
                }
            } catch (NoMatchingStepFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }


    private void findFeatures() {
        if (features.size() == 0) {
            features = new FeatureFinder().findFeatures();
        }
    }
}
