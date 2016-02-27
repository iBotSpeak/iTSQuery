package pl.themolka.itsquery.client;

import pl.themolka.iserverquery.client.Client;
import pl.themolka.iserverquery.client.UniqueIdentifier;
import pl.themolka.iserverquery.util.Callback;
import pl.themolka.itsquery.query.TSQuery;

public class TSClient implements Client {
    protected final TSQuery tsQuery;

    private final int databaseId;
    private String description;
    private final UniqueIdentifier identifier;
    private String username;

    public TSClient(TSQuery tsQuery, int databaseId, UniqueIdentifier identifier) {
        this.tsQuery = tsQuery;

        this.databaseId = databaseId;
        this.identifier = identifier;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void fetchData(Callback callback) {

    }

    @Override
    public void updateData() {

    }

    @Override
    public void updateData(Callback callback) {

    }

    @Override
    public int getDatabaseId() {
        return this.databaseId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public UniqueIdentifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isOnline() {
        return this.tsQuery.getServer().getConnectedClient(this.getIdentifier()) != null;
    }
}
