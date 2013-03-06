<%@page import="br.ciar.domain.ocorrencias.Conferencia"%>
<%@page import="br.ciar.domain.ocorrencias.EncontroPresencial"%>
<%@page import="br.ciar.domain.ocorrencias.WebConferencia"%>
<%@page import="br.ciar.domain.ocorrencias.Evento"%>
<%@page import="br.ciar.domain.ocorrencias.Ocorrencia"%>
<%@page import="br.ciar.utils.DataConverter.Data"%>
<%@page import="br.ciar.utils.DataConverter"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
    Document   : visualizar_informativos
    Created on : 28/04/2011, 05:58:41
    Author     : Carlo Rafael Rodovalho Cesar
--%>
<%
    Ocorrencia ocorrencia = (request.getAttribute("ocorrencia") != null) ? (Ocorrencia) request.getAttribute("ocorrencia") : new Ocorrencia();
%>
<form id="form" name="form" method="POST">
    <h1>Ocorr�ncia</h1>

    <% if (ocorrencia.getId() == null) {%>
    <div>
        <h2>Tipo</h2>
        <label><input type="radio" name="tipo" id="tipo" value="evento" onchange="mostrarCliente(this.value)" />Evento</label>
        <label><input type="radio" name="tipo" id="tipo" value="web_conferencia" onchange="mostrarCliente(this.value)" />Webconfer�ncia</label>
        <label><input type="radio" name="tipo" id="tipo" value="encontro_presencial" onchange="mostrarCliente(this.value)" />Encontro Presencial</label>
        <label for="tipo" style="display:none" class="error">Voc� precisa escolher um tipo para a ocorr�ncia!</label>
    </div><br />
    <% } else {
        String tipo = "ocorrencia";
        if (ocorrencia instanceof Evento) {
            tipo = "evento";
        } else {
            if (ocorrencia instanceof WebConferencia) {
                tipo = "web_conferencia";
            } else {
                if (ocorrencia instanceof EncontroPresencial) {
                    tipo = "encontro_presencial";
                }
            }
        }
    %>
    <input type="hidden" name="_method" value="PUT" />
    <input type="hidden" name="tipo" id="tipo" value="<%=tipo%>" />
    <input type="hidden" name="id" id="id" value="<%=ocorrencia.getId()%>" />
    <%
        }
    %>

    <div id="box_cliente" style="display: none">
        <%
            Conferencia conf = new Conferencia();
            if (ocorrencia instanceof Conferencia) {
                conf = (Conferencia) ocorrencia;
            }
        %>
        <label>Cliente <input size="68" type="text" name="cliente" id="cliente" value="<%=conf.getCliente() == null ? "" : conf.getCliente()%>" /></label>
    </div>
    <div id="box_url" style="display: none">
        <%
            WebConferencia webConf = new WebConferencia();
            if (ocorrencia instanceof WebConferencia) {
                webConf = (WebConferencia) ocorrencia;
            }
        %>
        <sec:authorize ifAnyGranted="SITE_ADMIN,SITE_WEBCONF" >
            <label>URL Acesso <input size="64" type="text" name="url_acesso" id="url_acesso" value="<%=webConf.getUrlAcesso() == null ? "" : webConf.getUrlAcesso()%>" /></label><br />
            <label>URL Grava��o <input size="62" type="text" name="url_gravacao" id="url_gravacao" value="<%=webConf.getUrlGravacao() == null ? "" : webConf.getUrlGravacao()%>" /></label>
            </sec:authorize>
    </div>


    <h2>Nome da Ocorr�ncia</h2>
    <input type="text" name="nome" id="nome" value="<%=ocorrencia.getNome() == null ? "" : ocorrencia.getNome()%>" maxlength="250" size="75" /><span id="dica_titulo">250</span>
    <br />

    <div>
        <h2>In�cio/Fim</h2>
        <span>
            De <input value="<%=ocorrencia.getInicio() == null ? "" : DataConverter.converterData(ocorrencia.getInicio(), Data.DDMMAAAA)%>" type="text" id="inicio" name="inicio" size="20" />
        </span>
        <span> at�
            <input value="<%=ocorrencia.getFim() == null ? "" : DataConverter.converterData(ocorrencia.getFim(), Data.DDMMAAAA)%>" type="text" id="fim" name="fim" size="20" />
        </span>
    </div>

    <h2>Local</h2>
    <input type="text" name="local_ocorrencia" id="local_ocorrencia" value="<%=ocorrencia.getLocalOcorrencia() == null ? "" : ocorrencia.getLocalOcorrencia()%>" maxlength="250" size="75" /><span id="dica_titulo">250</span>

    <h2>Mapa</h2>
    <input type="text" name="mapa" id="mapa" value="<%=ocorrencia.getMapa() == null ? "" : ocorrencia.getMapa()%>" maxlength="700" size="75" />
    <div id="dica_mapa">dica: Endere�o do local com Google Maps. (width="380" height="220")</div>

    <h2>Descri��o</h2>
    <textarea id="descricao" name="descricao" rows="30" cols="80"><%=ocorrencia.getDescricao() == null ? "" : ocorrencia.getDescricao()%></textarea>

    <input type="hidden" value="<%=ocorrencia.getInicio() == null ? "" : DataConverter.converterData(ocorrencia.getInicio(), Data.DDMMAAAAHHMM)%>" id="inicio" name="inicio" />
    <input type="hidden" value="<%=ocorrencia.getFim() == null ? "" : DataConverter.converterData(ocorrencia.getFim(), Data.DDMMAAAAHHMM)%>" id="fim" name="fim" />
    <input type="button" value="Enviar" onclick="enviar()" />
