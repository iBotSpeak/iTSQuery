package pl.themolka.itsquery.net.input;

import pl.themolka.iserverquery.command.Command;
import pl.themolka.iserverquery.text.QueryTextEncoding;
import pl.themolka.itsquery.net.QueryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDataParser {
    public static QueryData[] parse(QueryTextEncoding encoder, String commandLine) {
        String[] name = commandLine.split("\\s", 2);
        QueryCommand command = new QueryCommand(name[0]);

        if (name.length != 2) {
            return new QueryData[] {
                    new QueryData(command, new HashMap<String, String>(), new ArrayList<String>())
            };
        }

        List<QueryData> contexts = new ArrayList<>();

        for (String query : name[1].split("\\|")) {
            Map<String, String> parameters = new HashMap<>();
            List<String> options = new ArrayList<>();

            String[] data = query.split("\\s");
            for (int i = 0; i < data.length; i++) {
                String arg = data[i];

                if (arg.contains("=")) {
                    String[] keyValue = arg.split("=");
                    if (keyValue.length != 2) {
                        continue;
                    }

                    parameters.put(keyValue[0], encoder.decode(keyValue[1]));
                } else if (arg.startsWith("-")) {
                    options.add(arg);
                }
            }

            contexts.add(new QueryData(command, parameters, options));
        }

        return contexts.toArray(new QueryData[contexts.size()]);
    }

    public static class QueryCommand extends Command {
        public QueryCommand(String command) {
            super(command, true, null);
        }
    }
}
