package br.ciar.web.controllers;

import br.ciar.domain.ocorrencias.EncontroPresencial;
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
@RequestMapping("/encontro_presencial")
@Controller
public class EncontroPresencialController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(
            @RequestParam("nome") String nome,
            @RequestParam("inicio") String inicio,
            @RequestParam("fim") String fim,
            @RequestParam("local_ocorrencia") String localOcorrencia,
            @RequestParam("mapa") String mapa,
            @RequestParam("descricao") String descricao,
            @RequestParam("cliente") String cliente,
            Model uiModel, HttpServletRequest httpServletRequest) {
        
        EncontroPresencial encontroPresencial = new EncontroPresencial();
        encontroPresencial.setNome(nome);
        encontroPresencial.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        encontroPresencial.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        encontroPresencial.setLocalOcorrencia(localOcorrencia);
        encontroPresencial.setMapa(mapa);
        encontroPresencial.setDescricao(descricao);
        encontroPresencial.setCliente(cliente);

        uiModel.asMap().clear();
        encontroPresencial.persist();
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
            Model uiModel, HttpServletRequest httpServletRequest) {

        EncontroPresencial encontroPresencial = EncontroPresencial.findEncontroPresencial(id);
        encontroPresencial.setNome(nome);
        encontroPresencial.setInicio(DataConverter.converterData(inicio, DataConverter.Data.DDMMAAAA));
        encontroPresencial.setFim(DataConverter.converterData(fim, DataConverter.Data.DDMMAAAA));
        encontroPresencial.setLocalOcorrencia(localOcorrencia);
        encontroPresencial.setMapa(mapa);
        encontroPresencial.setDescricao(descricao);
        encontroPresencial.setCliente(cliente);

        uiModel.asMap().clear();
        encontroPresencial.merge();
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