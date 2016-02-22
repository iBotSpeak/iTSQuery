package pl.themolka.itsquery.net;

public class ValueMode {
    private final String name;
    private final int value;

    protected ValueMode(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public static ValueMode create(String name, int value) {
        return new ValueMode(name, value);
    }

    public static class CodecEncriptionMode {
        public static final ValueMode CRYPT_INDIVIDUAL = create("CODEC_CRYPT_INDIVIDUAL", 0);
        public static final ValueMode CRYPT_DISABLED = create("CODEC_CRYPT_DISABLED", 1);
        public static final ValueMode CRYPT_ENABLED = create("CODEC_CRYPT_ENABLED", 2);
    }

    public static class CodecMode {
        public static final ValueMode SPEEX_NARROWBAND = create("CODEC_SPEEX_NARROWBAND", 0);
        public static final ValueMode SPEEX_WIDEBAND = create("CODEC_SPEEX_WIDEBAND", 1);
        public static final ValueMode SPEEX_ULTRA_WIDEBAND = create("CODEC_SPEEX_ULTRAWIDEBAND,", 2);
        public static final ValueMode CELT_MONO = create("CODEC_CELT_MONO", 3);
    }

    public static class GroupDatabaseMode {
        public static final ValueMode TEMPLATE = create("PermGroupDBTypeTemplate", 0);
        public static final ValueMode REGULAR = create("PermGroupDBTypeRegular", 1);
        public static final ValueMode QUERY = create("PermGroupDBTypeQuery", 2);
    }

    public static class GroupMode {
        public static final ValueMode SERVER_GROUP = create("PermGroupTypeServerGroup", 0);
        public static final ValueMode GLOBAL_CLIENT = create("PermGroupTypeGlobalClient", 1);
        public static final ValueMode CHANNEL = create("PermGroupTypeChannel", 2);
        public static final ValueMode CHANNEL_GROUP = create("PermGroupTypeChannelGroup", 3);
        public static final ValueMode CHANNEL_CLIENT = create("PermGroupTypeChannelClient", 4);
    }

    public static class HostBannerMode {
        public static final ValueMode NO_ADJUST = create("HostMessageMode_NOADJUST", 0);
        public static final ValueMode IGNORE_ASPECT = create("HostMessageMode_IGNOREASPECT,", 1);
        public static final ValueMode KEEP_ASPECT = create("HostMessageMode_KEEPASPECT", 2);
    }

    public static class HostMessageMode {
        public static final ValueMode LOG = create("HostMessageMode_LOG", 1);
        public static final ValueMode MODAL = create("HostMessageMode_MODAL", 2);
        public static final ValueMode MODAL_QUIT = create("HostMessageMode_MODALQUIT", 3);
    }

    public static class LogLevelMode {
        public static final ValueMode ERROR = create("LogLevel_ERROR", 1);
        public static final ValueMode WARNING = create("LogLevel_WARNING", 2);
        public static final ValueMode DEBUG = create("LogLevel_DEBUG", 3);
        public static final ValueMode INFO = create("LogLevel_INFO", 4);
    }

    public static class ReasonMode {
        public static final ValueMode KICK_CHANNEL = create("REASON_KICK_CHANNEL", 1);
        public static final ValueMode KICK_SERVER = create("REASON_KICK_SERVER", 2);
    }

    public static class TextMessageProperty {
        public static final ValueMode CLIENT = create("TextMessageTarget_CLIENT", 1);
        public static final ValueMode CHANNEL = create("TextMessageTarget_CHANNEL", 2);
        public static final ValueMode SERVER = create("TextMessageTarget_SERVER", 3);
    }

    public static class TokenMode {
        public static final ValueMode SERVER_GROUP = create("TokenServerGroup", 0);
        public static final ValueMode CHANNEL_GROUP = create("TokenChannelGroup", 1);
    }
}
