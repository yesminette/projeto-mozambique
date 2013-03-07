/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.web.controllers;

import br.ciar.domain.informativos.Artigo;
import br.ciar.domain.informativos.Comunicado;
import br.ciar.domain.informativos.Informativo;
import br.ciar.domain.informativos.Noticia;
import br.ciar.domain.informativos.Review;
import br.ciar.domain.ocorrencias.Comentario;
import br.ciar.domain.ocorrencias.Conferencia;
import br.ciar.domain.ocorrencias.EncontroPresencial;
import br.ciar.domain.ocorrencias.Evento;
import br.ciar.domain.ocorrencias.Ocorrencia;
import br.ciar.domain.ocorrencias.WebConferencia;
import br.ciar.infraestrutura.ParametrosDoSistema;
import br.ciar.modelo.xml.comentario.ComentarioConverter;
import br.ciar.modelo.xml.comentario.ComentarioXML;
import br.ciar.modelo.xml.comentario.Comentarios;
import br.ciar.modelo.xml.rss.ItemNoticia;
import br.ciar.modelo.xml.rss.ItemOcorrencia;
import br.ciar.modelo.xml.rss.Rss;
import br.ciar.modelo.xml.rss.RssConverter;
import br.ciar.rss.RssFactory;
import br.ciar.rss.TIPORSS;
import br.ciar.utils.DataConverter;
import br.ciar.utils.DataConverter.Data;
import br.ciar.utils.DataUtil;
import br.ciar.utils.Paginacao;
import br.ciar.utils.StringUtils;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lauro
 */
