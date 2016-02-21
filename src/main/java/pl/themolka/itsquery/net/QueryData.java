package pl.themolka.itsquery.net;

import pl.themolka.iserverquery.command.CommandContext;

import java.util.List;
import java.util.Map;

public class QueryData extends CommandContext {
    public QueryData(Map<String, String> flags, List<String> params) {
        super(null, flags, params);
    }
}
