<%@page import="java.util.Calendar"%>
<%@page import="br.ciar.domain.informativos.Noticia"%>
<%@page import="br.ciar.domain.informativos.Comunicado"%>
<%@page import="br.ciar.domain.informativos.Artigo"%>
<%@page import="br.ciar.domain.informativos.Review"%>
<%@page import="br.ciar.domain.informativos.Informativo"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--
    Document   : visualizar_informativos
    Created on : 28/04/2011, 05:58:41
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%
            Informativo informativo = (request.getAttribute("informativo") != null) ? (Informativo) request.getAttribute("informativo") : new Informativo();
            int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
%>
<form id="form" enctype="multipart/form-data" method="post">
    <h1>Informativo</h1>

    <% if (informativo.getId() == null) {%>
    <div>
        <h2>Tipo</h2>
        <label><input type="radio" name="tipo" id="tipo" value="noticia" onchange="mostrarOcorrencias(this.value)" />Notícia</label>
        <label><input type="radio" name="tipo" id="tipo" value="review" onchange="mostrarOcorrencias(this.value)" />Pós-Evento</label>
        <label><input type="radio" name="tipo" id="tipo" value="artigo" onchange="mostrarOcorrencias(this.value)" />Artigo</label>
        <label><input type="radio" name="tipo" id="tipo" value="comunicado" onchange="mostrarOcorrencias(this.value)" />Comunicado</label>
        <label for="tipo" style="display:none" class="error">Você precisa escolher um tipo para o Informativo!</label>
    </div>
    <% } else {
         String tipo = "informativo";
         if (informativo instanceof Review) {
             tipo = "review";
         } else {
             if (informativo instanceof Artigo) {
                 tipo = "artigo";
             } else {
                 if (informativo instanceof Comunicado) {
                     tipo = "comunicado";
                 } else {
                     if (informativo instanceof Noticia) {
                         tipo = "noticia";
                     }
                 }
             }
         }
    %>
    <input type="hidden" name="_method" value="PUT" />
    <input type='hidden' value='<%=tipo%>' id='tipo' name='tipo' />
    <input type="hidden" name="id" id="id" value="<%=informativo.getId()%>" />
    <%
                }
    %>

    <div id="bloco_ocorrencia" style="display: none">
        <h2>Busque a ocorrência</h2>
        <select name="ano" id="ano" style="width:80px;">
            <%
                for(int ano=anoAtual; ano >=2012; ano--){
                    %><option value="<%=ano%>" <%=((ano==anoAtual)?"selected='selected'":"")%>><%=ano%></option><%
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

        <div class="listagem_agenda" id="ocorrencias_panel">
            Carregando...
        </div>
    </div>

    <div>
        <br />
        <h2>Esse informativo é um destaque?</h2>
        <label><input type="checkbox" id="isdestaque" name="isdestaque" <%=informativo.isDestaque() ? "checked='checked'" : ""%> onchange="mostrarImagemDestaque()" />Destaque</label>
        <br /><br /><em>dica: Informativos marcados como destaque aparecem no banner da página inicial.</em>

        <div id="box_imagem_destaque" style="display: none">
            <h2>Imagem de destaque</h2>
            <input type="file" id="imagem_destaque" name="imagem_destaque" size="70" />
            <br /><em>dica: É obrigatória a inclusão de um arquivo de imagem com resolução 495x310 com no mínimo 72 pontos por polegada e bno</em>
        </div>
    </div>

    <div>
        <h2>Imagem para o Informativo (opcional)</h2>
        <input type="file" id="imagem_informativo" name="imagem_informativo" size="70" />
    </div>

    <div>
        <h2>Título</h2>
        <input id="titulo" name="titulo" maxlength="250" value="<%=informativo.getTitulo() == null ? "" : informativo.getTitulo()%>" size="50" /><span id="dica_titulo">250</span>

        <h2>Descrição</h2>
        <input id="descricao" name="descricao" value="<%=informativo.getDescricao() == null ? "" : informativo.getDescricao()%>" size="78" /><span id="dica_descricao"></span>

        <h2>Conteúdo</h2>
        <textarea id="conteudo" name="conteudo" rows="30" cols="80"><%=informativo.getConteudo() == null ? "" : informativo.getConteudo()%></textarea>
        <div id="dica_conteudo"></div>
    </div>
        
    <input type="button" value="Enviar" onclick="enviar()"/>
    
</form>

<script>

    var limite = 25;
    var ano = document.getElementById("ano");
    var mes = document.getElementById("mes")
    getOcorrencias(6,limite,null,null);

    jQuery(function() {
        jQuery('#mes').change(function() {
            getOcorrencias(7,limite,jQuery("#ano").val(),jQuery("#mes").val());
        }); //close click(
    <%
                if (informativo.isDestaque()) {
    %>mostrarImagemDestaque();<%                            }
    %>
        }); //close jQuery(

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
            var url_get = "../busca?";
            if(limite!=null) url_get += "&limite="+limite;
            if(tipo!=null) url_get += "&tipo="+tipo;
            if(ano!=null) url_get += "&ano="+ano;
            if(mes!=null) url_get += "&mes="+mes;
            url_get = url_get.replace('&', '');
            jQuery("#ocorrencias_panel").html('<img src="../images/wait.gif" alt="Aguarde..." style="padding:70px 0px 0px 350px;" />');

            jQuery.ajax({
                type: "GET",
                cache:false,
                url: url_get,
                dataType: "xml",
                success: function(xml) {
                    jQuery("#ocorrencias_panel").html('');
                    jQuery(xml).find('item').each(function(){
                        var titulo = jQuery(this).find('title').text()
                        var descricao = jQuery(this).find('description').text()
                        var numero = jQuery(this).find('numero').text()
                        var data = jQuery(this).find('pubDate').text()
                        var inicio = jQuery(this).find('inicio').text()
                        var fim = jQuery(this).find('fim').text()


                        jQuery('<div></div>')
                        .html('<label><input type="radio" name="id_ocorrencia" id="id_ocorrencia" value="'+numero+'" /> <span class="data">'+inicio+' - '+fim+'</span> - '+titulo+'</label>')
                        .appendTo('#ocorrencias_panel');
                    }); //close each(

                }
            }); //close jQuery.ajax(
        }

        function mostrarOcorrencias(tipo_informativo){
            if(tipo_informativo == 'review'){
                $('#bloco_ocorrencia').show('fast');
            }else{
                $('#bloco_ocorrencia').hide('fast');
            }
        }

        function mostrarImagemDestaque(){
            if (jQuery('#isdestaque:checked').val() != undefined){
                $('#box_imagem_destaque').show('fast');
            }else{
                $('#box_imagem_destaque').hide('fast');
            }
        }

        function enviar(){
            <%if(informativo.getId() == null){%>
                var tipoInformativo = $('input[name=tipo]:checked').val();
            <%}else{%>
                var tipoInformativo = $('input[name=tipo]').val();
            <%}%>
            if(tipoInformativo == 'noticia'){
                $('#form').attr("action", '<%=request.getContextPath()%>/noticia');
            }
            if(tipoInformativo == 'review'){
                $('#form').attr("action", '<%=request.getContextPath()%>/review');
            }
            if(tipoInformativo == 'artigo'){
                $('#form').attr("action", '<%=request.getContextPath()%>/artigo');
            }
            if(tipoInformativo == 'comunicado'){
                $('#form').attr("action", '<%=request.getContextPath()%>/comunicado');
            }
            $('#form').submit();
        }

        //Validacao----------


        $(document).ready( function() {
            $("#form").validate({
                // Define as regras
                rules:{
                    tipo:{
                        required: true
                    },
                    titulo:{
                        required: true, minlength: 2
                    },
                    descricao:{
                        required: true, minlength: 2
                    //},
                    //conteudo:{
                    //    required: true, minlength: 30, maxlength: 200
                    }
                },
                // Define as mensagens de erro para cada regra
                messages:{
                    titulo:{
                        required: "O informativo precisa ter um nome!",
                        minlength: "Só isso? Não se parece com um nome de Infomativo!"
                    },
                    descricao:{
                        required: "Ajude o usuário a saber do que se trata o Informativo!",
                        minlength: "Só isso? É provável que esse campo não tenha sido preenchido corretamente!"
                    //},
                    //conteudo:{
                    //    required: "Seja claro com o usuário! Esse é o campo onde vai o conteúdo!",
                    //    minlength: "Só isso? Com certeza há mais a se falar sobre esse assunto!",
                    //    maxlength: "Calma lá! Não exagere, o excesso de conteúdo pode comprometer a qualidade da página!"
                    }
                }
            });
        });

        //----------------------
</script>