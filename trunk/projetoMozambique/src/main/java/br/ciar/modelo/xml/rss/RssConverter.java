package br.ciar.modelo.xml.rss;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class RssConverter {

    private XStream xs;

    public RssConverter(XStream xs) {
        this.xs = xs;
        xs.alias("rss", Rss.class);
        xs.alias("item", ItemFeedable.class);
        xs.alias("item", ItemNoticia.class);
        xs.alias("item", ItemOcorrencia.class);
        xs.addImplicitCollection(Channel.class, "items");
        xs.useAttributeFor("version", Version.class);
        xs.registerConverter(new VersionConverter());
    }

    public RssConverter() {
        //this(new XStream(new DomDriver()));
        this(new XStream(new DomDriver() {
            @Override
	    public HierarchicalStreamWriter createWriter(Writer out) {
		return new PrettyPrintWriter(out) {
		    boolean cdata = false;
                    @Override
		    public void startNode(String name, Class clazz){
			super.startNode(name, clazz);
			cdata = (name.equals("description") || name.equals("name") || name.equals("imagem")|| name.equals("comments"));
		    }
                    @Override
		    protected void writeText(QuickWriter writer, String text) {
			if(cdata) {
			    writer.write("<![CDATA[");
			    writer.write(text);
			    writer.write("]]>");
			} else {
			    writer.write(text);
			}
		    }
		};
	    }
	}) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn,
                            String fieldName) {
                        if (definedIn == Object.class && !fieldName.equals("item")) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        });
    }

    public Rss fromXml(InputStream inputStream) {
        return (Rss) xs.fromXML(inputStream);
    }

    public void toXml(Rss rss, OutputStream stream) {
        xs.toXML(rss, stream);
    }

    public void toXml(Rss rss, Writer writer) {
        xs.toXML(rss, writer);
    }

    public String toXml(Rss rss){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\r";
        xml += xs.toXML(rss);
        return xml;
    }
}
