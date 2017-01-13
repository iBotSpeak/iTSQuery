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
                if (value == null) {
                    continue;
                }

                if (value.equals("true")) {
                    value = "1";
                } else if (value.equals("false")) {
                    value = "0";
                }

                builder.append(" ").append(parameter).append("=").append(this.tsQuery.getTextEncoding().encode(value));
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

    public final void execute(String command, Data data) {
        this.execute(command, data, null);
    }

    public final void execute(String command, Data data, Options options) {
        this.execute(command, DataContainer.createOutput(data, options));
    }

    public final boolean executeRaw(String query) {
        return this.tsQuery.getWriter().addQuery(query);
    }

    public final Data createData() {
        return this.createData(null);
    }

    public final Data createData(Map<String, Object> data) {
        if (data != null) {
            return new Data(data);
        }

        return new Data();
    }

    public final Options createOptions() {
        return new Options();
    }

    /*
     * |                                                                                         |
     * | | ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== | |
     * | | >>>>>       >>>>>    TeamSpeak 3 Server Query Default Commands    <<<<<       <<<<< | |
     * | | ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== ===== | |
     * ||                                                                                       ||
     * |  http://media.teamspeak.com/ts3_literature/TeamSpeak%203%20Server%20Query%20Manual.pdf  |
     */

    public void bindingList() {
        this.execute("bindinglist");
    }

    public void channelCreate(@Required String channelName, Map<String, Object> properties) {
        Data data = this.createData(properties);
        data.put("channel_name", channelName);

        this.execute("channelcreate", data);
    }

    public void channelDelete(@Required int cid, @Option boolean force) {
        Data data = this.createData();
        data.put("cid", cid);
        data.put("force", force);

        this.execute("channeldelete", data);
    }

    public void channelEdit(@Required int cid, Map<String, Object> properties) {
        Data data = this.createData(properties);
        data.put("cid", cid);

        this.execute("channeledit", data);
    }

    public void channelFind(String pattern) {
        Data data = this.createData();
        data.put("pattern", pattern);

        this.execute("channelfind", data);
    }

    public void channelGroupAdd(@Required String name, int type) {
        Data data = this.createData();
        data.put("name", name);
        data.put("type", type);

        this.execute("channelgroupadd", data);
    }

    public void channelGroupCopy(@Required int scgid, @Required int tsgid, @Required String name, @Required int type) {
        Data data = this.createData();
        data.put("scgid", scgid);
        data.put("tsgid", tsgid);
        data.put("name", name);
        data.put("type", type);

        this.execute("channelgroupcopy", data);
    }

    public void channelGroupDel(@Required int cgid, boolean force) {
        Data data = this.createData();
        data.put("cgid", cgid);
        data.put("force", force);

        this.execute("channelgroupdel", data);
    }

    public void channelGroupList() {
        this.execute("channelgrouplist");
    }

    public void channelGroupRename(@Required int cgid, @Required String name) {
        Data data = this.createData();
        data.put("cgid", cgid);
        data.put("name", name);

        this.execute("channelgrouprename", data);
    }

    public void channelInfo(@Required int cid) {
        Data data = this.createData();
        data.put("cid", cid);

        this.execute("channelinfo", data);
    }

    public void channelList(@Option boolean topic, @Option boolean flags, @Option boolean voice, @Option boolean limits, @Option boolean icon) {
        Options options = this.createOptions();
        options.put("topic", topic);
        options.put("flags", flags);
        options.put("voice", voice);
        options.put("limits", limits);
        options.put("icon", icon);

        this.execute("channellist", null, options);
    }

    public void channelMove(@Required int cid, @Required int cpid, int order) {
        Data data = this.createData();
        data.put("cid", cid);
        data.put("cpid", cpid);
        data.put("order", order);

        this.execute("channelmove", data);
    }

    public void gm(@Required String msg) {
        Data data = this.createData();
        data.put("msg", msg);

        this.execute("gm", data);
    }

    public void help(String command) {
        this.execute("help");
    }

    public void hostInfo() {
        this.execute("hostinfo");
    }

    public void instanceEdit(@Required Map<String, Object> properties) {
        Data data = this.createData(properties);

        this.execute("instanceedit", data);
    }

    public void instanceInfo() {
        this.execute("instanceinfo");
    }

    public void logAdd(@Required int loglevel, @Required String logmsg) {
        Data data = this.createData();
        data.put("loglevel", loglevel);
        data.put("logmsg", logmsg);

        this.execute("logadd", data);
    }

    public void login(@Required String username, @Required String password) {
        Data data = this.createData();
        data.put("client_login_name", username);
        data.put("client_login_password", password);

        this.execute("login", data);
    }

    public void logView(int lines, boolean reverse, boolean instance, int beginPos) {
        Data data = this.createData();
        data.put("lines", lines);
        data.put("reverse", reverse);
        data.put("instance", instance);
        data.put("begin_pos", beginPos);

        this.execute("logview", data);
    }

    public void logout() {
        this.execute("logout");
    }

    public void sendTextMessage(@Required int targetMode, int target, @Required String msg) {
        Data data = this.createData();
        data.put("targetmode", targetMode);
        data.put("target", target);
        data.put("msg", msg);

        this.execute("sendtextmessage", data);
    }

    public void serverCreate(@Required String name, @Required Map<String, Object> properties) {
        Data data = this.createData(properties);
        data.put("virtualserver_name", name);

        this.execute("servercreate", data);
    }

    public void serverDelete(@Required int sid) {
        Data data = this.createData();
        data.put("sid", sid);

        this.execute("serverdelete", data);
    }

    public void serverEdit(@Required Map<String, Object> properties) {
        Data data = this.createData(properties);

        this.execute("serveredit", data);
    }

    public void serverGroupAdd(@Required String name, int type) {
        Data data = this.createData();
        data.put("name", name);
        data.put("type", type);

        this.execute("servergroupadd", data);
    }

    public void serverGroupAddClient(@Required int sgid, @Required int cldbid) {
        Data data = this.createData();
        data.put("sgid", cldbid);
        data.put("cldbid", cldbid);

        this.execute("servergroupaddclient", data);
    }

    public void serverGroupAddPerm(@Required int sgid, int permid, String permsid, @Required int permvalue, @Required boolean permnegated, @Required int permskip) {
        Data data = this.createData();
        data.put("sgid", sgid);
        data.put("permid", permid);
        data.put("permsid", permsid);
        data.put("permvalue", permvalue);
        data.put("permnegated", permnegated);
        data.put("permskip", permskip);

        this.execute("servergroupaddperm", data);
    }

    public void serverGroupAutoAddPerm(@Required int sgtype, int permid, int permsid, @Required int permvalue, @Required boolean permnegated, @Required boolean permskip) {
        Data data = this.createData();
        data.put("sgtype", sgtype);
        data.put("permid", permid);
        data.put("permsid", permsid);
        data.put("permvalue", permvalue);
        data.put("permnegated", permnegated);
        data.put("permskip", permskip);

        this.execute("servergroupautoaddperm", data);
    }

    public void serverGroupAutoDelPerm(@Required int sgtype, int permid, int permsid) {
        Data data = this.createData();
        data.put("sgtype", sgtype);
        data.put("permid", permid);
        data.put("permsid", permsid);

        this.execute("servergroupautodelperm", data);
    }

    public void serverGroupClientList(@Required int sgid,
                                      @Option boolean names) {
        Data data = this.createData();
        data.put("sgid", sgid);

        Options options = this.createOptions();
        options.put("names", names);

        this.execute("servergroupclientlist", data, options);
    }

    public void serverGroupCopy(@Required int ssgid, @Required int tsgid, @Required String name, @Required int type) {
        Data data = this.createData();
        data.put("ssgid", ssgid);
        data.put("tsgid", tsgid);
        data.put("name", name);
        data.put("type", type);

        this.execute("servergroupcopy", data);
    }

    public void serverGroupDel(@Required int sgid, int force) {
        Data data = this.createData();
        data.put("sqid", sgid);
        data.put("force", force);

        this.execute("servergroupdel", data);
    }

    public void serverGroupDelClient(@Required int sgid, @Required int cldbid) {
        Data data = this.createData();
        data.put("sgid", cldbid);
        data.put("cldbid", cldbid);

        this.execute("servergroupdelclient", data);
    }

    public void serverGroupDelPerm(@Required int sgid, int permid, String permsid) {
        Data data = this.createData();
        data.put("sgid", sgid);
        data.put("permid", permid);
        data.put("permsid", permsid);

        this.execute("servergroupdelperm", data);
    }

    public void serverGroupList() {
        this.execute("servergrouplist");
    }

    public void serverGroupPermList(@Required int sgid,
                                    @Option boolean permsid) {
        Data data = this.createData();
        data.put("sgid", sgid);

        Options options = this.createOptions();
        options.put("permsid", permsid);

        this.execute("servergrouppermlist", data, options);
    }

    public void serverGroupRename(@Required int sgid, @Required String name) {
        Data data = this.createData();
        data.put("sgid", sgid);
        data.put("name", name);

        this.execute("servergrouprename", data);
    }

    public void serverGroupsByClientId(@Required int cldbid) {
        Data data = this.createData();
        data.put("cldbid", cldbid);

        this.execute("servergroupsbyclientid", data);
    }

    public void serverIdGetByPort(@Required int port) {
        Data data = this.createData();
        data.put("virtualserver_port", port);

        this.execute("serveridgetbyport", data);
    }

    public void serverInfo() {
        this.execute("serverinfo");
    }

    public void serverList(@Option boolean uid, @Option boolean _short, @Option boolean all, @Option boolean onlyoffline) {
        Options options = this.createOptions();
        options.put("uid", uid);
        options.put("short", _short);
        options.put("all", all);
        options.put("onlyoffline", onlyoffline);

        this.execute("serverlist", null, options);
    }

    public void serverNotifyRegister(@Required String event, int id) {
        Data data = this.createData();
        data.put("event", event);
        data.put("id", id);

        this.execute("servernotifyregister", data);
    }

    public void serverNotifyUnregister() {
        this.execute("servernotifyunregister");
    }

    public void serverProcessStop() {
        this.execute("serverprocessstop");
    }

    public void serverRequestConnectionInfo() {
        this.execute("serverrequestconnectioninfo");
    }

    public void serverSnapshotCreate() {
        this.execute("serversnapshotcreate");
    }

    public void serverSnapshotDeploy(@Required String args) {
        this.executeRaw("serversnapshotdeploy " + args);
    }

    public void serverStart(@Required int sid) {
        Data data = this.createData();
        data.put("sid", sid);

        this.execute("serverstart", data);
    }

    public void serverStop(@Required int sid) {
        Data data = this.createData();
        data.put("sid", sid);

        this.execute("serverstop", data);
    }

    public void use(int sid, int port,
                    @Option boolean virtual) {
        Data data = this.createData();
        data.put("sid", sid);
        data.put("port", port);

        Options options = this.createOptions();
        options.put("virtual", virtual);

        this.execute("use", data, options);
    }

    public void version() {
        this.execute("version");
    }

    public void quit() {
        this.execute("quit");
    }

    private final class Data extends HashMap<String, Object> {
        public Data() {
            super();
        }

        public Data(Map<String, Object> properties) {
            super(properties);
        }
    }

    private final class Options extends HashMap<String, Boolean> {
    }
}
