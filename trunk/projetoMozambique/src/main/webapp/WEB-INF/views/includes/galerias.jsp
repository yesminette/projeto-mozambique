<%@page import="br.ciar.domain.configuracoes.CFG"%>
<%@page import="java.util.List"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>


Equipe Pedag�gica | Equipe de Produ��o de Material | Equipe de Suporte Tecnol�gico
Servi�os Prestados | Solicita��o de Servi�o | Galeria

<%
            List<Noticia> noticias = (List<Noticia>) request.getAttribute("noticias");
            if (noticias.size() == 0) {
                %>Ainda n�o h� galerias dispon�veis.<%
            } else {
                %><ul class="listagem_agenda" id="galerias_panel"><%
                    for (Noticia noticia : noticias) {
                        if (noticia.getGaleria().getFotos() != null && noticia.getGaleria().getFotos().size() > 0) {
                            %><li><a href='<%=CFG.contextPath%>/resources/gallery.swf?numero=<%=noticia.getGaleria().getId()%>' rel='lightbox' title='<%=noticia.getTitulo()%>' TAG='SWF' width='700' height='600'><%=noticia.getTitulo()%></a></li><%
                        }
                    }
                %></ul><%
            }
            
%>
sojdioasjdiasd
<script>
</script>