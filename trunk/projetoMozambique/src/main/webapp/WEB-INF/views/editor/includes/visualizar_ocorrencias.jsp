<%@page import="br.ciar.utils.DataConverter.Data"%>
<%@page import="br.ciar.utils.DataConverter"%>
<%@page import="br.ciar.domain.ocorrencias.Ocorrencia"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.web.context.support.SpringBeanAutowiringSupport"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%--
    Document   : visualizar_informativos
    Created on : 28/04/2011, 05:58:41
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%
    List<Ocorrencia> ocorrencias = (List<Ocorrencia>) request.getAttribute("ocorrencias");
%>
<div id="accordion">
    <%
                if (ocorrencias.size() > 0) {
                    for (Ocorrencia ocorrencia : ocorrencias) {
    %><h3><a href='#'><%=ocorrencia.getNome()%> <span class="data"><%=DataConverter.converterData(ocorrencia.getInicio(), Data.DDMMAAAA)%> - <%=DataConverter.converterData(ocorrencia.getFim(), Data.DDMMAAAA)%></span></a></h3>
    <div>
        <a href="<%=request.getContextPath()%>/ocorrencias/alterar/<%=ocorrencia.getId()%>">Editar</a> |
        <a href="javascript:excluir(<%=ocorrencia.getId()%>)">Excluir</a> |
        <a href="<%=request.getContextPath()%>/ocorrencias/<%=ocorrencia.getId()%>/comentarios">Comentarios</a>
    </div>
    <%
                        }
    %>
</div>
<%
            } else {
                out.print("Não há ocorrências cadastradas.");
            }
%>
<script>
    $(function() {
        $( "#accordion" ).accordion();
    });

    function excluir(id){
        var x = window.confirm("Tem certeza de que deseja excluir essa ocorrência?");
        if (x){
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "<%=request.getContextPath()%>/ocorrencias/"+id);
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