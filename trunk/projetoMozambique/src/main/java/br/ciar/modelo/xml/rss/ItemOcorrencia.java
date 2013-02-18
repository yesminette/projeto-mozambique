/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ciar.modelo.xml.rss;

/**
 *
 * @author Osélio
 */
public class ItemOcorrencia extends Item {

    private String inicio;
    private String fim;
    private String classe;

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
