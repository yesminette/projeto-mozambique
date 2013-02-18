<%-- 
    Document   : ocorrencias
    Created on : 28/03/2011, 13:08:16
    Author     : Carlo Rafael Rodovaho Cesar
--%>

<%@page import="java.util.Calendar"%>
<h1>Agenda de Eventos</h1>
<select name="ano" id="ano" style="width:80px;">
    <%
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        for (int ano = anoAtual; ano >= 2012; ano--) {
            %><option value="<%=ano%>" <%=((ano == anoAtual) ? "selected='selected'" : "")%>><%=ano%></option><%
        }
    %>
</select>

<select name="mes" id="mes" style="width:155px;">
    <option value="0" selected="selected">-------------Mes-------------</option>
    <option value="1">Janeiro</option>
    <option value="2">Fevereiro</option>
    <option value="3">Março</option>
    <option value="4">Abril</option>
    <option value="5">Maio</option>
    <option value="6">Junho</option>
    <option value="7">Julho</option>
    <option value="8">Agosto</option>
    <option value="9">Setembro</option>
    <option value="10">Outubro</option>
    <option value="11">Novembro</option>
    <option value="12">Dezembro</option>
</select>

<span id="quantidade"></span>
<ul class="listagem_agenda" id="ocorrencias_panel">
    Carregando...
</ul>


<script>
    
    var limite = 25;
    var ano = document.getElementById("ano");
    var mes = document.getElementById("mes")
    getOcorrencias(6,limite,null,null);

    jQuery(function() {
        jQuery('#mes').change(function() {
            getOcorrencias(7,limite,jQuery("#ano").val(),jQuery("#mes").val());
        }); //close click(
    }); //close jQuery

    jQuery(function() {
        jQuery('#ano').change(function() {
            if(jQuery('#mes').val() == 0){
                if(jQuery('#ano').val() != 2011){
                    document.getElementById("mes").selectedIndex = 12;
                }else{
                    getOcorrencias(6);
                    return;
                }
            }
            getOcorrencias(7,limite,jQuery("#ano").val(),jQuery("#mes").val());
        }); //close click(
    }); //close jQuery(





    function getOcorrencias(tipo,limite,ano,mes){
        var url_get = "<%=request.getContextPath()%>/busca?";
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