package pl.themolka.itsquery.net.input;

import pl.themolka.iserverquery.io.ResponseListener;
import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.INetworkHandler;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputNetworkHandler implements INetworkHandler {
    protected final TSQuery tsQuery;

    private final InputEventHandler queryEvents;
    private final Map<String, List<ResponseListener>> responses = new HashMap<>();

    public InputNetworkHandler(TSQuery tsQuery) {
        this.tsQuery = tsQuery;
        this.queryEvents = new InputEventHandler(tsQuery);
    }

    @Override
    public void execute(String command, DataContainer container) {
        if (command.equals("error")) {
            for (QueryData data : container) {
                int id = data.getFlagInt("id");
                String msg = data.getFlag("msg");

                if (id > 0) {
                    this.tsQuery.getEvents().post(new InputErrorEvent(id, msg));
                }
            }
        } else if (command.startsWith("notify")) {
            this.queryEvents.execute(command, container);
        } else if (this.responses.containsKey(command)) {
            for (QueryData data : container) {
                Map<String, String> parameters = new HashMap<>();
                for (String parameter : data.getFlags()) {
                    parameters.put(parameter, parameters.get(parameter));
                }

                List<String> options = new ArrayList<>();
                for (int i = 0; i < data.getParamsLength(); i++) {
                    options.add(data.getParam(i));
                }

                for (ResponseListener listener : this.responses.get(command)) {
                    try {
                        listener.onResponse(command, parameters, options);
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            this.tsQuery.getEvents().post(new InputResponseErrorEvent(command, container));
        }
    }

    public void registerResponse(String command, ResponseListener listener) {
        if (!this.responses.containsKey(command)) {
            this.responses.put(command, new ArrayList<ResponseListener>());
        }

        this.responses.get(command).add(listener);
    }

    public void unregisterResponse(String command, ResponseListener listener) {
        if (this.responses.containsKey(command)) {
            this.responses.get(command).remove(listener);
        }
    }
}
