package br.ciar.modelo.xml.rss;

public class ChannelNoticia extends Channel {

    private long quantidade;
    private long qtdpaginas;

    public long getQtdpaginas() {
        return qtdpaginas;
    }

    public void setQtdpaginas(long qtd_paginas) {
        this.qtdpaginas = qtd_paginas;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
}
