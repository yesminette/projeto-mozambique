/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.web.controllers;

import br.ciar.domain.informativos.Informativo;
import br.ciar.domain.informativos.Noticia;
import br.ciar.domain.informativos.Review;
import br.ciar.domain.ocorrencias.Ocorrencia;
import br.ciar.infraestrutura.ParametrosDoSistema;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Lauro
 */
@RequestMapping("/informativos")
@Controller
public class InformativoController {

    @RequestMapping(value = "/noticias", method = RequestMethod.GET)
    public ModelAndView noticias(Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        return new ModelAndView("noticias", "caminhoImagensNoticias", ParametrosDoSistema.getCaminhoRelativoNoticias(request));
    }

    @RequestMapping(value = "/noticia/{id}", method = RequestMethod.GET)
    public ModelAndView noticia(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        Informativo informativo = Informativo.findInformativo(id);
        ModelAndView view = new ModelAndView("noticia");
        view.getModel().put("informativo", informativo);
        view.getModel().put("caminhoImagensNoticias",ParametrosDoSistema.getCaminhoRelativoNoticias(request));
        return view;
    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        return new ModelAndView("informativos/novo");
    }

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public ModelAndView listar(Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        return new ModelAndView("informativos/lista", "informativos", Informativo.findAllInformativosOrdered("dataInformativo", "DESC"));
    }

    @RequestMapping(value = "/alterar/{id}", method = RequestMethod.GET)
    public ModelAndView alterar(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        Informativo informativo = Informativo.findInformativo(id);
        return new ModelAndView("informativos/novo", "informativo", informativo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable("id") Integer idInformativo) {
        Informativo informativo = Informativo.findInformativo(idInformativo);
        if(informativo instanceof Review){
            Review review = (Review) informativo;
            Ocorrencia ocorrencia  = review.getOcorrencia();
            ocorrencia.setReview(null);
            ocorrencia.merge();
        }
        informativo.remove();
        return "redirect:/informativos/lista";
    }

    @RequestMapping(value = "/noticia/gallery", method = RequestMethod.GET)
    public @ResponseBody String galeria(@RequestParam("numero") Integer numero, Model uiModel, HttpServletRequest request) {
        return GalleryController.getGaleria(numero, request);
    }

    @RequestMapping(value = "/galerias", method = RequestMethod.GET)
    public ModelAndView listarNoticiasComGalerias() {
        List<Noticia> noticias = Noticia.findNoticiasComGaleria(Integer.MAX_VALUE);
        return new ModelAndView("galerias", "noticias", noticias);
    }

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public @ResponseBody String galeria2(@RequestParam("numero") Integer numero, Model uiModel, HttpServletRequest request) {
        return GalleryController.getGaleria(numero, request);
    }
}
