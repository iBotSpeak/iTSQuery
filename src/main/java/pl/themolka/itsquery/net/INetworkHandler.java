package pl.themolka.itsquery.net;

/**
 * http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf
 */
public interface INetworkHandler {
    void execute(String command, DataContainer container);
}
