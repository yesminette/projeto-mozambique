/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.infraestrutura;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Osélio
 */
@Component
public class ParametrosDoSistema {

    private static Propriedades propriedades;

    public static String getCaminhoRelativoGaleria(HttpServletRequest request) {
        String diretorioGaleriaRelativo = "";
        String diretorioUploadRelativo = getDiretorioUploadRelativo(request);

        if (getDiretorioGaleria() != null) {
            if (!getDiretorioGaleria().isEmpty()) {
                diretorioGaleriaRelativo = diretorioUploadRelativo + getDiretorioGaleria() + "/";
            }
        }

        return diretorioGaleriaRelativo;
    }

    public static String getCaminhoRelativoThumbsGaleria(HttpServletRequest request) {
        String diretorioThumbsGaleria = "";
        String diretorioGaleriaRelativo = getCaminhoRelativoGaleria(request);

        if (getDiretorioThumbs() != null) {
            if (!getDiretorioThumbs().isEmpty()) {
                diretorioThumbsGaleria = diretorioGaleriaRelativo + getDiretorioThumbs() + "/";
            }
        }

        return diretorioThumbsGaleria;
    }

    public static String getCaminhoAbsolutoGaleria(HttpServletRequest request) {
        String diretorioGaleriaAbsoluto = "";
        String diretorioUploadAbsoluto = getDiretorioUploadAbsoluto(request);

        if (getDiretorioGaleria() != null) {
            if (!getDiretorioGaleria().isEmpty()) {
                diretorioGaleriaAbsoluto = diretorioUploadAbsoluto + getDiretorioGaleria() + "/";
            }
        }

        File file = new File(diretorioGaleriaAbsoluto);

        if (!file.exists()) {
            file.mkdir();
        }

        return diretorioGaleriaAbsoluto;
    }

    public static String getCaminhoAbsolutoThumbsGaleria(HttpServletRequest request) {
        String diretorioThumbsGaleria = "";
        String diretorioGaleriaAbsoluto = getCaminhoAbsolutoGaleria(request);

        if (getDiretorioThumbs() != null) {
            if (!getDiretorioThumbs().isEmpty()) {
                diretorioThumbsGaleria = diretorioGaleriaAbsoluto + getDiretorioThumbs() + "/";
            }
        }

        File file = new File(diretorioThumbsGaleria);

        if (!file.exists()) {
            file.mkdir();
        }

        return diretorioThumbsGaleria;
    }

    public static String getDiretorioUploadRelativo(HttpServletRequest request) {
        String diretorioUploadRelativo = "";

        String enderecoServidor = getEnderecoRelativoServidor(request);

        if (getDiretorioUpload() != null) {
            if (!getDiretorioUpload().isEmpty()) {
                diretorioUploadRelativo = enderecoServidor + getDiretorioUpload() + "/";
            }
        }

        return diretorioUploadRelativo;
    }

    public static String getEnderecoRelativoServidor(HttpServletRequest request) {
        String endereco_servidor = "";

        if (getEnderecoServidorUpload() != null) {
            if (!getEnderecoServidorUpload().isEmpty()) {
                endereco_servidor = getEnderecoServidorUpload() + "/";
            } else {
                if (request != null) {
                    String schema = request.getScheme();
                    String porta = request.getServerPort() != 0 ? ":" + request.getServerPort() : "";
                    String servidor = request.getServerName();
                    endereco_servidor = schema + "://" + servidor + porta + "/";
                }
            }
        }

        return endereco_servidor;
    }

    public static String getDiretorioUploadAbsoluto(HttpServletRequest request) {
        String diretorioUploadAbsoluto = "";

        String enderecoServidor = getEnderecoAbsolutoServidor(request);

        if (getDiretorioUpload() != null) {
            if (!getDiretorioUpload().isEmpty()) {
                diretorioUploadAbsoluto = enderecoServidor + getDiretorioUpload() + "/";
            }
        }

        return diretorioUploadAbsoluto;
    }

