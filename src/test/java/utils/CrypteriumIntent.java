package utils;

import com.testvagrant.intents.Intent;
import com.testvagrant.intents.core.Seeker;
import com.testvagrant.intents.core.SeekerImpl;
import com.testvagrant.intents.exceptions.FeatureNotFoundException;
import com.testvagrant.intents.exceptions.IntentException;
import com.testvagrant.intents.utils.FeatureFinder;
import cucumber.api.DataTable;
import gherkin.ast.Examples;
import gherkin.ast.ScenarioDefinition;
import gherkin.ast.ScenarioOutline;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CrypteriumIntent extends Intent {

    public void run(String intentId, DataTable table) throws IntentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Seeker seeker;
        ScenarioDefinition scenario;
        String newIntentId = intentId + "_" + System.getProperty("testFeed");
        try {
            seeker = new SeekerImpl(newIntentId);
            gherkin.ast.Feature feature = seeker.seekFeature(new FeatureFinder().findFeatures());
            scenario = seeker.seekScenario(feature);
        } catch (FeatureNotFoundException ex) {
            seeker = new SeekerImpl(intentId);
            gherkin.ast.Feature feature = seeker.seekFeature(new FeatureFinder().findFeatures());
            scenario = seeker.seekScenario(feature);
            newIntentId = intentId;
        }

        DataTable dataTable;


        if (scenario instanceof ScenarioOutline) {
            Map<String, String> hashTable;
            if (table != null) {
                hashTable = new HashMap<>(table.transpose().asMap(String.class, String.class));
            } else {
                hashTable = new HashMap<>();
            }

            Examples scenarioDefaultValues = ((ScenarioOutline) scenario).getExamples().get(0);

            for (int index = 0; index < scenarioDefaultValues.getTableHeader().getCells().size(); index++) {

                String header = "<" + scenarioDefaultValues.getTableHeader().getCells().get(index).getValue() + ">";
                if (!hashTable.containsKey(header)) {
                    String value = scenarioDefaultValues.getTableBody().get(0).getCells().get(index).getValue();
                    hashTable.put(header, value);
                }
            }
            List<List<String>> rawData = new ArrayList<>();

            List<String> headers = new ArrayList<>(hashTable.keySet());
            List<String> row = new ArrayList<>(hashTable.values());

            rawData.add(headers);
            rawData.add(row);
            dataTable = DataTable.create(rawData, Locale.getDefault());

        } else {
            dataTable = table;
        }
        if (dataTable != null) {
            this.useDatatable(dataTable);
        }

        super.run(newIntentId);
    }
}
