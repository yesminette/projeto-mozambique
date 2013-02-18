package br.ciar.modelo.xml.rss;

public interface ItemFeedable {

    public String getTitle();

    public void setTitle(String title);

    public String getLink();

    public void setLink(String link);

    public String getDescription();

    public void setDescription(String description);

    public String getGuid();

    public void setGuid(String guid);

    public String getPubDate();

    public void setPubDate(String pubDate);

    public String getComments();

    public void setComments(String comments);

    public String getNumero();

    public void setNumero(String numero);
}
