package pl.themolka.itsquery.query;

import pl.themolka.iserverquery.ServerQuery;
import pl.themolka.iserverquery.command.CommandSystem;
import pl.themolka.iserverquery.event.EventSystem;
import pl.themolka.iserverquery.query.*;
import pl.themolka.iserverquery.server.Server;
import pl.themolka.iserverquery.util.Platform;
import pl.themolka.itsquery.net.input.InputNetworkHandler;
import pl.themolka.itsquery.net.input.ReadQueryThread;
import pl.themolka.itsquery.net.output.OutputNetworkHandler;
import pl.themolka.itsquery.net.output.WriteQueryThread;
import pl.themolka.itsquery.server.TSServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class TSQuery implements ServerQuery {
    public static final String BUILD = "Unknown";
    public static final String VERSION = "1.0-SNAPSHOT";

    private final String build = BUILD;
    private final CommandSystem commands;
    private final Charset encoding;
    private final EventSystem events;
    private final String identifier;
    private final InputNetworkHandler inputHandler;
    private final OutputNetworkHandler outputHandler;
    private final Platform platform;
    private final ReadQueryThread reader;
    private final Server server;
    private final Socket socket;
    private final String version = VERSION;
    private final WriteQueryThread writer;

    private boolean running;
    private final Thread shutdownThread;

    public TSQuery(String identifier, String host, int port) throws IOException {
        this(Charset.defaultCharset(), identifier, host, port);
    }

    public TSQuery(Charset encoding, String identifier, String host, int port) throws IOException {
        this.commands = new CommandSystem(this);
        this.encoding = encoding;
        this.events = new EventSystem(identifier);
        this.identifier = identifier;
        this.inputHandler = new InputNetworkHandler(this);
        this.outputHandler = new OutputNetworkHandler(this);
        this.platform = Platform.system();
        this.reader = new ReadQueryThread(this);
        this.server = new TSServer(this);
        this.socket = new Socket(host, port);
        this.writer = new WriteQueryThread(this);

        this.shutdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                TSQuery.this.stop();
            }
        }, identifier + " Shutdown Thread");
        Runtime.getRuntime().addShutdownHook(this.shutdownThread);
    }

    @Override
    public String getBuild() {
        return this.build;
    }

    @Override
    public CommandSystem getCommands() {
        return this.commands;
    }

    @Override
    public Charset getEncoding() {
        return this.encoding;
    }

    @Override
    public EventSystem getEvents() {
        return this.events;
    }

    @Override
    public InetAddress getHost() {
        return this.getSocket().getInetAddress();
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Platform getPlatform() {
        return this.platform;
    }

    @Override
    public int getPort() {
        return this.getSocket().getPort();
    }

    @Override
    public Server getServer() {
        return this.server;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void login(String username, String password) {
        QueryLoginEvent event = new QueryLoginEvent(this, username);
        this.getEvents().post(event);

        this.getOutputHandler().login(event.getUsername(), password);
    }

    @Override
    public void logout() {
        QueryLogoutEvent event = new QueryLogoutEvent(this);
        this.getEvents().post(event);

        this.getOutputHandler().logout();
    }

    @Override
    public void selectById(int serverId, boolean virtual) {
        QuerySelectEvent event = new QuerySelectEvent(this, serverId, QuerySelectEvent.UNKNOWN);
        this.getEvents().post(event);

        this.getOutputHandler().use(event.getServerId(), event.getPort(), virtual);
    }

    @Override
    public void selectByPort(int port, boolean virtual) {
        QuerySelectEvent event = new QuerySelectEvent(this, QuerySelectEvent.UNKNOWN, port);
        this.getEvents().post(event);

        this.getOutputHandler().use(event.getServerId(), event.getPort(), virtual);
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            return;
        }
        this.running = true;

        QueryStartEvent event = new QueryStartEvent(this);
        this.getEvents().post(event);

        this.getReader().start();
        this.getWriter().start();
    }

    @Override
    public void stop() {
        if (!this.isRunning()) {
            return;
        }
        this.running = false;

        QueryStopEvent event = new QueryStopEvent(this);
        this.getEvents().post(event);

        try {
            this.logout();
            Thread.sleep(1000L);
            this.getSocket().close();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        this.getReader().interrupt();
        this.getWriter().interrupt();
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public InputNetworkHandler getInputHandler() {
        return this.inputHandler;
    }

    public OutputNetworkHandler getOutputHandler() {
        return this.outputHandler;
    }

    public ReadQueryThread getReader() {
        return this.reader;
    }

    public Thread getShutdownThread() {
        return this.shutdownThread;
    }

    public WriteQueryThread getWriter() {
        return this.writer;
    }

    public boolean isRunning() {
        return this.running;
    }
}
