package pl.themolka.itsquery.query;

import pl.themolka.iserverquery.ServerQuery;
import pl.themolka.itsquery.net.InputNetworkHandler;
import pl.themolka.itsquery.net.OutputNetworkHandler;

public class TSQuery extends ServerQuery {
    private final InputNetworkHandler inputHandler;
    private final OutputNetworkHandler outputHandler;

    public TSQuery() {
        this.inputHandler = new InputNetworkHandler(this);
        this.outputHandler = new OutputNetworkHandler(this);
    }

    public InputNetworkHandler getInputHandler() {
        return this.inputHandler;
    }

    public OutputNetworkHandler getOutputHandler() {
        return this.outputHandler;
    }
}
