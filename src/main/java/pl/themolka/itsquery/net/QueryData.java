package pl.themolka.itsquery.net;

import pl.themolka.iserverquery.command.CommandContext;
import pl.themolka.itsquery.net.input.QueryDataParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryData extends CommandContext {
    private final List<String> flags;
    private final Map<String, String> parameters;
    private String raw;

    public QueryData(Map<String, String> parameters, List<String> options) {
        this(null, parameters, options);
    }

    public QueryData(QueryDataParser.QueryCommand command, Map<String, String> parameters, List<String> options) {
        super(command, parameters, options);

        this.flags = new ArrayList<>(parameters.keySet());
        this.parameters = new HashMap<>();
        this.raw = raw;
    }

    @Override
    public String getFlag(String flag, String def) {
        if (this.parameters.containsKey(flag)) {
            return this.parameters.get(flag);
        }

        return super.getFlag(flag, def);
    }

    public void addParameter(String key, String value) {
        this.flags.add(key);
        this.parameters.put(key, value);
    }

    public List<String> getFlags() {
        return this.flags;
    }

    public String getRaw() {
        return this.raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
