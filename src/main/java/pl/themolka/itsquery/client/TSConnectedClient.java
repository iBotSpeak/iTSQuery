package pl.themolka.itsquery.client;

import pl.themolka.iserverquery.channel.Channel;
import pl.themolka.iserverquery.client.ConnectedClient;
import pl.themolka.iserverquery.client.UniqueIdentifier;
import pl.themolka.iserverquery.text.Message;
import pl.themolka.iserverquery.util.Callback;
import pl.themolka.iserverquery.util.Platform;
import pl.themolka.itsquery.query.TSQuery;

public class TSConnectedClient implements ConnectedClient {
    private final TSQuery tsQuery;

    private boolean away;
    private String awayMessage;
    private Channel channel;
    private boolean channelCommander;
    private String country;
    private final int databaseId;
    private String description;
    private final int id;
    private final UniqueIdentifier identifier;
    private boolean muted;
    private Platform platform;
    private boolean prioritySpeaker;
    private boolean recording;
    private int talkPower;
    private int talkRequest;
    private String talkRequestMessage;
    private String username;
    private String version;

    public TSConnectedClient(TSQuery tsQuery, int databaseId, int id, UniqueIdentifier identifier) {
        this.tsQuery = tsQuery;

        this.databaseId = databaseId;
        this.id = id;
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
    public void addPermission() {

    }

    @Override
    public String getAwayMessage() {
        return this.awayMessage;
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public String getCountry() {
        return this.country;
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
        return this.id;
    }

    @Override
    public UniqueIdentifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

    @Override
    public Platform getPlatform() {
        return this.platform;
    }

    @Override
    public int getTalkPower() {
        return this.talkPower;
    }

    @Override
    public int getTalkRequest() {
        return this.talkRequest;
    }

    @Override
    public String getTalkRequestMessage() {
        return this.talkRequestMessage;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean isAway() {
        return this.away;
    }

    @Override
    public boolean isChannelCommander() {
        return this.channelCommander;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public boolean isMuted() {
        return this.muted;
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public boolean isPrioritySpeaker() {
        return this.prioritySpeaker;
    }

    @Override
    public boolean isRecording() {
        return this.recording;
    }

    @Override
    public void kick(Message reason) {

    }

    @Override
    public void move(Channel destination) {

    }

    @Override
    public void poke(Message message) {

    }

    @Override
    public void removePermission() {

    }

    @Override
    public void sendMessage(Message message) {
        this.sendMessage(message.getMessage());
    }

    @Override
    public void sendMessage(String message) {
        this.tsQuery.getOutputHandler().sendTextMessage(3, this.id, message); // should be 1
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectedClient) {
            return ((ConnectedClient) obj).getIdentifier().equals(this.getIdentifier());
        }
        return false;
    }

    public void setAway(boolean away) {
        this.away = away;
    }

    public void setAwayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setChannelCommander(boolean channelCommander) {
        this.channelCommander = channelCommander;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setPrioritySpeaker(boolean prioritySpeaker) {
        this.prioritySpeaker = prioritySpeaker;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    public void setTalkPower(int talkPower) {
        this.talkPower = talkPower;
    }

    public void setTalkRequest(int talkRequest) {
        this.talkRequest = talkRequest;
    }

    public void setTalkRequestMessage(String talkRequestMessage) {
        this.talkRequestMessage = talkRequestMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
