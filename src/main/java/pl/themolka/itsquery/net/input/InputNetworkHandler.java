package pl.themolka.itsquery.net.input;

import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.INetworkHandler;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;

public class InputNetworkHandler implements INetworkHandler {
    protected final TSQuery tsQuery;

    private final InputEventHandler queryEvents;

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
        } else {

        }
    }
}
