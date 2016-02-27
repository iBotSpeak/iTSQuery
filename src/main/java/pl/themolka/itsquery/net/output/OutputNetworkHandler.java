package pl.themolka.itsquery.net.output;

import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.INetworkHandler;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;

import java.util.HashMap;
import java.util.Map;

public class OutputNetworkHandler implements INetworkHandler {
    public static final int NONE = Integer.MAX_VALUE;

    protected final TSQuery tsQuery;

    public OutputNetworkHandler(TSQuery tsQuery) {
        this.tsQuery = tsQuery;
    }

    @Override
    public void execute(String command, DataContainer container) {
        StringBuilder builder = new StringBuilder();

        for (QueryData data : container) {
            for (String parameter : data.getFlags()) {
                String value = data.getFlag(parameter);

                if (value != null) {
                    builder.append(" ").append(parameter).append("=").append(this.tsQuery.getTextEncoding().encode(value));
                }
            }

            for (int i = 0; i < data.getParamsLength(); i++) {
                String option = data.getParam(i);

                if (option != null) {
                    builder.append("-").append(option);
                }
            }
        }

        this.executeRaw(command + builder.toString());
    }

    public final void execute(String command) {
        this.execute(command, null, null);
    }

    public final void execute(String command, Map<String, Object> data) {
        this.execute(command, data, null);
    }

    public final void execute(String command, Map<String, Object> data, Map<String, Boolean> options) {
        this.execute(command, DataContainer.createOutput(data, options));
    }

    public final void executeRaw(String query) {
        this.tsQuery.getWriter().addQuery(query);
    }

    public final Map<String, Object> createData() {
        return this.createData(null);
    }

    public final Map<String, Object> createData(Map<String, Object> data) {
        if (data != null) {
            return new HashMap<>(data);
        }

        return new HashMap<>();
    }

    public final Map<String, Boolean> createOptions() {
        return new HashMap<>();
    }

    /*
     * ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====
     * >>>>>       >>>>>    TeamSpeak 3 Server Query Default Commands    <<<<<       <<<<<
     * ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== =====
     *
     * http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf
     */

    public void bindingList() {
        this.execute("bindinglist");
    }

    public void help(String command) {
        this.execute("help");
    }

    public void hostInfo() {
        this.execute("hostinfo");
    }

    public void instanceEdit(@Required Map<String, Object> properties) {
        this.execute("instanceedit", properties);
    }

    public void instanceInfo() {
        this.execute("instanceinfo");
    }

    public void login(@Required String username, @Required String password) {
        Map<String, Object> data = this.createData();
        data.put("client_login_name", username);
        data.put("client_login_password", password);

        this.execute("login", data);
    }

    public void logout() {
        this.execute("logout");
    }

    public void sendTextMessage(@Required int targetMode, int target, @Required String msg) {
        Map<String, Object> data = this.createData();
        data.put("targetmode", targetMode);
        data.put("target", target);
        data.put("msg", msg);

        this.execute("sendtextmessage", data);
    }

    public void serverCreate(@Required String name, @Required Map<String, Object> properties) {
        Map<String, Object> data = this.createData(properties);
        data.put("virtualserver_name", name);

        this.execute("servercreate", data);
    }

    public void serverDelete(@Required int sid) {
        Map<String, Object> data = this.createData();
        data.put("sid", sid);

        this.execute("serverdelete", data);
    }

    public void serverEdit(@Required Map<String, Object> properties) {
        this.execute("serveredit", properties);
    }

    public void serverGroupAdd(@Required String name, int type) {
        Map<String, Object> data = this.createData();
        data.put("name", name);
        data.put("type", type);

        this.execute("servergroupadd", data);
    }

    public void serverGroupDel(@Required int sgid, int force) {
        Map<String, Object> data = this.createData();
        data.put("sqid", sgid);
        data.put("force", force);

        this.execute("servergroupdel", data);
    }

    public void serverGroupList() {
        this.execute("servergrouplist");
    }

    public void serverIdGetByPort(@Required int port) {
        Map<String, Object> data = this.createData();
        data.put("virtualserver_port", port);

        this.execute("serveridgetbyport", data);
    }

    public void serverInfo() {
        this.execute("serverinfo");
    }

    public void serverList(
            @Option boolean uid, @Option boolean _short, @Option boolean all, @Option boolean onlyOffline) {
        Map<String, Boolean> options = this.createOptions();
        options.put("uid", uid);
        options.put("short", _short);
        options.put("all", all);
        options.put("onlyoffline", onlyOffline);

        this.execute("serverlist", null, options);
    }

    public void serverProcessStop() {
        this.execute("serverprocessstop");
    }

    public void serverRequestConnectionInfo() {
        this.execute("serverrequestconnectioninfo");
    }

    public void serverStart(@Required int sid) {
        Map<String, Object> data = this.createData();
        data.put("sid", sid);

        this.execute("serverstart", data);
    }

    public void serverStop(@Required int sid) {
        Map<String, Object> data = this.createData();
        data.put("sid", sid);

        this.execute("serverstop", data);
    }

    public void use(int sid, int port,
                    @Option boolean virtual) {
        Map<String, Object> data = this.createData();
        data.put("sid", sid);
        data.put("port", port);

        Map<String, Boolean> options = this.createOptions();
        options.put("virtual", virtual);

        this.execute("use", data, options);
    }

    public void version() {
        this.execute("version");
    }

    public void quit() {
        this.execute("quit");
    }
}
