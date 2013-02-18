<%--
    Document   : visualizar_informativos
    Created on : 28/04/2011, 05:58:41
    Author     : Carlo Rafael Rodovalho Cesar
--%>

<%@page import="br.ciar.utils.DataConverter.Data"%>
<%@page import="br.ciar.utils.DataConverter"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>
<%@page import="br.ciar.domain.informativos.Comunicado"%>
<%@page import="br.ciar.domain.informativos.Artigo"%>
<%@page import="br.ciar.domain.informativos.Review"%>
<%@page import="br.ciar.domain.informativos.Informativo"%>
<%@page import="java.util.List"%>
<%
            List<Informativo> informativos = (List<Informativo>) request.getAttribute("informativos");
            if (informativos.size() > 0) {
%>

<div id="accordion">
    <%
                    for (Informativo informativo : informativos) {
    %><h3><a href='#'><%=informativo.getTitulo()%> - <%=DataConverter.converterData(informativo.getDataInformativo(), Data.DDMMAAAAHHMM)%></a></h3>
    <div>
        <div><%=informativo.getDescricao() != null ? informativo.getDescricao() : ""%></div><br />
        <a href="<%=request.getContextPath()%>/informativos/alterar/<%=informativo.getId()%>">Editar</a> |
        <a href="javascript:excluir(<%=informativo.getId()%>)">Excluir</a>
        <%if (informativo instanceof Noticia) {
            Noticia noticia = (Noticia) informativo;
        %>| <a href="<%=request.getContextPath()%>/noticia/<%=noticia.getId()%>/galeria/novo">Enviar Fotos</a>
        <%
            if (noticia.temGaleria()) {%>
        | <a href="<%=request.getContextPath()%>/noticia/<%=noticia.getId()%>/galeria/<%=noticia.getGaleria().getId()%>/fotos">Excluir Fotos</a>
        <%  }
                            }%>

    </div>
    <%
                    }
    %>
</div>
<%
            } else {
                out.print("Não há informativos cadastrados.");
            }
%>
<script>
    $(function() {
        $( "#accordion" ).accordion();
    });

    function excluir(id){
        var x = window.confirm("Tem certeza de que deseja excluir esse informativo?");
        if (x){
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "<%=request.getContextPath()%>/informativos/"+id);
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