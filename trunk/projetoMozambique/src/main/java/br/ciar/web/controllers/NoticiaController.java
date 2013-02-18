package br.ciar.web.controllers;

import br.ciar.domain.configuracoes.CFG;
import br.ciar.domain.informativos.Noticia;
import br.ciar.domain.informativos.fotografia.Foto;
import br.ciar.domain.informativos.fotografia.Galeria;
import br.ciar.infraestrutura.ParametrosDoSistema;
import br.ciar.utils.Criptografia;
import br.ciar.utils.ImagemUtil;
import br.ciar.utils.TiposImagens;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author Lauro
 */
@RequestMapping("/noticia")
@Controller
public class NoticiaController {

    @RequestMapping(method = RequestMethod.POST)
    public String novo(HttpServletRequest request) {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            try {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile imagemDestaque = multipartRequest.getFile("imagem_destaque");
                MultipartFile imagemInformativo = multipartRequest.getFile("imagem_informativo");
                Noticia noticia = new Noticia();
                noticia.setTitulo(multipartRequest.getParameter("titulo"));
                noticia.setDescricao(multipartRequest.getParameter("descricao"));
                noticia.setConteudo(multipartRequest.getParameter("conteudo"));
                noticia.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                noticia.setAutor(multipartRequest.getParameter("autor"));
                noticia.setDataInformativo(new Date());
                noticia.persist();
                salvarImagens(noticia.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(NoticiaController.class.getName()).log(Level.SEVERE, null, ex);
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
                Noticia noticia = Noticia.findNoticia(id);
                noticia.setTitulo(multipartRequest.getParameter("titulo"));
                noticia.setDescricao(multipartRequest.getParameter("descricao"));
                noticia.setConteudo(multipartRequest.getParameter("conteudo"));
                noticia.setDestaque(null != multipartRequest.getParameter("isdestaque"));
                noticia.setAutor(multipartRequest.getParameter("autor"));
                noticia.setDataInformativo(new Date());
                noticia.merge();
                salvarImagens(noticia.getId(),
                        imagemDestaque.getBytes(),
                        getDiretorioImagemDestaque(request),
                        imagemInformativo.getBytes(),
                        getDiretorioImagemInformativo(request));
            } catch (IOException ex) {
                Logger.getLogger(NoticiaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "redirect:/informativos/lista";
    }

    @RequestMapping(value = "/{id}/galeria/novo", method = RequestMethod.GET)
    public ModelAndView novaGaleria(@PathVariable("id") Integer idNoticia) {
        return new ModelAndView("galeria/novo", "idNoticia", idNoticia);
    }

    @RequestMapping(value = "/{id}/galeria/novo", method = RequestMethod.POST)
    public String novaGaleria(@PathVariable("id") Integer idNoticia, HttpServletRequest request) {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Noticia noticia = Noticia.findNoticia(idNoticia);
            Galeria galeria = null;
            if (noticia.getGaleria() != null) {
                galeria = noticia.getGaleria();
            } else {
                galeria = new Galeria();
                galeria.setNoticia(noticia);
                galeria.persist();
                noticia.setGaleria(galeria);
                noticia.merge();
            }

            MultiValueMap<String, MultipartFile> listaDeItems = multipartRequest.getMultiFileMap();
            for (String nomeItem : listaDeItems.keySet()) {
                MultipartFile imagem = listaDeItems.getFirst(nomeItem);
                if (!imagem.isEmpty()) {
                    try {
                        Integer indice = Integer.parseInt(imagem.getName().replace("foto", ""));
                        String legenda = multipartRequest.getParameter("legenda" + indice);
                        Foto foto = new Foto();
                        foto.setLegenda(legenda);
                        foto.setData_foto(new Date());
                        foto.setGaleria(galeria);
                        inserirFotoEmGaleria(foto, imagem.getBytes(), ParametrosDoSistema.getCaminhoAbsolutoGaleria(request), ParametrosDoSistema.getCaminhoAbsolutoThumbsGaleria(request));
                    } catch (IOException ex) {
                    }
                }
            }
        }

        return "redirect:/informativos/lista";
    }

    @RequestMapping(value = "/{idNoticia}/galeria/{idGaleria}/fotos", method = RequestMethod.GET)
    public ModelAndView fotos(@PathVariable("idNoticia") Integer idNoticia, @PathVariable("idGaleria") Integer idGaleria, HttpServletRequest request) {
        Set<Foto> fotos = Galeria.findGaleria(idGaleria).getFotos();
        String caminhoRelativoThumbs = ParametrosDoSistema.getCaminhoRelativoThumbsGaleria(request);
        ModelAndView view = new ModelAndView("galeria/fotos");
        view.getModel().put("idNoticia", idNoticia);
        view.getModel().put("idGaleria", idGaleria);
        view.getModel().put("fotos", fotos);
        view.getModel().put("caminhoRelativoThumbs", caminhoRelativoThumbs);
        return view;
    }

    @RequestMapping(value = "/{idNoticia}/galeria/{idGaleria}/fotos/{idFoto}", method = RequestMethod.DELETE)
    public String removerFoto(
            @PathVariable("idNoticia") Integer idNoticia,
            @PathVariable("idGaleria") Integer idGaleria,
            @PathVariable("idFoto") Integer idFoto,
            HttpServletRequest request) {

        Foto.findFoto(idFoto).remove();
        return "redirect:/noticia/" + idNoticia + "/galeria/" + idGaleria + "/fotos";
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

    private void inserirFotoEmGaleria(Foto foto, byte[] arquivoFoto, String diretorioImagem, String diretorioThumbs) {
        String nomaArquivosSalvos = salvarFoto(arquivoFoto, diretorioImagem, diretorioThumbs);
        if (!nomaArquivosSalvos.isEmpty()) {
            foto.persist();
            Integer idFoto = foto.getId();
            renomearImagens(idFoto, nomaArquivosSalvos, diretorioImagem, diretorioThumbs);
        }
    }

    /**
     *
     * @param arquivoFoto
     * @param diretorioImagem
     * @param diretorioThumbs
     * @return O nome da imagem criada (em Hash MD5).
     */
    private String salvarFoto(byte[] arquivoFoto, String diretorioImagem, String diretorioThumbs) {
        try {
            String nomeImagem = geraNomeImagem();

            String caminhoImagem = diretorioImagem + nomeImagem + ParametrosDoSistema.getExtensaoImagens();
            BufferedImage imagemFoto = ImagemUtil.resizeImage(new ImageIcon(arquivoFoto).getImage(), TiposImagens.IMAGE_JPEG, ParametrosDoSistema.getLarguraMaxImagens(), ParametrosDoSistema.getAlturaMaxImagens());
            ImagemUtil.saveCompressedImage(imagemFoto, caminhoImagem, TiposImagens.IMAGE_JPEG);

            BufferedImage imagemThumbnail = gerarThumbnail(imagemFoto);
            String caminhoThumbnail = diretorioThumbs + nomeImagem + ParametrosDoSistema.getExtensaoImagens();
            ImagemUtil.saveCompressedImage(imagemThumbnail, caminhoThumbnail, TiposImagens.IMAGE_JPEG);

            return nomeImagem;
        } catch (Exception ex) {
            return "";
        }
    }

    private String geraNomeImagem() {
        Double randomNumber = Math.random();
        return Criptografia.criptografarMD5(randomNumber.toString());
    }

    private void renomearImagens(Integer idFoto, String nomaArquivosSalvos, String diretorioImagem, String diretorioThumbs) {
        File nomeAntigoImagem = new File(diretorioImagem + nomaArquivosSalvos + ParametrosDoSistema.getExtensaoImagens());
        nomeAntigoImagem.setReadable(true, false);
        nomeAntigoImagem.setExecutable(true, false);
        nomeAntigoImagem.setWritable(true, true);
        File nomeAtualImagem = new File(diretorioImagem + idFoto.toString() + ParametrosDoSistema.getExtensaoImagens());
        nomeAtualImagem.setReadable(true, false);
        nomeAtualImagem.setExecutable(true, false);
        nomeAtualImagem.setWritable(true, true);
        nomeAntigoImagem.renameTo(nomeAtualImagem);

        File nomeAntigoThumb = new File(diretorioThumbs + nomaArquivosSalvos + ParametrosDoSistema.getExtensaoImagens());
        nomeAntigoThumb.setReadable(true, false);
        nomeAntigoThumb.setExecutable(true, false);
        nomeAntigoThumb.setWritable(true, true);
        File nomeAtualThumb = new File(diretorioThumbs + idFoto.toString() + ParametrosDoSistema.getExtensaoImagens());
        nomeAtualThumb.setReadable(true, false);
        nomeAtualThumb.setExecutable(true, false);
        nomeAtualThumb.setWritable(true, true);
        nomeAntigoThumb.renameTo(nomeAtualThumb);
    }
    private float PROPORCAO_THUMBNAIL = 0.4f;

    private BufferedImage gerarThumbnail(BufferedImage arquivoFoto) throws IOException {
        int alturaThumb = (int) (PROPORCAO_THUMBNAIL * ((float) arquivoFoto.getHeight()));
        int larguraThumb = (int) (PROPORCAO_THUMBNAIL * ((float) arquivoFoto.getWidth()));
        return ImagemUtil.resizeImage(arquivoFoto, TiposImagens.IMAGE_JPEG, larguraThumb, alturaThumb);
    }
}
