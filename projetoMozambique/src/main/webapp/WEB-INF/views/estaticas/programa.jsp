<style>
    .ui-widget-header { height: 26px; }
</style>
<script>
    jQuery(function() {
        jQuery("#abas").tabs();
    });
</script>
<div id="abas">
    <ul>
        <li><a class="link_menu_horizontal" href="#aba1">Sobre o Programa</a></li>
        <li><a class="link_menu_horizontal" href="#aba2">Informes</a></li>
        <li><a class="link_menu_horizontal" href="#aba3">Documentos</a></li>
    </ul>
    <div class="aba" id="aba1">
        <%@include file="sobreoprograma.jsp" %>
    </div>
    <div class="aba" id="aba2">
        <%@include file="informes.jsp" %>
    </div>
    <div class="aba" id="aba3">
        <%@include file="documentos.jsp" %>
    </div>
</div>