package br.ciar.modelo.xml.rss;

public class Rss {

    private Version version;
    private Channel channel;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void addItem(ItemFeedable item) {
        if (channel.getItems() != null) {
            channel.getItems().add(item);
        }
    }
}
