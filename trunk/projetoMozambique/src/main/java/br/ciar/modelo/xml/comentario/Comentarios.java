/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ciar.modelo.xml.comentario;

import java.util.ArrayList;

/**
 *
 * @author Osélio
 */
public class Comentarios {

    private ArrayList comentarios;

    public Comentarios() {
    }

    public ArrayList getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList comentarios) {
        this.comentarios = comentarios;
    }

    public void addComentario(ComentarioXML comentario){
        if(comentarios == null){
            comentarios = new ArrayList();
        }
        comentarios.add(comentario);
    }
    
}
