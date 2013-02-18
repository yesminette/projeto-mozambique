<%--
    Document   : visualizar_informativos
    Created on : 28/04/2011, 05:58:41
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%
        Integer idNoticia = (Integer) request.getAttribute("idNoticia");
%>
<form action="<%=request.getContextPath()%>/noticia/<%=idNoticia.intValue()%>/galeria/novo" method="POST" enctype="multipart/form-data" >
    <h1>Galeria da Notícia</h1>
    <div id="box_galerias">
        <p>
            <input type="file" id="foto0" name="foto0" /><br /><br />
            <input type="text" id="legenda0" name="legenda0" size="50" title="Legenda da foto" /><br />
        </p>
    </div>
    <center><input type="button" id="mais_galeria" value="+" title="Adicionar mais campos" /></center><br />
    <br /><input type="submit" />
</form>

<script>
    var ct = 1;
    $('#mais_galeria').click(function() {
        $('<p></p>')
        .html('<br /><hr /><br /><input type="file" id="foto'+ct+'" name="foto'+ct+'" /><br /><br />'+
            '<input type="text" id="legenda'+ct+'" name="legenda'+ct+'" size="50" /><br />')
        .appendTo('#box_galerias');
        ct++;
    });
</script>