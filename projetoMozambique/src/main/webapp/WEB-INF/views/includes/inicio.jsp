<%@page import="br.ciar.domain.configuracoes.CFG"%>
<div id="bloco_destaque">
    <div id="destaque">
        <!-- SlideShow -->
        <ul id="slideshow">
        </ul>
        <!-- SlideShow -->
    </div>
</div>
<table id="main_layout">
    <tr>
        <td>            
            <div id="box_noticias" style="filter:glow(strength=5, color=#000000,enabled=1); border-style: solid;border-width: 1px; border-color: #ccc;" >
                <span id="titulo">Informativos</span>
                <input name="chave" type="text" id="chave" style="width:200px;margin-left:10px" placeholder="Busca"/>
                <span class="vermais">
                    <a href="<%=request.getContextPath()%>/informativos/noticias#x" style="margin-left: 20px;">ver todos</a>
                </span>
                <div class="componente_noticias_index" id="noticias">

                </div>
            </div>
        </td>
        <td>
            <div id="eventos" class="box"><span class="topico_box">Agenda de Eventos <a href="<%=request.getContextPath()%>/ocorrencias"><span class="vermais" style="margin-left:30px; "> ver todos</a></span></span>
                <ul id="ocorrencias_panel">

                </ul>
            </div>
        </td>
    </tr>
</table>
<script>
    
    var d = new Date();
    
    // SLIDESHOW - Destaques
    var z = 1;
    var width = 495;
    var height = 310;
    jQuery.ajax({
        type: "GET",
        cache: false,
        url: "busca?data="+d.getDate()+"&tipo=5&limite=4",
        dataType: "xml",
        success: function(xml) {
            if(jQuery(xml).find('item').length > 0){ // Caso tenha pelo menos 1 destaque
                jQuery(xml).find('item').each(function(){
                    var titulo = jQuery(this).find('title').text();
                    var descricao = jQuery(this).find('description').text();
                    var numero = jQuery(this).find('numero').text();
                    var data = jQuery(this).find('pubDate').text();
                    var imagem = jQuery(this).find('imagem').text();
                    if(numero != <%=CFG.ID_CLIPPING%>){
                        jQuery('#slideshow')
                        .append('<li id="slide'+z+'">'
                            +'<a href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'" title="'+titulo+'" style="display: none">'
                            +'<img width="'+width+'" height="'+height+'" title="'+titulo+'" src="'+imagem+'" alt="'+titulo+'">'
                            +'</a>'
                            +'<div class="slideshow-caption" style="display: none;">'
                            +'<a href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'">'
                            +'<p><strong>'+titulo+'</strong></p>'
                            +'<p class="data">'+data+'</p><br />'
                            +'<p>'+descricao+'. Leia mais »</p>'
                            +'</a>'
                            +'</div>'
                            +'</li>');
                    }
                    z = z+1;
                }); //close each(
                jQuery('document').ready(function () {
                    jQuery('#slideshow').slideshow();
                });
                jQuery('#slideshow-buttons').css('width','');
            }else{
                // Tratar caso não haja destaques
                jQuery('#slideshow')
                .append('<div style="text-align: center; padding-top: 120px;"><p>Não há destaques.</p></div>');
            }
        }
    }); //close jQuery.ajax(
    // FIM SLIDESHOW - Destaques
    
    
    
    var tamanho_descricao = 130;
    var tamanho_titulo = 60;
    var limite_noticias = 4;
    
    jQuery.ajax({
        type: "GET",
        cache: false,
        url: "busca?data="+d.getDate()+"&tipo=1&limite="+limite_noticias,
        dataType: "xml",
        success: function(xml) {
            jQuery(xml).find('item').each(function(){
                var titulo = jQuery(this).find('title').text()
                var descricao = jQuery(this).find('description').text()
                var numero = jQuery(this).find('numero').text()
                var data = jQuery(this).find('pubDate').text()
                if(numero != <%=CFG.ID_CLIPPING%>){
                    jQuery('<div></div>')
                    .html('<a href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'" title="'+titulo+'"><span class="topico">'+reduzirParaCaber(titulo,tamanho_titulo)+'</span></a></td></tr><tr><td><a class="descricao" href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'"><div id="data" class="data">'+data+'</div>'+reduzirParaCaber(descricao,tamanho_descricao)+'</a><br />')
                    .appendTo('#noticias');
                }
            }); //close each(
        }
    }); //close jQuery.ajax(




    jQuery(function() {
        jQuery('#chave').keyup(function() {
            jQuery.ajax({
                type: "GET",
                cache:false,
                url: "busca?data="+d.getDate()+"&tipo=2&limite="+limite_noticias+"&chavebusca="+jQuery('#chave').val()+"",
                dataType: "xml",
                success: function(xml) {
                    document.getElementById("noticias").innerHTML = "";
                    jQuery(xml).find('item').each(function(){
                        var titulo = jQuery(this).find('title').text()
                        var descricao = jQuery(this).find('description').text()
                        var numero = jQuery(this).find('numero').text()
                        var data = jQuery(this).find('pubDate').text()
                        jQuery('<div class="noticia"></div>')
                        .html('<a href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'" title="'+titulo+'"><span class="topico">'+reduzirParaCaber(titulo,tamanho_titulo)+'</span></a></td></tr><tr><td><a class="descricao" href="<%=request.getContextPath()%>/informativos/noticia/'+numero+'"><div id="data" class="data">'+data+'</div>'+reduzirParaCaber(descricao,tamanho_descricao)+'</a><br />')
                        .appendTo('#noticias');
                    }); //close each(
                }
            }); //close jQuery.ajax(
        }); //close click(
    }); //close jQuery(

    var limite = 3;
    getOcorrencias(6,limite,null,null);
    function getOcorrencias(tipo,limite,ano,mes){
        var url_get = "busca?";
        if(limite!=null) url_get += "&limite="+limite;
        if(tipo!=null) url_get += "&tipo="+tipo;
        if(ano!=null) url_get += "&ano="+ano;
        if(mes!=null) url_get += "&mes="+mes;
        url_get = url_get.replace('&', '');
        jQuery("#ocorrencias_panel").html('<img src="<%=request.getContextPath()%>/resources/images/wait.gif" alt="Aguarde..." style="padding:70px 0px 0px 350px;" />');

        jQuery.ajax({
            type: "GET",
            cache:false,
            url: url_get,
            dataType: "xml",
            success: function(xml) {
                jQuery("#ocorrencias_panel").html('');
                var count = 0;
                jQuery(xml).find('item').each(function(){
                    var titulo = jQuery(this).find('title').text()
                    var descricao = jQuery(this).find('description').text()
                    var numero = jQuery(this).find('numero').text()
                    var data = jQuery(this).find('pubDate').text()
                    var inicio = jQuery(this).find('inicio').text()
                    var fim = jQuery(this).find('fim').text()
                    
                    var periodo = (inicio == fim) ? inicio : inicio+' - '+fim;
                    
                    jQuery('<li></li>')
                    .html('<span class="data">'+periodo+'</span> - <a href="<%=request.getContextPath()%>/ocorrencias/'+numero+'" title="Ocorrencia">'+titulo+'</a>')
                    .appendTo('#ocorrencias_panel');
                    count++;
                }); //close each(
                if(count==0){
                    jQuery('<li></li>')
                    .html('Nenhuma Ocorrência encontrada no período selecionado.')
                    .appendTo('#ocorrencias_panel');
                }
            }
        }); //close jQuery.ajax(
    }

</script>
