<%@page import="java.util.List"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>


Equipe Pedagógica | Equipe de Produção de Material | Equipe de Suporte Tecnológico
Serviços Prestados | Solicitação de Serviço | Galeria

<%
            List<Noticia> noticias = (List<Noticia>) request.getAttribute("noticias");
            if (noticias.size() == 0) {
                %>Ainda não há galerias disponíveis.<%
            } else {
                %><ul class="listagem_agenda" id="galerias_panel"><%
                    for (Noticia noticia : noticias) {
                        if (noticia.getGaleria().getFotos() != null && noticia.getGaleria().getFotos().size() > 0) {
                            %><li><a href='<%=request.getContextPath()%>/resources/gallery.swf?numero=<%=noticia.getGaleria().getId()%>' rel='lightbox' title='<%=noticia.getTitulo()%>' TAG='SWF' width='700' height='600'><%=noticia.getTitulo()%></a></li><%
                        }
                    }
                %></ul><%
            }
            
%>
sojdioasjdiasd
<script>
</script>