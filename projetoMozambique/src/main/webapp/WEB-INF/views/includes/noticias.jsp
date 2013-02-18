<%--
    Document   : noticias
    Created on : 12/04/2011, 18:14:47
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String caminhoImagensNoticias = (String) request.getAttribute("caminhoImagensNoticias");
%>
<div id="informativos">
    <a name="x"></a>
    <h1>Informativos</h1>
    <div id="box_noticias_todas">
        <select name="artigo" id="artigo" onchange="" style="width:125px;">
            <option value="todos" selected="selected">Tudo</option>
            <option value="noticia">Notícias</option>
            <option value="comunicado" >Comunicado</option>
            <option value="artigo" >Artigo</option>
            <option value="review" >Pós-Evento</option>
        </select>
        <input name="chave" type="text" id="chave" style="width:600px;" onclick="release_chave()" value="Pesquisa por palavras-chave"/>
        <br /><span id="quantidade"></span>
        <div class="noticias_frame" id="noticias_todas">

        </div><br /><strong>Páginas: </strong>
        <ul class="paginas" id="pages"></ul>
    </div>
    <script>
        var pagina_atual = 1;
        var paginas_a_listar = 13;
        var total_paginas = 1;
        getNoticias(6,1,null,null,null);

        jQuery(function() {
            jQuery('#box_noticias_todas input').keyup(function() {
                getNoticias(6,4,jQuery('input#chave').val(),jQuery("#artigo").val(),null);
            }); //close click(
        }); //close jQuery(

        jQuery(function() {
            jQuery('#artigo').change(function() {
                document.getElementById("chave").value = "Pesquisa por palavras-chave";
                getNoticias(6,4,null,jQuery(this).val(),null);
            }); //close click(
        }); //close jQuery(

        function mostrarPaginas(xml){
            var paginas = jQuery(xml).find('qtdpaginas').text();
            total_paginas = paginas;

            var html_paginas = "";
            var media_paginas_a_listar = parseInt(paginas_a_listar/2);

            // Verificar se não é uma das primeiras páginas. Se não for, então mostrar os botões de voltar páginas.
            if( !(pagina_atual <= (media_paginas_a_listar+1)) ){
                html_paginas += "<li class='paginas' onclick='mudarPagina(this)' title='Ir para primeira página' >&laquo;</li>";
                html_paginas += "<li class='paginas' onclick='mudarPagina(this)' title='Voltar 10 páginas' >&lt;</li>";
                html_paginas += "&nbsp;...";
            }

            // definir qual será a primeira página a ser mostrada na lista e qual será a última. A página atual tenderá a ser a do meio da lista.
            var indice_primeira_pagina = (pagina_atual - media_paginas_a_listar > 1) ? ( (pagina_atual > paginas-media_paginas_a_listar ) ? (paginas-(media_paginas_a_listar*2)) : (pagina_atual - media_paginas_a_listar) ) : 1;
            var indice_ultima_pagina = (indice_primeira_pagina + (media_paginas_a_listar*2)) < paginas ? (indice_primeira_pagina + (media_paginas_a_listar*2)) : paginas;

            // mostrar o conjunto de páginas, indo do indice da primeira página até o índice da última
            for(var indice=indice_primeira_pagina; indice <= indice_ultima_pagina; indice++){
                if(indice == pagina_atual){
                    html_paginas += "<li id='pagina_atual' class='paginas' onclick='mudarPagina(this)'>"+indice+"</li>";
                }else{
                    html_paginas += "<li class='paginas' onclick='mudarPagina(this)'>"+indice+"</li>";
                }
            }

            // Verificar se não é uma das últimas páginas. Se não for, então mostrar os botões de avançar páginas.
            if( !(pagina_atual > (paginas - (media_paginas_a_listar+1) )) ){
                html_paginas += "&nbsp;...";
                html_paginas += "<li class='paginas' onclick='mudarPagina(this)' title='Avançar 10 páginas' >&gt;</li>";
                html_paginas += "<li class='paginas' onclick='mudarPagina(this)' title='Ir para última página' >&raquo;</li>";
            }
        
            document.getElementById("pages").innerHTML = html_paginas;
        }

        function getNoticias(limite,tipo,chaveBusca,categoria,pagina){
            var url_get = "<%=request.getContextPath()%>/busca?";
            if(limite!=null) url_get += "&limite="+limite;
            if(tipo!=null) url_get += "&tipo="+tipo;
            if(chaveBusca!=null) url_get += "&chavebusca="+chaveBusca;
            if(categoria!=null) url_get += "&categoria="+categoria;
            if(pagina!=null) url_get += "&pagina="+pagina;
            url_get = url_get.replace('&', '');
            jQuery("#noticias_todas").html('<img src="<%=request.getContextPath()%>/resources/images/wait.gif" alt="Aguarde..." style="padding:70px 0px 0px 350px;" />');

            jQuery.ajax({
                type: "GET",
                cache:false,
                url: url_get,
                dataType: "xml",
                success: function(xml) {
                    document.getElementById("noticias_todas").innerHTML = "";
                    jQuery(xml).find('item').each(function(){
                        var titulo = jQuery(this).find('title').text()
                        var descricao = jQuery(this).find('description').text()
                        var numero = jQuery(this).find('numero').text()
                        var data = jQuery(this).find('pubDate').text()
                        jQuery('<div style=""></div>')
                        .html('<table><tr><td rowspan="2"><img title="'+data+'" class="img_chamada" src="<%=caminhoImagensNoticias%>'+numero+'.jpg" width="70" height="70" alt="" onerror="this.remove()"></td><td class="content"><a href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'"><span class="topico">'+titulo+'</span></a></td></tr><tr><td class="content"><div class="data">'+data+'</div>'+descricao+'<br /></td></tr></table>')
                        .appendTo('#noticias_todas');
                    }); //close each(
                    mostrarPaginas(xml);
                    setQuantidade(xml);
                }
            }); //close jQuery.ajax(
        }

        function mudarPagina(pagina){
            release_chave();
            var pagina_desejada = pagina.innerHTML;
            if(pagina_desejada == '«'){
                pagina_atual = 1;
            }else if(pagina_desejada == '&lt;'){
                pagina_atual = (parseInt(pagina_atual)-10) > 1 ? (parseInt(pagina_atual)-10) : 1;
            }else if(pagina_desejada == '»'){
                pagina_atual = total_paginas;
            }else if(pagina_desejada == '&gt;'){
                pagina_atual = (parseInt(pagina_atual)+10) > total_paginas ? total_paginas : (parseInt(pagina_atual)+10);
            }else{
                pagina_atual = pagina_desejada;
            }
            getNoticias(6,4,jQuery('input#chave').val(),jQuery("#artigo").val(),pagina_atual);
        }

        function setQuantidade(xml){
            var descritivo = "";
            var qtd = jQuery(xml).find('quantidade').text();
            if(qtd > 1 || qtd < 0){
                descritivo = "resultados obtidos";
            }else{
                descritivo="resultado obtido";
            }
            jQuery('#quantidade').text(qtd+" "+descritivo);
        }
    </script>
</div>