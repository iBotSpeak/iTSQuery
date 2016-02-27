package pl.themolka.itsquery.query;

import pl.themolka.iserverquery.ServerQuery;
import pl.themolka.iserverquery.command.CommandSystem;
import pl.themolka.iserverquery.command.Console;
import pl.themolka.iserverquery.event.EventSystem;
import pl.themolka.iserverquery.io.ResponseListener;
import pl.themolka.iserverquery.query.QueryLoginEvent;
import pl.themolka.iserverquery.query.QueryLogoutEvent;
import pl.themolka.iserverquery.query.QuerySelectEvent;
import pl.themolka.iserverquery.query.QueryStartEvent;
import pl.themolka.iserverquery.query.QueryStopEvent;
import pl.themolka.iserverquery.server.Server;
import pl.themolka.iserverquery.text.QueryTextEncoding;
import pl.themolka.iserverquery.util.Platform;
import pl.themolka.iserverquery.util.ShutdownHook;
import pl.themolka.itsquery.net.input.InputNetworkHandler;
import pl.themolka.itsquery.net.input.ReadQueryThread;
import pl.themolka.itsquery.net.output.OutputNetworkHandler;
import pl.themolka.itsquery.net.output.WriteQueryThread;
import pl.themolka.itsquery.server.TSServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class TSQuery implements ServerQuery {
    public static final String BUILD = "Unknown";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    public static final String DEFAULT_IDENTIFIER = "Default";

    private final InetSocketAddress address;
    protected String build = BUILD;
    private final CommandSystem commands;
    private final Console console;
    private final Charset encoding;
    private final EventSystem events;
    private final String identifier;
    private final InputNetworkHandler inputHandler;
    private final OutputNetworkHandler outputHandler;
    private final Platform platform;
    private ReadQueryThread reader;
    private boolean running;
    private final Server server;
    private final ShutdownHook shutdownHook;
    private Socket socket;
    private final QueryTextEncoding textEncoding;
    protected String version = VERSION;
    private WriteQueryThread writer;

    public TSQuery(String identifier, String host, int port) throws IOException {
        this(null, identifier, host, port);
    }

    public TSQuery(Charset encoding, String identifier, String host, int port) throws IOException {
        if (encoding == null) {
            encoding = DEFAULT_CHARSET;
        }
        if (identifier == null) {
            identifier = DEFAULT_IDENTIFIER;
        }

        this.address = new InetSocketAddress(host, port);
        this.commands = new CommandSystem(this);
        this.console = new Console(this);
        this.encoding = encoding;
        this.events = new EventSystem(identifier);
        this.identifier = identifier;
        this.inputHandler = new InputNetworkHandler(this);
        this.outputHandler = new OutputNetworkHandler(this);
        this.platform = Platform.system();
        this.server = new TSServer(this);
        this.shutdownHook = new ShutdownHook(this);
        this.textEncoding = new QueryTextEncoding();

        Runtime.getRuntime().addShutdownHook(this.getShutdownHook());
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
    public Console getConsole() {
        return this.console;
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
    public InetSocketAddress getHost() {
        return this.address;
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
    public Server getServer() {
        return this.server;
    }

    @Override
    public ShutdownHook getShutdownHook() {
        return this.shutdownHook;
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public QueryTextEncoding getTextEncoding() {
        return this.textEncoding;
    }

    @Override
    public boolean isRunning() {
        return this.running;
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
    public void query(String query) {
        this.getOutputHandler().executeRaw(query);
    }

    @Override
    public void registerResponse(String command, ResponseListener listener) {
        this.getInputHandler().registerResponse(command, listener);
    }

    @Override
    public void selectById(int serverId, boolean virtual) {
        this.select(serverId, QuerySelectEvent.UNKNOWN, virtual);
    }

    @Override
    public void selectByPort(int port, boolean virtual) {
        this.select(QuerySelectEvent.UNKNOWN, port, virtual);
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            return;
        }
        this.running = true;

        QueryStartEvent event = new QueryStartEvent(this);
        this.getEvents().post(event);

        try {
            this.socket = new Socket();
            this.socket.connect(this.getHost());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        this.reader = new ReadQueryThread(this);
        this.reader.start();

        this.writer = new WriteQueryThread(this);
        this.writer.start();
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

    public WriteQueryThread getWriter() {
        return this.writer;
    }

    private void select(int serverId, int port, boolean virtual) {
        QuerySelectEvent event = new QuerySelectEvent(this, port, serverId, virtual);
        this.getEvents().post(event);

        int preparedServerId = event.getServerId();
        if (preparedServerId == QuerySelectEvent.UNKNOWN) {
            preparedServerId = OutputNetworkHandler.NONE;
        }

        int preparedPort = event.getPort();
        if (preparedPort == QuerySelectEvent.UNKNOWN) {
            preparedPort = OutputNetworkHandler.NONE;
        }

        OutputNetworkHandler output = this.getOutputHandler();
        output.use(preparedServerId, preparedPort, event.isVirtual());

        output.serverNotifyRegister("channel", 0);
        output.serverNotifyRegister("server", OutputNetworkHandler.NONE);
        output.serverNotifyRegister("textprivate", OutputNetworkHandler.NONE);
        output.serverNotifyRegister("textserver", OutputNetworkHandler.NONE);
    }
}
