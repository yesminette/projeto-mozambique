<%@page import="br.ciar.infraestrutura.ParametrosDoSistema"%>
<%@page import="br.ciar.domain.informativos.fotografia.Foto"%>
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
            <p id="descricao_noticia" style="font-style: italic">
                <%=informativo.getDescricao()%>
            </p>
            <div id="box_imagem_noticia" style="margin: 35px 0px">
                <img alt=""  src="<%=caminhoImagem%>" id="imagem_noticia_<%=informativo.getId()%>" onerror="this.remove()" style="max-width: 720px" />
            </div>
            <%=(!informativo.getId().equals(CFG.ID_CLIPPING)) ? "<p>" : ""%>
            <%=informativo.getConteudo()%>
            <%=(!informativo.getId().equals(CFG.ID_CLIPPING)) ? "</p>" : ""%>

            <% if (informativo instanceof Noticia) {
                    Noticia noticia = (Noticia) informativo;
                    if (noticia.temGaleria()) {%>
            <div style="margin: 40px 0px 20px;">
                Fotos (clique para ampliar):
            </div>
            <div style="margin: 20px 30px">
                <%
                    for (Foto foto : noticia.getGaleria().getFotos()) {
                        String imagemFoto = ParametrosDoSistema.getCaminhoRelativoGaleria(request) + foto.getId() + ".jpg";
                        String thumbFoto = ParametrosDoSistema.getCaminhoRelativoThumbsGaleria(request) + foto.getId() + ".jpg";
                %>
                <a href="<%=imagemFoto%>" class="lytebox" data-lyte-options="group:fotosg<%=noticia.getGaleria().getId()%> doAnimations:false" data-description="<%=foto.getLegenda()%>" >
                    <img src="<%=thumbFoto%>" class="foto" title="Clique para ampliar" />
                </a>
                <%
                    }
                %>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</div>