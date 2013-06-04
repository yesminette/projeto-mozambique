<%@page import="br.ciar.domain.configuracoes.CFG"%>
<%@page import="br.ciar.utils.DataUtil"%>
<%@page import="br.ciar.domain.ocorrencias.Comentario"%>
<%@page import="br.ciar.domain.ocorrencias.Ocorrencia"%>
<%
            Ocorrencia ocorrencia = (Ocorrencia) request.getAttribute("ocorrencia");
%>
<h1>Comentários para <%=ocorrencia.getNome()%></h1>

<div id="comentarios">
    <%
                int ct_comentarios = 0;
                for (Comentario comentario : ocorrencia.getComentarios()) {

                    ct_comentarios++;
                        %><blockquote class="comentario_ocorrencia">
                            <span><%=comentario.getConteudo()%></span><br />
                            <em>por <%=comentario.getNome()%> - <%= DataUtil.calculaTempoDecorrido(comentario.getHora())%></em>
                            <br /><a href="javascript:excluir(<%=comentario.getId()%>,<%=ocorrencia.getId()%>)">Excluir</a>
                        </blockquote>
                        <%
                }
                if (ct_comentarios == 0) {
                    out.println("<em>Ainda não há comentários para essa Ocorrência.</em>");
                }
    %>
</div>
<script>
    function excluir(idComentario, idOcorrencia){
        var x = window.confirm("Tem certeza que deseja excluir esse comentário?");
        if (x){
            var form = document.createElement("form");
            form.setAttribute("method", "POST");
            form.setAttribute("action", "<%=CFG.contextPath%>/ocorrencias/"+idOcorrencia+"/comentarios/"+idComentario);
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