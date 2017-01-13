package pl.themolka.itsquery.server;

import pl.themolka.iserverquery.channel.Channel;
import pl.themolka.iserverquery.client.Client;
import pl.themolka.iserverquery.client.ConnectedClient;
import pl.themolka.iserverquery.client.UniqueIdentifier;
import pl.themolka.iserverquery.server.Server;
import pl.themolka.iserverquery.util.Callback;
import pl.themolka.itsquery.query.TSQuery;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TSServer implements Server {
    protected final TSQuery tsQuery;

    private final Set<Channel> channels = new HashSet<>();
    private final Set<ConnectedClient> connectedClients = new HashSet<>();
    private Channel defaultChannel;
    private int reservedSlots;
    private int slots;

    public TSServer(TSQuery tsQuery) {
        this.tsQuery = tsQuery;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void fetchData(Callback callback) {

    }

    @Override
    public void createChannel(Channel channel) {

    }

    @Override
    public ConnectedClient findConnectedClient(String username) {
        for (ConnectedClient client : this.getConnectedClients()) {
            if (client.getUsername().toLowerCase().contains(username.toLowerCase())) {
                return client;
            }
        }

        return null;
    }


    @Override
    public Channel getChannel(int id) {
        for (Channel channel : this.getChannels()) {
            if (channel.getId() == id) {
                return channel;
            }
        }

        return null;
    }

    @Override
    public Channel getChannel(String name) {
        for (Channel channel : this.getChannels()) {
            if (channel.getName().equals(name)) {
                return channel;
            }
        }

        return null;
    }

    @Override
    public Collection<Channel> getChannels() {
        return new HashSet<>(this.channels);
    }

    @Override
    public Client getClient(int databaseId) {
        return null;
    }

    @Override
    public Client getClient(UniqueIdentifier identifier) {
        return null;
    }

    @Override
    public ConnectedClient getConnectedClient(int id) {
        for (ConnectedClient client : this.getConnectedClients()) {
            if (client.getId() == id) {
                return client;
            }
        }

        return null;
    }

    @Override
    public ConnectedClient getConnectedClient(UniqueIdentifier identifier) {
        for (ConnectedClient client : this.getConnectedClients()) {
            if (client.getIdentifier().equals(identifier)) {
                return client;
            }
        }

        return null;
    }

    @Override
    public ConnectedClient getConnectedClient(String username) {
        for (ConnectedClient client : this.getConnectedClients()) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }

        return null;
    }

    @Override
    public Collection<ConnectedClient> getConnectedClients() {
        return new HashSet<>(this.connectedClients);
    }

    @Override
    public Channel getDefaultChannel() {
        return this.defaultChannel;
    }

    @Override
    public int getReservedSlots() {
        return this.reservedSlots;
    }

    @Override
    public int getSlots() {
        return this.slots;
    }

    public void registerChannel(Channel channel) {
        if (this.channels.contains(channel)) {
            return;
        }

        this.channels.add(channel);
    }

    public void registerClient(ConnectedClient client) {
        if (this.connectedClients.contains(client)) {
            return;
        }

        this.connectedClients.add(client);
    }

    public void unregisterChannel(Channel channel) {
        this.channels.remove(channel);
    }

    public void unregisterClient(ConnectedClient client) {
        this.connectedClients.remove(client);
    }
}
