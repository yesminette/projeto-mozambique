<style>
    .ui-widget-header { height: 26px; }
</style>
<script>
    jQuery(function() {
        jQuery("#abas").tabs();
    });
    jQuery(document).ready(function(){
        jQuery("#local_texto").html(jQuery("#texto"));
        jQuery("#texto").css("display", "block");
    });
</script>
<div id="texto" class="aba ui-tabs-panel ui-widget-content ui-corner-bottom" style="display: none">
    <p style="margin-top:0px;">
        Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, 
        sit amet.Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, sit amet.
    </p>
    <p>
        Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, 
        sit amet.Lorem ipsum Dollor, sit amet.Lorem ipsum Dollor, sit amet.
    </p>
</div>
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