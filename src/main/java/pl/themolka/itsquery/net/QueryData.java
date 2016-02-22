package pl.themolka.itsquery.net;

import pl.themolka.iserverquery.command.CommandContext;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryData extends CommandContext {
    private final Set<String> flags;

    public QueryData(Map<String, String> parameters, List<String> options) {
        this(null, parameters, options);
    }

    public QueryData(QueryDataParser.QueryCommand command, Map<String, String> parameters, List<String> options) {
        super(command, parameters, options);

        this.flags = parameters.keySet();
    }

    public Set<String> getFlags() {
        return this.flags;
    }
}
