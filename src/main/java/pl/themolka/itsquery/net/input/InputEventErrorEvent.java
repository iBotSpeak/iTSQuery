package pl.themolka.itsquery.net.input;

import pl.themolka.itsquery.net.DataContainer;

public class InputEventErrorEvent extends InputErrorEvent {
    private final DataContainer data;
    private final String notifyName;

    public InputEventErrorEvent(DataContainer data, String notifyName) {
        super(0, null);

        this.data = data;
        this.notifyName = notifyName;
    }

    public DataContainer getData() {
        return this.data;
    }

    public String getNotifyName() {
        return this.notifyName;
    }
}
