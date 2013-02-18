<%@page import="br.ciar.domain.configuracoes.CFG"%>
<!--MENU-->
<div id="menu">
    <ul class="menubv">
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/programa" title="COnheça o Programa">Conheça o Programa</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/centrosderecursos" title="Conheça os Centros de Recursos">Conheça os Centros de Recursos</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/cursos" title="Cursos">Cursos</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/informativos/noticias/" title="Notícias EaD">Notícias EaD</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/ocorrencias" title="Eventos EaD">Eventos EaD</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/ouvidoria" title="Ouvidoria EaD">Ouvidoria EaD</a></li>
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/faleconosco" title="Fale Conosco">Fale Conosco</a></li>
        <li class="topico_menu"><a href="http://moodle.brmz.uff.br/login/" title="Sala da Coordenação">Sala da Coordenação</a></li>
    </ul><br />

    <!--  Início NavBar Moodle    -->
    <div id="moodle_login" class="">
        <div class="topico_menu">Acesse seu curso</div>
        <form id="moodle_form" method="POST" action="http://moodle.brmz.uff.br/login/">
            <div id="moodle_dados" class="">
                <div id="user">
                    <input name="username" id="username" type="text" class="lform" size="25" placeholder="Usuário">
                </div>
                <div id="senha">
                    <input name="password" id="password" type="password" class="lform" size="25" placeholder="Senha">
                </div>
                <input name="acesso" type="submit" class="lform" id="acesso" value="Entrar" style="width:75px">
                <br />
                <a id="forgot_pass" href="http://moodle.brmz.uff.br/login/forgot_password.php">Esqueceu sua Senha?</a>
            </div>
        </form>
    </div>

    <script>        
        jQuery(function(){
            jQuery('input[placeholder], textarea[placeholder]').placeholder();
        });           
    </script>
    <!-- FIM NavBar Moodle    -->
</div>
<!--MENU-->
