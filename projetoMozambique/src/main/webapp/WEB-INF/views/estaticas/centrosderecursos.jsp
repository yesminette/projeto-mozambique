<style>
    .ui-widget-header { height: 26px; }
</style>
<script>
    jQuery(function() {
        jQuery("#abas").tabs();
    });
</script>
<div id="abas">
    <div id="local_texto"></div>
    <ul>
        <li><a class="link_menu_horizontal" href="#aba1">Beira</a></li>
        <li><a class="link_menu_horizontal"  href="#aba2">Lichinga</a></li>
        <li><a class="link_menu_horizontal"  href="#aba3">Maputo</a></li>
    </ul>
    <div class="aba" id="aba1">
        <%@include file="beira.jsp" %>
    </div>
    <div class="aba" id="aba2">
        <%@include file="lichinga.jsp" %>
    </div>
    <div class="aba" id="aba3">
        <%@include file="maputo.jsp" %>
    </div>
</div>