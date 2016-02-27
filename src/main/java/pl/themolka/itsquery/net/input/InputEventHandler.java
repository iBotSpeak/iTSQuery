package pl.themolka.itsquery.net.input;

import pl.themolka.iserverquery.client.ClientConnectEvent;
import pl.themolka.iserverquery.client.ClientDisconnectEvent;
import pl.themolka.iserverquery.client.ClientMoveEvent;
import pl.themolka.iserverquery.client.UniqueIdentifier;
import pl.themolka.iserverquery.command.CommandSender;
import pl.themolka.iserverquery.command.CommandSystem;
import pl.themolka.iserverquery.command.DefaultContextParser;
import pl.themolka.iserverquery.event.Event;
import pl.themolka.iserverquery.server.PrivilegeKeyEvent;
import pl.themolka.iserverquery.text.GlobalMessageEvent;
import pl.themolka.iserverquery.text.ImmutableMessage;
import pl.themolka.iserverquery.text.PrivateMessageEvent;
import pl.themolka.itsquery.client.TSConnectedClient;
import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.INetworkHandler;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;
import pl.themolka.itsquery.server.TSServer;

public class InputEventHandler implements INetworkHandler {
    protected final TSQuery tsQuery;

    public InputEventHandler(TSQuery tsQuery) {
        this.tsQuery = tsQuery;
    }

    @Override
    public void execute(String command, DataContainer container) {
        switch (command) {
            // notify channel created
            case "notifychannelcreated":
                break;
            // notify channel deleted
            case "notifychanneldeleted":
                break;
            // notify channel description changed
            case "notifychanneldescriptionchanged":
                break;
            // notify channel edited
            case "notifychanneledited":
                break;
            // notify channel moved
            case "notifychannelmoved":
                break;
            // notify channel password changed
            case "notifychannelpasswordchanged":
                break;

            // notify client enter view
            case "notifycliententerview":
                this.handleClientConnect(container);
                break;
            // notify client left view
            case "notifyclientleftview":
                break;
            // notify client moved
            case "notifyclientmoved":
                break;

            // notify server edited
            case "notifyserveredited":
                break;
            // notify token used
            case "notifytokenused":
                this.handleTokenUsed(container);
                break;

            // notify text message
            case "notifytextmessage":
                this.handleTextMessage(container);
                break;

            default:
                this.callEvent(new InputEventErrorEvent(container, command));
                break;
        }
    }

    // channels
    public void handleChannelCreate(DataContainer container) {
        // create channel object
        for (QueryData data : container) {

        }
    }

    public void handleChannelDelete(DataContainer container) {
        // remove channel object
        for (QueryData data : container) {

        }
    }

    public void handleChannelDescription(DataContainer container) {
        // edit channel object
        for (QueryData data : container) {

        }
    }

    public void handleChannelEdit(DataContainer container) {
        // edit channel object
        for (QueryData data : container) {

        }
    }

    public void handleChannelMove(DataContainer container) {
        // edit channel object
        for (QueryData data : container) {

        }
    }

    public void handleChannelPassword(DataContainer container) {
        // edit channel object
        for (QueryData data : container) {

        }
    }