@RequestMapping("/busca")
@Controller
public class BuscaController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getXML(
            @RequestParam(value = "tipo", required = true, defaultValue = "0") Integer tipo,
            @RequestParam(value = "limite", required = false, defaultValue = "1000") Integer limite,
            @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer pagina,
            @RequestParam(value = "mes", required = false, defaultValue = "0") Integer mes,
            @RequestParam(value = "ano", required = false, defaultValue = "0") Integer ano,
            @RequestParam(value = "numero", required = false, defaultValue = "0") Integer numero,
            @RequestParam(value = "chavebusca", required = false, defaultValue = "") String textoBusca,
            @RequestParam(value = "categoria", required = false, defaultValue = "informativo") String categoria,
            Model uiModel,
            HttpServletRequest request,
            HttpServletResponse response) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType("application", "rss+xml"));
        response.setContentType("application/rss+xml;charset=ISO-8859-1");

        String conteudo = "";
        int itensPorPagina = 6;

        if (mes == 0) {
            mes = (Calendar.getInstance().get(Calendar.MONTH) + 1);
        }
        if (ano == 0) {
            ano = Calendar.getInstance().get(Calendar.YEAR);
        }

        switch (tipo) {
            case 0://todos informativos, sem limite
                conteudo = getXMLInformativos(textoBusca, pagina, limite, categoria, itensPorPagina);
                break;
            case 1://Todos Informativos, quantidade Limitada
                conteudo = getXMLInformativos(textoBusca, pagina, limite, categoria, itensPorPagina);
                break;
            case 2:// pega os informativos por palavras-chave, quantidade limitada
                conteudo = getXMLInformativos(textoBusca, pagina, limite, categoria, itensPorPagina);
                break;
            case 3:// pega os informativos por palavras-chave (sem limite)
                conteudo = getXMLInformativos(textoBusca, pagina, limite, categoria, itensPorPagina);
                break;
            case 4:// pega os informativos filtrados por seu tipo (categoria)
                conteudo = getXMLInformativos(textoBusca, pagina, limite, categoria, itensPorPagina);
                break;
            case 5://getDestaques
                conteudo = getDestaques(limite, request);
                break;
            case 6://getOcorrencias
                conteudo = getOcorrencias(limite);
                break;
            case 7://getOcorrencias
                if (mes != 0) {//Mes zero para quando a pessoa nao seleciona o mes
                    conteudo = getOcorrencias(limite, mes, ano);
                } else {
                    conteudo = getOcorrencias(limite);
                }
                break;
            case 8:// getComentarios
                conteudo = getComentarios(numero, limite);
                break;
        }

        return new ResponseEntity<String>(conteudo, responseHeaders, HttpStatus.CREATED);
    }

    public String getXMLInformativos(String textoBusca, int pagina, int limite, String categoria, int itensPorPagina) {
        String[] textosBusca = StringUtils.quebrarEFormatarString(textoBusca);

        int primeiroResultado = Paginacao.inicioItens(pagina, itensPorPagina);

        List<Informativo> listaInformativos = null;
        long quantidadePaginas = 0;
        long quantidadeInformativos = 0;

        if (categoria.equalsIgnoreCase("artigo")) {
            listaInformativos = Artigo.getArtigos(textosBusca, primeiroResultado, limite);
            quantidadeInformativos = Artigo.countArtigos(textosBusca);
            quantidadePaginas = getQuantidadePaginas(quantidadeInformativos, itensPorPagina);
        } else if (categoria.equalsIgnoreCase("comunicado")) {
            listaInformativos = Comunicado.getComunicados(textosBusca, primeiroResultado, limite);
            quantidadeInformativos = Comunicado.countComunicados(textosBusca);
            quantidadePaginas = getQuantidadePaginas(quantidadeInformativos, itensPorPagina);
        } else if (categoria.equalsIgnoreCase("noticia")) {
            listaInformativos = Noticia.getNoticias(textosBusca, primeiroResultado, limite);
            quantidadeInformativos = Noticia.countNoticias(textosBusca);
            quantidadePaginas = getQuantidadePaginas(quantidadeInformativos, itensPorPagina);
        } else if (categoria.equalsIgnoreCase("review")) {
            listaInformativos = Review.getReviews(textosBusca, primeiroResultado, limite);
            quantidadeInformativos = Review.countReviews(textosBusca);
            quantidadePaginas = getQuantidadePaginas(quantidadeInformativos, itensPorPagina);
        } else {
            listaInformativos = Informativo.getInformativos(textosBusca, primeiroResultado, limite);
            quantidadeInformativos = Informativo.countInformativos(textosBusca);
            quantidadePaginas = getQuantidadePaginas(quantidadeInformativos, itensPorPagina);
        }

        return geraXmlInformativos(listaInformativos, quantidadeInformativos, quantidadePaginas, null);
    }

    private long getQuantidadePaginas(long quantidade, long itensPorPagina) {
        return Paginacao.calculaPaginas(quantidade, itensPorPagina);
    }

    public String getDestaques(int limite, HttpServletRequest request) {
        return geraXmlInformativos(Informativo.getDestaques(limite), 0, 0, request);
    }

    private String geraXmlInformativos(List<Informativo> listaInformativos, long quantidade, long quantidadePaginas, HttpServletRequest request) {
        RssConverter converter = new RssConverter();

        Rss rss = RssFactory.getRss(TIPORSS.RSS_NOTICIA, quantidade, quantidadePaginas);
        for (Informativo informativo : listaInformativos) {
            ItemNoticia item = new ItemNoticia();
            item.setTitle(informativo.getTitulo());
            item.setDescription(informativo.getDescricao());
            item.setComments(informativo.getConteudo());
            item.setPubDate(DataConverter.converterData(informativo.getDataInformativo(), Data.PARARSS));
            item.setLink("noticia/"+informativo.getId());
            item.setGuid("noticia/"+informativo.getId());
            item.setNumero("" + informativo.getId());
            if (informativo.isDestaque()) {
                item.setImagem(ParametrosDoSistema.getCaminhoRelativoDestaque(request) + informativo.getId() + ".jpg");
            }
            rss.addItem(item);
        }

        return converter.toXml(rss);
    }

    public String getOcorrencias(int limite) {
        return geraXmlOcorrencias(Ocorrencia.getOcorrenciasMaisProximas(limite));
    }

    public String getOcorrencias(int limite, int mes, int ano) {
        return geraXmlOcorrencias(Ocorrencia.getOcorrencias(limite, mes, ano));
    }

    private String geraXmlOcorrencias(List<Ocorrencia> listaOcorrencias) {
        RssConverter converter = new RssConverter();
        Rss rss = RssFactory.getRss(TIPORSS.RSS_OCORRENCIA, 0, 0);
        for (Ocorrencia ocorrencia : listaOcorrencias) {
            ItemOcorrencia item = new ItemOcorrencia();
            item.setTitle(ocorrencia.getNome());
            item.setDescription(ocorrencia.getDescricao());
            String textoComentarios = "Comentários: ";
            for (Comentario comentario : ocorrencia.getComentarios()) {
                textoComentarios += "\n-->" + comentario.getConteudo() + "\n*Autor: " + comentario.getNome() + "\n*Hora: " + DataUtil.getHora(comentario.getHora()) + "";
            }
            item.setComments(textoComentarios);
            item.setPubDate(DataConverter.converterData(ocorrencia.getInicio(), Data.PARARSS));
            item.setLink("www.ciar.ufg.br");
            item.setGuid("www.ciar.ufg.br");
            item.setNumero("" + ocorrencia.getId());
            item.setInicio(DataConverter.converterData(ocorrencia.getInicio(), Data.DDMMAAAA));
            item.setFim(DataConverter.converterData(ocorrencia.getFim(), Data.DDMMAAAA));
            item.setClasse(getClasseOcorrencia(ocorrencia));
            rss.addItem(item);
        }
        return converter.toXml(rss);
    }

    private String getClasseOcorrencia(Ocorrencia ocorrencia) {
        if (ocorrencia instanceof Evento) {
            return "evento";
        } else {
            if (ocorrencia instanceof WebConferencia) {
                return "webconferencia";
            } else {
                if (ocorrencia instanceof EncontroPresencial) {
                    return "encontropresencial";
                } else {
                    if (ocorrencia instanceof Conferencia) {
                        return "conferencia";
                    } else {
                        return "ocorrencia";
                    }
                }
            }
        }
    }

    public String getComentarios(int idComentario, int limite) {
        List<Comentario> comentarios = Ocorrencia.getComentarios(idComentario, limite);

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
}
