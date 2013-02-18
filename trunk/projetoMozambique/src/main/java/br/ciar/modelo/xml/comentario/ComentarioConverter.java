/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ciar.modelo.xml.comentario;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 *
 * @author Osélio
 */
public class ComentarioConverter {

    private XStream xs;

    public ComentarioConverter(XStream xs) {
        this.xs = xs;
        xs.alias("comentario", ComentarioXML.class);
        xs.alias("comentarios", Comentarios.class);
        xs.addImplicitCollection(Comentarios.class, "comentarios");
    }

    public ComentarioConverter() {
        this(new XStream(new DomDriver()));
    }

    public Comentarios fromXml(InputStream inputStream) {
        return (Comentarios) xs.fromXML(inputStream);
    }

    public void toXml(Comentarios comentarios, OutputStream stream) {
        xs.toXML(comentarios, stream);
    }

    public void toXml(Comentarios comentarios, Writer writer) {
        xs.toXML(comentarios, writer);
    }

    public String toXml(Comentarios comentarios){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\r";
        xml += xs.toXML(comentarios);
        return xml;
    }
}
