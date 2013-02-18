package br.ciar.web.controllers;

import br.ciar.domain.ocorrencias.Evento;
import br.ciar.utils.DataConverter;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Lauro
 */
@RequestMapping("/evento")
@Controller
public class EventoController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(
            @RequestParam("nome") String nome,
            @RequestParam("inicio") String inicio,
            @RequestParam("fim") String fim,
            @RequestParam("local_ocorrencia") String localOcorrencia,
            @RequestParam("mapa") String mapa,
            @RequestParam("descricao") String descricao,
            Model uiModel, HttpServletRequest httpServletRequest) {
        
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        evento.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        evento.setLocalOcorrencia(localOcorrencia);
        evento.setMapa(mapa);
        evento.setDescricao(descricao);

        uiModel.asMap().clear();
        evento.persist();
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
            Model uiModel, HttpServletRequest httpServletRequest) {

        Evento evento = Evento.findEvento(id);
        evento.setNome(nome);
        evento.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        evento.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        evento.setLocalOcorrencia(localOcorrencia);
        evento.setMapa(mapa);
        evento.setDescricao(descricao);

        uiModel.asMap().clear();
        evento.merge();
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