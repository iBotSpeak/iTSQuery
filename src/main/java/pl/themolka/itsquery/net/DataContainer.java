package pl.themolka.itsquery.net;

import pl.themolka.itsquery.net.output.OutputNetworkHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataContainer extends ArrayList<QueryData> {
    public static DataContainer createInput(QueryData[] contexts) {
        DataContainer container = new DataContainer();
        container.addAll(Arrays.asList(contexts));
        return container;
    }

    public static DataContainer createOutput(Map<String, Object> parameters, Map<String, Boolean> options) {
        Map<String, String> parametersMap = new HashMap<>();
        if (parameters != null) {
            for (String parameter : parameters.keySet()) {
                String value = String.valueOf(parameters.get(parameter));

                if (value != null && !String.valueOf(OutputNetworkHandler.NONE).equals(value)) {
                    parametersMap.put(parameter, value);
                }
            }
        }

        List<String> optionsList = new ArrayList<>();
        if (options != null) {
            for (String option : options.keySet()) {
                if (options.get(option)) {
                    optionsList.add(option);
                }
            }
        }

        DataContainer container = new DataContainer();
        container.add(new QueryData(parametersMap, optionsList));
        return container;
    }
}