    // clients
    public void handleClientConnect(DataContainer container) {
        // create client object
        for (QueryData data : container) {
            int channelId = data.getFlagInt("ctid");
            int reasonId = data.getFlagInt("reasonid");
            int clientId = data.getFlagInt("clid");
            UniqueIdentifier identifier = UniqueIdentifier.valueOf(data.getFlag("client_unique_identifier"));
            String username = data.getFlag("client_nickname");
            boolean inputMuted = data.getFlagBoolean("client_input_muted");
            boolean outputMuted = data.getFlagBoolean("client_output_muted");
            boolean inputHardware = data.getFlagBoolean("client_input_hardware");
            boolean outputHardware = data.getFlagBoolean("client_output_hardware");
            boolean recording = data.getFlagBoolean("client_is_recording");
            int databaseId = data.getFlagInt("client_database_id");
            boolean away = data.getFlagBoolean("client_away");
            String awayMessage = data.getFlag("client_away_message");
            boolean query = data.getFlagBoolean("client_type");
            int talkPower = data.getFlagInt("client_talk_power");
            int talkRequest = data.getFlagInt("client_talk_request");
            String talkRequestMessage = data.getFlag("client_talk_request_msg");
            String description = data.getFlag("client_description");
            boolean talker = data.getFlagBoolean("client_is_talker");
            boolean prioritySpeaker = data.getFlagBoolean("client_is_priority_speaker");
            int unreadMessages = data.getFlagInt("client_unread_messages");
            boolean channelCommander = data.getFlagBoolean("client_is_channel_commander");
            String country = data.getFlag("flag_country");

            TSConnectedClient client = new TSConnectedClient(this.tsQuery, databaseId, clientId, identifier);
            client.setAway(away);
            client.setAwayMessage(awayMessage);
            client.setChannel(this.tsQuery.getServer().getChannel(channelId));
            client.setChannelCommander(channelCommander);
            client.setCountry(country);
            client.setDescription(description);
            client.setMuted(inputMuted);
            client.setPlatform(null);
            client.setPrioritySpeaker(prioritySpeaker);
            client.setRecording(recording);
            client.setTalkPower(talkPower);
            client.setTalkRequest(talkRequest);
            client.setTalkRequestMessage(talkRequestMessage);
            client.setUsername(username);
            client.setVersion(null);

            ((TSServer) this.tsQuery.getServer()).registerClient(client);
            this.callEvent(new ClientConnectEvent(client));
        }
    }

    public void handleClientDisconnect(DataContainer container) {
        // remove client object
        for (QueryData data : container) {
            int channelId = data.getFlagInt("cfid");
            int reasonId = data.getFlagInt("reasonid");
            String reasonMessage = data.getFlag("reasomsg");
            int clientId = data.getFlagInt("clid");

            TSServer server = (TSServer) this.tsQuery.getServer();
            TSConnectedClient client = (TSConnectedClient) server.getConnectedClient(clientId);
            server.unregisterClient(client);

            this.callEvent(new ClientDisconnectEvent(client));
        }
    }

    public void handleClientMove(DataContainer container) {
        // edit client object
        for (QueryData data : container) {
            int channelId = data.getFlagInt("ctid");
            int reasonId = data.getFlagInt("reasonid");
            int clientId = data.getFlagInt("clid");

            TSConnectedClient client = (TSConnectedClient) this.tsQuery.getServer().getConnectedClient(clientId);

            this.callEvent(new ClientMoveEvent(client));

            client.setChannel(this.tsQuery.getServer().getChannel(channelId));
        }
    }

    // servers
    public void handleServerEdit(DataContainer container) {
        // edit server object
        for (QueryData data : container) {

        }
    }

    public void handleTokenUsed(DataContainer container) {
        for (QueryData data : container) {
            this.callEvent(new PrivilegeKeyEvent(this.tsQuery.getServer()));
        }
    }

    // messages
    public void handleTextMessage(DataContainer container) {
        for (QueryData data : container) {
            String message = data.getFlag("msg");
            int targetMode = data.getFlagInt("targetmode");
            int invokerId = data.getFlagInt("invokerid");

            CommandSender sender = this.tsQuery.getServer().getConnectedClient(invokerId);
            if (sender == null) {
                sender = this.tsQuery.getConsole();
            }

            CommandSystem commands = this.tsQuery.getCommands();
            if (message.startsWith(commands.getPrefix())) {
                commands.handleCommand(sender, message, new DefaultContextParser());
                return;
            }

            Event event = null;
            if (targetMode == 1) { // client
                event = new PrivateMessageEvent(
                        ImmutableMessage.from(message)
                );
            } else if (targetMode == 2) { // channel
                // should never happen
            } else if (targetMode == 3) { // server
                event = new GlobalMessageEvent(
                        ImmutableMessage.from(message)
                );
            }

            if (event != null) {
                this.callEvent(event);
            }
        }
    }

    private void callEvent(Event event) {
        this.tsQuery.getEvents().post(event);
    }
}
