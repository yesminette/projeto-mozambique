package br.ciar.web.controllers;

import br.ciar.domain.informativos.Review;
import br.ciar.domain.ocorrencias.Ocorrencia;
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
@RequestMapping("/review")
@Controller
public class ReviewController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(HttpServletRequest request) {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile imagemDestaque = multipartRequest.getFile("imagem_destaque");
                MultipartFile imagemInformativo = multipartRequest.getFile("imagem_informativo");
                Review review = new Review();
                review.setTitulo(multipartRequest.getParameter("titulo"));
                review.setDescricao(multipartRequest.getParameter("descricao"));
                review.setConteudo(multipartRequest.getParameter("conteudo"));
                review.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                review.setDataInformativo(new Date());
                Integer idOcorrencia = Integer.parseInt(multipartRequest.getParameter("id_ocorrencia"));
                Ocorrencia ocorrencia = Ocorrencia.findOcorrencia(idOcorrencia);
                review.setOcorrencia(ocorrencia);
                review.persist();
                ocorrencia.setReview(review);
                ocorrencia.merge();
                salvarImagens(review.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
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
                Review review = Review.findReview(id);
                review.setTitulo(multipartRequest.getParameter("titulo"));
                review.setDescricao(multipartRequest.getParameter("descricao"));
                review.setConteudo(multipartRequest.getParameter("conteudo"));
                review.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                //noticia.setDataInformativo(new Date());
                review.merge();
                salvarImagens(review.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(ReviewController.class.getName()).log(Level.SEVERE, null, ex);
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
