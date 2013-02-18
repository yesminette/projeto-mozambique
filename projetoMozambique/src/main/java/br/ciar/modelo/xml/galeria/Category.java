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
public class Category {

    private String name;
    private ArrayList<Image> images;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addImage(Image image){
        if (images == null){
            images = new ArrayList<Image>();
        }
        images.add(image);
    }
}
