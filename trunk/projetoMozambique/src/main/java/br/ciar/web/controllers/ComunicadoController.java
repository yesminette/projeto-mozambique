package br.ciar.web.controllers;

import br.ciar.domain.informativos.Comunicado;
import br.ciar.infraestrutura.ParametrosDoSistema;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Lauro
 */
@RequestMapping("/comunicado")
@Controller
public class ComunicadoController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(HttpServletRequest request) {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile imagemDestaque = multipartRequest.getFile("imagem_destaque");
                MultipartFile imagemInformativo = multipartRequest.getFile("imagem_informativo");
                Comunicado comunicado = new Comunicado();
                comunicado.setTitulo(multipartRequest.getParameter("titulo"));
                comunicado.setDescricao(multipartRequest.getParameter("descricao"));
                comunicado.setConteudo(multipartRequest.getParameter("conteudo"));
                comunicado.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                comunicado.setDataInformativo(new Date());
                comunicado.persist();
                salvarImagens(comunicado.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(ComunicadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "redirect:/informativos/lista";
    }

    @RequestMapping(method = RequestMethod.POST, params = "_method=PUT")
    public String alterarB(HttpServletRequest request) {
        return alterar(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String alterar(HttpServletRequest request) {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile imagemDestaque = multipartRequest.getFile("imagem_destaque");
                MultipartFile imagemInformativo = multipartRequest.getFile("imagem_informativo");
                Integer id = Integer.parseInt(multipartRequest.getParameter("id"));
                Comunicado comunicado = Comunicado.findComunicado(id);
                comunicado.setTitulo(multipartRequest.getParameter("titulo"));
                comunicado.setDescricao(multipartRequest.getParameter("descricao"));
                comunicado.setConteudo(multipartRequest.getParameter("conteudo"));
                comunicado.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                //noticia.setDataInformativo(new Date());
                comunicado.merge();
                salvarImagens(comunicado.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(ComunicadoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "redirect:/informativos/lista";
    }

    private void salvarImagens(int idInformativo, byte[] imagemDestaque, String diretorioImagemDestaque, byte[] imagemInformativo, String diretorioImagemInformativo) throws IOException {
        if (imagemDestaque.length != 0) {
            File imagem = new File(diretorioImagemDestaque, idInformativo + ".jpg");
            imagem.setExecutable(true);
            imagem.setReadable(true);
            imagem.setWritable(true, true);
            FileUtils.writeByteArrayToFile(imagem, imagemDestaque);
        }

        if (imagemInformativo.length != 0) {
            File imagem = new File(diretorioImagemInformativo, idInformativo + ".jpg");
            imagem.setExecutable(true);
            imagem.setReadable(true);
            imagem.setWritable(true, true);
            FileUtils.writeByteArrayToFile(imagem, imagemInformativo);
        }
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

    private String getDiretorioImagemDestaque(HttpServletRequest request) {
        return ParametrosDoSistema.getCaminhoAbsolutoDestaque(request);
    }

    private String getDiretorioImagemInformativo(HttpServletRequest request) {
        return ParametrosDoSistema.getCaminhoAbsolutoNoticia(request);
    }
}
