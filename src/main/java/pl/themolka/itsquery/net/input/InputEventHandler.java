package pl.themolka.itsquery.net.input;

import pl.themolka.iserverquery.command.CommandSender;
import pl.themolka.iserverquery.command.CommandSystem;
import pl.themolka.iserverquery.command.DefaultContextParser;
import pl.themolka.iserverquery.event.Event;
import pl.themolka.iserverquery.server.PrivilegeKeyEvent;
import pl.themolka.iserverquery.text.GlobalMessageEvent;
import pl.themolka.iserverquery.text.ImmutableMessage;
import pl.themolka.iserverquery.text.PrivateMessageEvent;
import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.INetworkHandler;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;

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

        }
    }

    public void handleClientDisconnect(DataContainer container) {
        // remove client object
        for (QueryData data : container) {

        }
    }

    public void handleClientMove(DataContainer container) {
        // edit client object
        for (QueryData data : container) {

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
            this.tsQuery.getEvents().post(new PrivilegeKeyEvent(
                    this.tsQuery.getServer()
            ));
        }
    }

    // messages
    public void handleTextMessage(DataContainer container) {
        for (QueryData data : container) {
            String msg = data.getFlag("msg");
            int targetMode = data.getFlagInt("targetmode");

            CommandSender sender = null; // TODO

            CommandSystem commands = this.tsQuery.getCommands();
            if (msg.startsWith(commands.getPrefix())) {
                commands.handleCommand(sender, msg, new DefaultContextParser());
                return;
            }

            Event event = null;
            if (targetMode == 1) { // client
                event = new PrivateMessageEvent(
                        ImmutableMessage.from(msg)
                );
            } else if (targetMode == 2) { // channel
                // should never happen
            } else if (targetMode == 3) { // server
                event = new GlobalMessageEvent(
                        ImmutableMessage.from(msg)
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
