package br.ciar.web.controllers;

import br.ciar.domain.ocorrencias.Ocorrencia;
import br.ciar.domain.ocorrencias.WebConferencia;
import br.ciar.utils.DataConverter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Lauro
 */
@RequestMapping("/web_conferencia")
@Controller
public class WebConferenciaController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(
            @RequestParam("nome") String nome,
            @RequestParam("inicio") String inicio,
            @RequestParam("fim") String fim,
            @RequestParam("local_ocorrencia") String localOcorrencia,
            @RequestParam("mapa") String mapa,
            @RequestParam("descricao") String descricao,
            @RequestParam("cliente") String cliente,
            @RequestParam("url_acesso") String urlAcesso,
            @RequestParam("url_gravacao") String urlGravacao,
            Model uiModel, HttpServletRequest httpServletRequest) {
        
        WebConferencia webConferencia = new WebConferencia();
        webConferencia.setNome(nome);
        webConferencia.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        webConferencia.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        webConferencia.setLocalOcorrencia(localOcorrencia);
        webConferencia.setMapa(mapa);
        webConferencia.setDescricao(descricao);
        webConferencia.setCliente(cliente);
        webConferencia.setUrlAcesso(urlAcesso);
        webConferencia.setUrlGravacao(urlGravacao);

        uiModel.asMap().clear();
        webConferencia.persist();
        return "redirect:/ocorrencias/lista";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String alterar(
            @RequestParam("id") Integer id,
            @RequestParam("nome") String nome,
            @RequestParam("inicio") String inicio,
            @RequestParam("fim") String fim,
            @RequestParam("local_ocorrencia") String localOcorrencia,
            @RequestParam("mapa") String mapa,
            @RequestParam("descricao") String descricao,
            @RequestParam("cliente") String cliente,
            @RequestParam("url_acesso") String urlAcesso,
            @RequestParam("url_gravacao") String urlGravacao,
            Model uiModel, HttpServletRequest httpServletRequest) {

        WebConferencia webConferencia = WebConferencia.findWebConferencia(id);
        webConferencia.setNome(nome);
        webConferencia.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        webConferencia.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        webConferencia.setLocalOcorrencia(localOcorrencia);
        webConferencia.setMapa(mapa);
        webConferencia.setDescricao(descricao);
        webConferencia.setCliente(cliente);
        webConferencia.setUrlAcesso(urlAcesso);
        webConferencia.setUrlGravacao(urlGravacao);

        uiModel.asMap().clear();
        webConferencia.merge();
        return "redirect:/ocorrencias/lista";
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}