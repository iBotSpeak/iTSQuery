package pl.themolka.itsquery.net.input;

import pl.themolka.itsquery.net.DataContainer;

public class InputResponseErrorEvent extends InputErrorEvent {
    private final String command;
    private final DataContainer container;

    public InputResponseErrorEvent(String command, DataContainer container) {
        super(0, null);

        this.command = command;
        this.container = container;
    }

    public String getCommand() {
        return this.command;
    }

    public DataContainer getDataContainer() {
        return this.container;
    }
}
