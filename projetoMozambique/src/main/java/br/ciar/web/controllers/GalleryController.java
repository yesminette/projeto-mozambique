/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.web.controllers;

import br.ciar.domain.informativos.Noticia;
import br.ciar.domain.informativos.fotografia.Foto;
import br.ciar.domain.informativos.fotografia.Galeria;
import br.ciar.infraestrutura.ParametrosDoSistema;
import br.ciar.modelo.xml.galeria.Category;
import br.ciar.modelo.xml.galeria.GaleriaConverter;
import br.ciar.modelo.xml.galeria.Gallery;
import br.ciar.modelo.xml.galeria.Image;
import br.ciar.utils.DataUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Lauro
 */
@RequestMapping("/gallery")
@Controller
public class GalleryController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String gallery(@RequestParam("numero") Integer numero, HttpServletRequest request) {
        return getGaleria(numero, request);
    }

    public static String getGaleria(int idGaleria, HttpServletRequest request) {
        Galeria galeria = Galeria.findGaleria(idGaleria);
        Noticia noticia = galeria.getNoticia();

        GaleriaConverter converter = new GaleriaConverter();
        Gallery gallery = new Gallery();
        gallery.setImageDir(ParametrosDoSistema.getCaminhoRelativoGaleria(request));
        gallery.setThumbDir(ParametrosDoSistema.getCaminhoRelativoThumbsGaleria(request));

        gallery.setRandom("true");
        gallery.setTitle("CIAR");

        Category categoryPrincipal = new Category();
        categoryPrincipal.setName(noticia.getTitulo());
        for (Foto foto : galeria.getFotos()) {
            Image image = new Image();
            image.setDate(DataUtil.getData(foto.getData_foto()));
            image.setDesc(foto.getLegenda());
            image.setImg(foto.getId() + ".jpg");
            image.setThumb(foto.getId() + ".jpg");
            image.setTitle(foto.getLegenda());
            categoryPrincipal.addImage(image);
        }

        gallery.addCategory(categoryPrincipal);

        // Adicionar às categorias as demais galerias disponíveis
        /*List<Noticia> noticiasComGalerias = Noticia.findNoticiasComGaleria(Integer.MAX_VALUE);
        for (Noticia noticiaComGaleria : noticiasComGalerias) {
            if (noticiaComGaleria.getId() != noticia.getId()) {
                if (noticiaComGaleria.getGaleria().getFotos() != null && noticiaComGaleria.getGaleria().getFotos().size() > 0) {
                    Category category = new Category();
                    category.setName(noticiaComGaleria.getTitulo());
                    for (Foto foto : noticiaComGaleria.getGaleria().getFotos()) {
                        Image image = new Image();
                        image.setDate(DataUtil.getData(foto.getData_foto()));
                        image.setDesc(foto.getLegenda());
                        image.setImg(foto.getId() + ".jpg");
                        image.setThumb(foto.getId() + ".jpg");
                        image.setTitle(foto.getLegenda());
                        category.addImage(image);
                    }
                    gallery.addCategory(category);
                }
            }
        }*/

        return converter.toXml(gallery);
    }
}