</form>


<script>
    (function($){
        // call setMask function on the document.ready event
        $(function(){
            $('input:text').setMask();
        }
    );
    })(jQuery);

    function mostrarCliente(tipo_ocorrencia){
        if(tipo_ocorrencia == 'web_conferencia' || tipo_ocorrencia == 'encontro_presencial'){
            $('#box_cliente').show('fast');
        }else{
            $('#box_cliente').hide('fast');
        }
        if(tipo_ocorrencia == 'web_conferencia'){
            $('#box_url').show('fast');
        }else{
            $('#box_url').hide('fast');
        }
    }

    function enviar(){
    <%if (ocorrencia.getId() == null) {%>
            var tipoOcorrencia = $('input[name=tipo]:checked').val();
    <%} else {%>
            var tipoOcorrencia = $('input[name=tipo]').val();
    <%}%>
            if(tipoOcorrencia == 'evento'){
                $('#form').attr("action", '<%=request.getContextPath()%>/evento');
            }
            if(tipoOcorrencia == 'web_conferencia'){
                $('#form').attr("action", '<%=request.getContextPath()%>/web_conferencia');
            }
            if(tipoOcorrencia == 'encontro_presencial'){
                $('#form').attr("action", '<%=request.getContextPath()%>/encontro_presencial');
            }
            $('#form').submit();
        }

        $(function() {
            $( "#inicio" ).datepicker({
                dateFormat: 'dd/mm/yy',
                dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
                dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
                dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
                monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
                monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
                nextText: 'Pr�ximo',
                prevText: 'Anterior',
                changeMonth: true,
                changeYear: true,
                showAnim: 'slideDown'
            });
            $( "#fim" ).datepicker({
                dateFormat: 'dd/mm/yy',
                dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
                dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
                dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
                monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
                monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
                nextText: 'Pr�ximo',
                prevText: 'Anterior',
                changeMonth: true,
                changeYear: true,
                showAnim: 'slideDown'
            });
        });

        //Valida��o-------------
        $.validator.addMethod(
        "data",
        function(value, element) {
            var check = false;
            var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
            if( re.test(value)){
                var adata = value.split('/');
                var dd = parseInt(adata[0],10);
                var mm = parseInt(adata[1],10);
                var yyyy = parseInt(adata[2],10)
                var xdata = new Date(yyyy,mm-1,dd);
                if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) )
                    check = true;
                else
                    check = false;
            } else
                check = false;
            return this.optional(element) || check;
        },
        "Essa data n�o � v�lida."
    );
        $(document).ready( function() {
            $("#form").validate({
                // Define as regras
                rules:{
                    tipo:{
                        required: true
                    },
                    nome:{
                        required: true, minlength: 2
                    },
                    local_ocorrencia:{
                        required: true, minlength: 2
                    },
                    //descricao:{
                    //    required: true, minlength: 1, maxlength: 200
                    //},
                    inicio:{
                        required: true, minlength: 10, maxlength: 10, data:true
                    },
                    fim:{
                        required: true, minlength: 10, maxlength: 10, data:true
                    }
                },
                // Define as mensagens de erro para cada regra
                messages:{
                    nome:{
                        required: "O informativo precisa ter um nome!",
                        minlength: "S� isso? N�o se parece com um nome de evento!"
                    },
                    local_ocorrencia:{
                        required: "Se n�o h� local, n�o h� evento. Por favor seja claro com o usu�rio!",
                        minlength: "S� isso? � prov�vel que esse campo n�o tenha sido preenchido corretamente!"
                    },
                    //descricao:{
                    //    required: "Seja claro com o usu�rio! Ele precisa saber algo sobre o evento!",
                    //    minlength: "S� isso? Com certeza h� mais a se falar sobre esse evento!",
                    //    maxlength: "Calma l�! N�o exagere, o excesso de conte�do pode comprometer a qualidade da p�gina!"
                    //},
                    inicio:{
                        required: "Como pode haver um evento sem data de in�cio?",
                        minlength: "Siga o formato padr�o de data! - dd/mm/aaaa",
                        maxlength: "Siga o formato padr�o de data! - dd/mm/aaaa"

                    },
                    fim:{
                        required: "Como pode haver um evento sem data de t�rmino?",
                        minlength: "Siga o formato padr�o de data! - dd/mm/aaaa",
                        maxlength: "Siga o formato padr�o de data! - dd/mm/aaaa"
                    }
                }
            });
        });
        //-----------------------

        jQuery(function(){
    <% if (ocorrencia instanceof Conferencia) {%>
            $('#box_cliente').show('fast');
    <% }%>
                
    <% if (ocorrencia instanceof WebConferencia) {%>
            $('#box_url').show('fast');
    <% }%>
        });

</script>