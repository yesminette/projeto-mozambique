/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ciar.modelo.xml.galeria;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 *
 * @author Osélio
 */
public class GaleriaConverter {

    private XStream xs;

    public GaleriaConverter(XStream xs) {
        this.xs = xs;
        xs.alias("gallery", Gallery.class);
        xs.alias("category", Category.class);
        xs.alias("image", Image.class);
        xs.useAttributeFor(Gallery.class, "title");
        xs.useAttributeFor(Gallery.class, "thumbDir");
        xs.useAttributeFor(Gallery.class, "imageDir");
        xs.useAttributeFor(Gallery.class, "random");
        xs.addImplicitCollection(Gallery.class, "categorias");
        xs.addImplicitCollection(Category.class, "images");
        xs.useAttributeFor(Category.class, "name");
    }

    public GaleriaConverter() {
        this(new XStream(new DomDriver()));
    }

    public Gallery fromXml(InputStream inputStream) {
        return (Gallery) xs.fromXML(inputStream);
    }

    public void toXml(Gallery gallery, OutputStream stream) {
        xs.toXML(gallery, stream);
    }

    public void toXml(Gallery gallery, Writer writer) {
        xs.toXML(gallery, writer);
    }

    public String toXml(Gallery gallery){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\r";
        xml += xs.toXML(gallery);
        return xml;
    }
}
