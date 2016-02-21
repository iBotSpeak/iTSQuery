package pl.themolka.itsquery.net;

import java.util.ArrayList;
import java.util.Map;

public class DataContainer extends ArrayList<QueryData> {
    public static DataContainer create(Map<String, Object> data, Map<String, Boolean> options) {
        return new DataContainer();
    }
}
