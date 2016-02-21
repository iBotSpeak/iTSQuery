package pl.themolka.itsquery.channel;

import pl.themolka.iserverquery.channel.Channel;
import pl.themolka.iserverquery.client.ConnectedClient;
import pl.themolka.iserverquery.text.Message;
import pl.themolka.iserverquery.util.Callback;
import pl.themolka.itsquery.query.TSQuery;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TSChannel implements Channel {
    public static final int CLIENTS_UNLIMITED = -1;

    public static final int STATE_NONE = 0;
    public static final int STATE_TEMPORARY = 1;
    public static final int STATE_PERMANENT = 2;
    public static final int STATE_SEMI_PERMANENT = 3;

    protected final TSQuery tsQuery;

    private int channelState;
    private Message description;
    private final int id;
    private boolean isDefault;
    private int maxClients;
    private int maxFamilyClients;
    private String name;
    private int order;
    private String password;
    private int talkPower;
    private String topic;

    public TSChannel(TSQuery tsQuery, int id) {
        this.tsQuery = tsQuery;
        this.id = id;
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
    public Collection<ConnectedClient> getClients() {
        Set<ConnectedClient> clients = new HashSet<>();
        for (ConnectedClient client : this.tsQuery.getServer().getConnectedClients()) {
            if (client.getChannel().equals(this)) {
                clients.add(client);
            }
        }

        return clients;
    }

    @Override
    public Message getDescription() {
        return this.description;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getMaxClients() {
        return this.maxClients;
    }

    @Override
    public int getMaxFamilyClients() {
        return this.maxFamilyClients;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public int getTalkPower() {
        return this.talkPower;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public boolean hasTalkPower() {
        return this.talkPower != 0;
    }

    @Override
    public boolean hasPassword() {
        return this.password != null;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    @Override
    public boolean isPermanent() {
        return this.channelState == STATE_PERMANENT;
    }

    @Override
    public boolean isSemiPermanent() {
        return this.channelState == STATE_SEMI_PERMANENT;
    }

    @Override
    public boolean isTemporary() {
        return this.channelState == STATE_TEMPORARY;
    }

    @Override
    public boolean isUnlimited() {
        return this.maxClients == CLIENTS_UNLIMITED;
    }

    @Override
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public void setDescription(Message description) {
        this.description = description;
    }

    @Override
    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    @Override
    public void setMaxFamilyClients(int maxClients) {
        this.maxFamilyClients = maxClients;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setPermanent(boolean permanent) {
        if (permanent) {
            this.channelState = STATE_PERMANENT;
        } else {
            this.channelState = STATE_NONE;
        }
    }

    @Override
    public void setSemiPermanent(boolean semi) {
        if (semi) {
            this.channelState = STATE_SEMI_PERMANENT;
        } else {
            this.channelState = STATE_NONE;
        }
    }

    @Override
    public void setTalkPower(int talkPower) {
        this.talkPower = talkPower;
    }

    @Override
    public void setTemporary(boolean temporary) {
        if (temporary) {
            this.channelState = STATE_TEMPORARY;
        } else {
            this.channelState = STATE_NONE;
        }
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void setUnlimited(boolean unlimited) {
        if (unlimited) {
            this.maxClients = CLIENTS_UNLIMITED;
        } else {
            this.maxClients = this.tsQuery.getServer().getSlots();
        }
    }
}
