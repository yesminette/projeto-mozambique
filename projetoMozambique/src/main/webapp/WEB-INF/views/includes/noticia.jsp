<%@page import="br.ciar.domain.configuracoes.CFG"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>
<%@page import="br.ciar.utils.DataConverter.Data"%>
<%@page import="br.ciar.utils.DataConverter"%>
<%@page import="br.ciar.domain.informativos.Informativo"%>
<%
    Informativo informativo = (Informativo) request.getAttribute("informativo");

    String caminhoImagensNoticias = (String) request.getAttribute("caminhoImagensNoticias");
    String caminhoImagem = caminhoImagensNoticias + informativo.getId() + ".jpg";
%>
<style>
    .aba .conteudo{
        padding: 1em 1.4em;
    }
    p.ui-widget-header{
        margin: 3px;
        height: 18px;
        font-size: 13px;
        padding: 5px 10px;
    }
</style>
<div id="abas" class="aba ui-tabs-panel ui-widget-content ui-corner-bottom">
    <p class="ui-widget-header"><span>Informativos > Notícia</span></p>
    <div class="conteudo">
        <h1 id="titulo_noticia"><%=informativo.getTitulo()%></h1>
        <em><%=DataConverter.converterData(informativo.getDataInformativo(), Data.DDMMAAAAHHMM)%></em>
        <%
            if (informativo.getId().equals(CFG.ID_CLIPPING)) {
        %>
        <script>
            jQuery(function(){
                jQuery('#clipping').accordion();
            });
        </script>
        <%    }
        %>
        <div id="conteudo_noticia">
            <% if (informativo instanceof Noticia) {
                    Noticia noticia = (Noticia) informativo;
            %>
            <p id="descricao_noticia" style="font-style: italic">
                <%=informativo.getDescricao()%>
            </p>
            <div id="box_imagem_noticia" style="margin: 35px 0px">
                <img alt=""  src="<%=caminhoImagem%>" id="imagem_noticia_<%=informativo.getId()%>" onerror="this.remove()" />
            </div>
            <%  if (noticia.temGaleria()) {%>
            <a href="/resources/gallery.swf?numero=<%=noticia.getGaleria().getId()%>" rel="lightbox" title="<%=noticia.getTitulo()%>" TAG="SWF" width="700" height="600" ><img src="/resources/images/fotos_icon.gif" width="150px" alt="Galeria" /></a>
                <%}
                    }%>
                <%=(!informativo.getId().equals(CFG.ID_CLIPPING)) ? "<p>" : ""%>
                <%=informativo.getConteudo()%>
                <%=(!informativo.getId().equals(CFG.ID_CLIPPING)) ? "</p>" : ""%>

        </div>
    </div>
</div>