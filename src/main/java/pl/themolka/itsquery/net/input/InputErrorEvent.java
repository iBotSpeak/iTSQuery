package pl.themolka.itsquery.net.input;

import pl.themolka.iserverquery.event.Event;

public class InputErrorEvent extends Event {
    public static final int INPUT_EVENT_ID = 0;

    private final int id;
    private final String message;

    public InputErrorEvent(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }
}
