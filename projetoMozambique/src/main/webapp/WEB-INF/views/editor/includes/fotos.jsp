<%-- 
    Document   : fotos
    Created on : 10/05/2011, 11:20:09
    Author     : Carlo e Lauro
--%>
<%@page import="java.util.Set"%>
<%@page import="br.ciar.domain.informativos.fotografia.Foto"%>
<%
            Integer idNoticia = (Integer) request.getAttribute("idNoticia");
            Integer idGaleria = (Integer) request.getAttribute("idGaleria");
            Set<Foto> fotos = (Set<Foto>) request.getAttribute("fotos");
            String caminhoRelativoThumbs = (String) request.getAttribute("caminhoRelativoThumbs");

            for (Foto foto : fotos) {
%>
<table style="width:100px">
    <tr><td><img name="" src="<%=caminhoRelativoThumbs%><%=foto.getId()%>.jpg" width="100" alt="" /></td></tr>
    <tr><td><center><a href="javascript:excluir(<%=foto.getId()%>)">Excluir</a></center></td></tr>
</table>
<%
            }
            if(fotos.size() <= 0){
                %>Não há fotos para esse informativo.<br /><a href="<%=request.getContextPath()%>/informativos/lista" >Voltar</a><%
            }
%>
<script>
    function excluir(id){
        var x = window.confirm("Tem certeza de que deseja excluir essa foto?");
        if (x){
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "<%=request.getContextPath()%>/noticia/<%=idNoticia%>/galeria/<%=idGaleria%>/fotos/"+id);
            var deleteHiddenField = document.createElement("input");
            deleteHiddenField.setAttribute("name", "_method");
            deleteHiddenField.setAttribute("value", "DELETE");
            deleteHiddenField.setAttribute("type", "hidden");
            form.appendChild(deleteHiddenField);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>