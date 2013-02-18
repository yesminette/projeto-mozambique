<%-- 
    Document   : ocorrencia
    Created on : 28/03/2011, 21:04:50
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%@page import="java.net.URLEncoder"%>
<%@page import="br.ciar.domain.ocorrencias.Comentario"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>
<%@page import="br.ciar.domain.ocorrencias.WebConferencia"%>
<%@page import="br.ciar.utils.DataUtil"%>
<%@page import="br.ciar.domain.ocorrencias.Ocorrencia"%>
<%
            Ocorrencia ocorrencia = (Ocorrencia) request.getAttribute("ocorrencia");
%>
<h1><%=ocorrencia.getNome()%></h1>

<div id="descricao_ocorrencia">
    <p>
        <%=ocorrencia.getDescricao()%>
    </p>

</div>
    

<%
            String dataInicio = DataUtil.getData(ocorrencia.getInicio());
            String dataFim = DataUtil.getData(ocorrencia.getFim());
%>
<div id="detalhes_ocorrencia">

    <ul class="listagem_agenda">
        <li><strong>Data:</strong> <%= dataInicio%> <%=(dataInicio.equals(dataFim)) ? ("") : (" a " + dataFim)%></li>
        <li><strong>Local:</strong> <%= ocorrencia.getLocalOcorrencia()%></li>
    </ul>
    <%
                if (ocorrencia.getMapa() != null && !ocorrencia.getMapa().isEmpty()) {
                    %>
                    <iframe width="380" height="220" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="<%=ocorrencia.getMapa()%>&output=embed"></iframe>
                    <br />
                    <small>
                        <a href="https://maps.google.com.br/maps?q=Centro+de+Cultura+e+Eventos+Prof%C2%BA+Ricardo+Freua+Buf%C3%A1i%C3%A7al&amp;ie=UTF8&amp;hq=Centro+de+Cultura+e+Eventos+Prof%C2%BA+Ricardo+Freua+Buf%C3%A1i%C3%A7al&amp;hnear=Goi%C3%A2nia+-+Goi%C3%A1s&amp;t=m&amp;ll=-16.603952,-49.258919&amp;spn=0.072382,0.130119&amp;z=12&amp;iwloc=A&amp;source=embed" style="color:#0000FF;text-align:center">Exibir mapa ampliado</a>
                    </small>
                    <%
                }
    %>

    <center>
        <br />
        <%
                    if (ocorrencia instanceof WebConferencia) {
                        WebConferencia webconf = (WebConferencia) ocorrencia;
                        if (webconf.isAcessivel()) {
                            out.println("<a href='" + webconf.getUrlAcesso() + "' target='_blank' >Acesso à sala de Webconferência</a>");
                        }
                        if (webconf.isGravacaoAcessivel()) {
                            out.println("| <a href='" + webconf.getUrlGravacao() + "' target='_blank' >Acesso à gravação da Webconferência</a>");
                        }
                        out.println("<br />");
                    }

                    Noticia noticia = ocorrencia.getReview();
                    if (noticia != null) {
                        if (noticia.temGaleria()) {
                            out.println("<a href='"+request.getContextPath()+"/resources/gallery.swf?numero=" + noticia.getGaleria().getId() + "' rel='lightbox' title='" + ocorrencia.getNome() + "' TAG='SWF' width='700' height='600'>Fotos do Evento | ");
                        } else {
                            out.println("Ainda não há fotos desse Evento.");
                        }
        %><a href='<%=request.getContextPath()%>/informativos/noticia/<%=noticia.getId()%>' >Pós-Evento</a><%
                            } else {
                                out.println("Não há fotos nem Notícias(Reviews) relacionadas a esse evento.");
                            }
        %>
    </center>
</div>

<div id="comentarios_ocorrencia">
    <h2>Comentários</h2>

    <a href="javascript:mostrar_novo_comentario()">Inserir Comentário</a><br />
    <div id="box_novo_comentario" style="display:none">
        <form name="fomulario" id="formulario" method="post">

            <label>Comentário <br />
                <textarea id="novo_comentario" name="novo_comentario" cols="41" rows="4"></textarea></label><br />
            <label>Seu Nome <input id="nome" type="text" name="nome"  size="34" /> </label>
            <input type='hidden' name='numero' value='<%=ocorrencia.getId()%>' />
            <input type='hidden' name='limite' value='6' />
            <input type="button" onclick='comentar()' value="Enviar" />
        </form>

    </div>
    <div id="comentarios">
        <%
                    int limite = 6;
                    int ct_comentarios = 0;
                    try {
                        for (int index = 0; index < limite; index++) {
                            Comentario comentario = ocorrencia.getComentarios().get(index);
                            ct_comentarios++;
        %><blockquote class="comentario_ocorrencia">
            <span><%=comentario.getConteudo()%></span><br />
            <em>por <%=comentario.getNome()%> - <%= DataUtil.calculaTempoDecorrido(comentario.getHora())%></em>
        </blockquote>
        <%
                        }
                    } catch (Exception e) {
                        if (ct_comentarios == 0) {
                            out.println("<em>Ainda não há comentários para esse evento.</em>");
                        }
                    }
        %>
    </div>
</div>

<script>
    function mostrar_novo_comentario(){
        jQuery('#box_novo_comentario').toggle('fast');
    }

    function comentar(){
        var params = jQuery('form').serialize();
        var url_post = "<%=request.getContextPath()%>/ocorrencias/<%=ocorrencia.getId()%>/comentar";
        jQuery.ajax({
            type: "POST",
            cache:false,
            url: url_post,
            dataType: "xml",
            data:params,
            success: function(xml) {
                limpar_campos();
                mostrar_novo_comentario();
                jQuery("#comentarios").html('');
                jQuery(xml).find('comentario').each(function(){;
                    var conteudo = jQuery(this).find('conteudo').text();
                    var nome = jQuery(this).find('nome').text();
                    var tempo = jQuery(this).find('tempo').text();
                    jQuery('<blockquote class="comentario_ocorrencia"></blockquote>')
                    .html('<span>'+conteudo+'</span><br /><em>por '+nome+' - '+tempo+'</em>')
                    .appendTo('#comentarios');
                }); //close each(\n");
            }
        }); //close jQuery.ajax(
    };
    function limpar_campos(){
        jQuery("#nome").val("");
        jQuery("#novo_comentario").val("");
    }
</script>
