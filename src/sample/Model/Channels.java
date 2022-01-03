package sample.Model;

public class Channels {
    private String ChannelName;
    private String ChannelOwner;

    public Channels(String channelName, String channelOwner) {
        ChannelName = channelName;
        ChannelOwner = channelOwner;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getChannelOwner() {
        return ChannelOwner;
    }

    public void setChannelOwner(String channelOwner) {
        ChannelOwner = channelOwner;
    }
}
