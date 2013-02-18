/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.web.controllers;

import br.ciar.domain.ocorrencias.Comentario;
import br.ciar.domain.ocorrencias.Ocorrencia;
import br.ciar.modelo.xml.comentario.ComentarioConverter;
import br.ciar.modelo.xml.comentario.ComentarioXML;
import br.ciar.modelo.xml.comentario.Comentarios;
import br.ciar.utils.DataUtil;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/ocorrencias")
@Controller
public class OcorrenciaController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView ocorrencias(Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        return new ModelAndView("ocorrencias");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView ocorrencia(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(id);
        return new ModelAndView("ocorrencia", "ocorrencia", ocorrencia);
    }

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo(Model uiModel, HttpServletRequest request) {
        uiModel.asMap().clear();
        return new ModelAndView("ocorrencias/novo");
    }

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public ModelAndView listar(Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        return new ModelAndView("ocorrencias/lista", "ocorrencias", Ocorrencia.findAllOcorrenciasOrdered("inicio", "DESC"));
    }

    @RequestMapping(value = "/alterar/{id}", method = RequestMethod.GET)
    public ModelAndView alterar(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(id);
        return new ModelAndView("ocorrencias/novo", "ocorrencia", ocorrencia);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable("id") Integer id) {
        Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(id);
        ocorrencia.remove();
        return "redirect:/ocorrencias/lista";
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
    public ModelAndView getComentarios(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest request) {
        Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(id);
        uiModel.asMap().clear();
        return new ModelAndView("ocorrencias/comentarios", "ocorrencia", ocorrencia);
    }

    @RequestMapping(value = "/{id}/comentar", method = RequestMethod.POST)
    public @ResponseBody String inserirComentario(@PathVariable("id") Integer idOcorrencia,
            @RequestParam("limite") Integer limite,
            @RequestParam("nome") String nome,
            @RequestParam("novo_comentario") String textoComentario,
            Model uiModel, HttpServletRequest request,
            HttpServletResponse response) {

        response.setContentType("text/html;charset=ISO-8859-1");
        uiModel.asMap().clear();

        if (!textoComentario.trim().isEmpty()) {
            String enderecoIP = request.getRemoteAddr();
            Date hora = new Date();
            if (nome.trim().isEmpty()) {
                nome = "Anônimo";
            }
            Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(idOcorrencia);
            Comentario comentario = new Comentario();
            comentario.setNome(nome);
            comentario.setConteudo(textoComentario);
            comentario.setHora(hora);
            comentario.setEnderecoip(enderecoIP);
            comentario.setOcorrencia(ocorrencia);
            comentario.persist();
        }
        return getComentariosXML(idOcorrencia, limite);
    }

    @RequestMapping(value = "/{id}/comentarios/{id_comentario}", method = RequestMethod.DELETE)
    public String removerComentario(@PathVariable("id") Integer idOcorrencia,
            @PathVariable("id_comentario") Integer idComentario,
            Model uiModel, HttpServletRequest request) {
        Comentario comentario = Comentario.findComentario(idComentario);
        comentario.remove();
        uiModel.asMap().clear();
        return "redirect:/ocorrencias/"+idOcorrencia+"/comentarios";
    }

    public String getComentariosXML(int idOcorrencia, int limite) {
        List<Comentario> comentarios = Ocorrencia.getComentarios(idOcorrencia, limite);

        ComentarioConverter converter = new ComentarioConverter();
        Comentarios comentariosXml = new Comentarios();
        for (Comentario comentario : comentarios) {
            ComentarioXML comentarioXML = new ComentarioXML();
            comentarioXML.setConteudo(comentario.getConteudo());
            comentarioXML.setNome(comentario.getNome());
            comentarioXML.setTempo(DataUtil.calculaTempoDecorrido(comentario.getHora()));
            comentariosXml.addComentario(comentarioXML);
        }

        return converter.toXml(comentariosXml);
    }

    @RequestMapping(value = "/gallery", method = RequestMethod.GET)
    public @ResponseBody String galeria(@RequestParam("numero") Integer numero, Model uiModel, HttpServletRequest request) {
        return GalleryController.getGaleria(numero, request);
    }
}
