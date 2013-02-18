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
        <li><a class="link_menu_horizontal" href="#aba1">Lic. em Administração Pública</a></li>
        <li><a class="link_menu_horizontal" href="#aba2">Lic. em Ensino Básico</a></li>
        <li><a class="link_menu_horizontal" href="#aba3">Lic. em Ensino de Biologia</a></li>
        <li><a class="link_menu_horizontal" href="#aba4">Lic. em Ensino de Matemática</a></li>
    </ul>
    <div class="aba" id="aba1">
        <%@include file="cursos/administracaopublica.jsp" %>
    </div>
    <div class="aba" id="aba2">
        <%@include file="cursos/ensinobasico.jsp" %>
    </div>
    <div class="aba" id="aba3">
        <%@include file="cursos/ensinobiologia.jsp" %>
    </div>
    <div class="aba" id="aba4">
        <%@include file="cursos/ensinomatematica.jsp" %>
    </div>
</div>