/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.modelo.xml.galeria;

import java.util.ArrayList;

/**
 *
 * @author Osélio
 */
public class Gallery {

    private String title, thumbDir, imageDir, random;
    private ArrayList<Category> categorias;

    public ArrayList<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Category> categorias) {
        this.categorias = categorias;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getThumbDir() {
        return thumbDir;
    }

    public void setThumbDir(String thumbDir) {
        this.thumbDir = thumbDir;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addCategory(Category category){
        if (categorias == null){
            categorias = new ArrayList<Category>();
        }
        categorias.add(category);
    }
}