    public static String getEnderecoAbsolutoServidor(HttpServletRequest request) {
        String endereco_servidor = "";

        if (getEnderecoServidorUploadAbsoluto() != null) {
            if (!getEnderecoServidorUploadAbsoluto().isEmpty()) {
                endereco_servidor = getEnderecoServidorUploadAbsoluto() + "/";
            } else {
                String root = request.getSession().getServletContext().getRealPath("/");
                root = root.substring(0, root.lastIndexOf(request.getSession().getServletContext().getContextPath().replace("/", barra())));
                if(root.contains("ROOT")){
                    root = root.substring(0,root.lastIndexOf("/ROOT".replace("/", barra())));
                }
                endereco_servidor = root + barra();
            }
        }

        return endereco_servidor;
    }

    public static String barra() {
        return System.getProperty("file.separator");
    }

    public static String getCaminhoAbsolutoNoticia(HttpServletRequest request) {
        String diretorioUploadAbsoluto = getDiretorioUploadAbsoluto(request);
        String diretorioNoticia = "";

        if (getDiretorioImagensNoticias() != null) {
            if (!getDiretorioImagensNoticias().isEmpty()) {
                diretorioNoticia = diretorioUploadAbsoluto + getDiretorioImagensNoticias() + "/";
            }
        }

        return diretorioNoticia;
    }

    public static String getCaminhoAbsolutoDestaque(HttpServletRequest request) {
        String diretorioUploadAbsoluto = getDiretorioUploadAbsoluto(request);
        String diretorioNoticia = "";

        if (getDiretorioImagensDestaques() != null) {
            if (!getDiretorioImagensDestaques().isEmpty()) {
                diretorioNoticia = diretorioUploadAbsoluto + getDiretorioImagensDestaques() + "/";
            }
        }

        return diretorioNoticia;
    }

    public static String getCaminhoRelativoNoticias(HttpServletRequest request) {
        String diretorioNoticia = "";
        String diretorioUploadRelativo = getDiretorioUploadRelativo(request);

        if (getDiretorioImagensNoticias() != null) {
            if (!getDiretorioImagensNoticias().isEmpty()) {
                diretorioNoticia = diretorioUploadRelativo + getDiretorioImagensNoticias() + "/";
            }
        }

        return diretorioNoticia;
    }

    public static String getCaminhoRelativoDestaque(HttpServletRequest request) {
        String diretorioUploadRelativo = getDiretorioUploadRelativo(request);
        String diretorioNoticia = "";

        if (getDiretorioImagensDestaques() != null) {
            if (!getDiretorioImagensDestaques().isEmpty()) {
                diretorioNoticia = diretorioUploadRelativo + getDiretorioImagensDestaques() + "/";
            }
        }

        return diretorioNoticia;
    }

    public static String getDiretorioImagensNoticias() {
        return propriedades.getDiretorioImagensNoticias();
    }

    private static String getDiretorioGaleria() {
        return propriedades.getDiretorioGaleria();
    }

    private static String getDiretorioThumbs() {
        return propriedades.getDiretorioThumbs();
    }

    private static String getDiretorioUpload() {
        return propriedades.getDiretorioUpload();
    }

    private static String getEnderecoServidorUpload() {
        return propriedades.getEnderecoServidorUpload();
    }

    private static String getEnderecoServidorUploadAbsoluto() {
        return propriedades.getEnderecoServidorUploadAbsoluto();
    }

    private static String getDiretorioImagensDestaques() {
        return propriedades.getDiretorioImagensDestaques();
    }

    public static String getExtensaoImagens(){
        return propriedades.getExtensaoImagens();
    }

    public static int getAlturaMaxImagens() {
        return propriedades.getAlturaMaxImagens();
    }

    public static int getAlturaThumbs() {
        return propriedades.getAlturaThumbs();
    }

    public static int getImageQuality() {
        return propriedades.getImageQuality();
    }

    public static int getLarguraMaxImagens() {
        return propriedades.getLarguraMaxImagens();
    }

    public static int getLarguraThumbs() {
        return propriedades.getLarguraThumbs();
    }

    public static Propriedades getPropriedades() {
        return propriedades;
    }

    @Autowired(required=true)
    public void setPropriedades(Propriedades propriedades) {
        ParametrosDoSistema.propriedades = propriedades;
    }
}
