/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ciar.rss;

import br.ciar.modelo.xml.rss.Channel;
import br.ciar.modelo.xml.rss.ChannelNoticia;
import br.ciar.modelo.xml.rss.ChannelOcorrencia;
import br.ciar.modelo.xml.rss.Rss;
import br.ciar.modelo.xml.rss.Version;
import java.util.ArrayList;

/**
 *
 * @author Lauro
 */
public class RssFactory {

    public static Rss getRss(TIPORSS tipo_rss) {
        return getRss(tipo_rss, 0, 0);
    }

    public static Rss getRss(TIPORSS tipo_rss, long quantidadeTotal, long qtdadePaginas) {
        switch (tipo_rss) {
            case RSS_NOTICIA :
                return getRssNoticia(quantidadeTotal, qtdadePaginas);
            case RSS_OCORRENCIA:
                return getRssOcorrencia();
            default:
                return new Rss();
        }
    }

    private static Rss getRssNoticia(long quantidadeTotal, long qtdadePaginas) {
        Rss rss = new Rss();
        rss.setVersion(new Version("2.0"));
        ChannelNoticia channel = new ChannelNoticia();
        channel.setTitle("CIAR - RSS");
        channel.setLink("http://www.ciar.ufg.br/informativos/");
        channel.setDescription("Notícias CIAR");
        channel.setLanguage("pt-br");
        channel.setCopyright("Copyright 2000-2011, CIAR");
        channel.setWebMaster("Lauro Ramon e Carlo Rafael");
        channel.setQtdpaginas(qtdadePaginas);
        channel.setQuantidade(quantidadeTotal);
        ArrayList items = new ArrayList();
        channel.setItems(items);
        rss.setChannel(channel);

        return rss;
    }

    private static Rss getRssOcorrencia() {
        Rss rss = new Rss();
        rss.setVersion(new Version("2.0"));
        ChannelOcorrencia channel = new ChannelOcorrencia();
        channel.setTitle("CIAR - RSS");
        channel.setLink("http://www.ciar.ufg.br/ultimas/rss.html");
        channel.setDescription("Notícias CIAR");
        channel.setLanguage("pt-br");
        channel.setCopyright("Copyright 2000-2011, CIAR");
        channel.setWebMaster("Lauro Ramon e Carlo Rafael");

        ArrayList items = new ArrayList();
        channel.setItems(items);
        rss.setChannel(channel);

        return rss;
    }
}
