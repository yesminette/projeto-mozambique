<%@page import="br.ciar.domain.configuracoes.CFG"%>
<!--MENU-->
<div id="menu">
    <ul class="menubv">
        <li class="topico_menu"><a href="<%=request.getContextPath()%>/programa" title="Conheça o Programa">Conheça o Programa</a></li>
        <li class="item_menu"><a href="<%=request.getContextPath()%>/parceiros" title="Universidades Parceiras">Universidades Parceiras</a></li>
        <li class="item_menu"><a href="<%=request.getContextPath()%>/centrosderecursos" title="Centros de Recursos">Centros de Recursos</a></li>
        <li class="item_menu"><a href="<%=request.getContextPath()%>/cursos" title="Cursos">Cursos</a></li>
        <li class="item_menu"><a href="http://moodle.brmz.uff.br/login/" target="_blank" title="Sala da Coordenação">Sala da Coordenação</a></li>
        <li class="item_menu"><a href="<%=request.getContextPath()%>/links" title="Sites Úteis">Sites Úteis</a></li>
        <li class="item_menu"><a href="<%=request.getContextPath()%>/faleconosco" title="Fale Conosco">Fale Conosco</a></li>
    </ul><br />
    <ul class="menubv">
        <li class="topico_menu mostrarmoodles">Moodle - ACESSE SEU CURSO</li>
        <li class="item_menu linkmoodle"><a href="http://www.ufjf.uem.mz/login/index.php" target="_blank" title="Licenciatura em Administração Pública">Licenciatura em Administração Pública</a></li>
        <li class="item_menu linkmoodle"><a href="http://moodle.brmz.uff.br/course/category.php?id=4" target="_blank" title="Licenciatura em Ensino Básico">Licenciatura em Ensino Básico</a></li>
        <li class="item_menu linkmoodle"><a href="http://moodle.brmz.uff.br/course/category.php?id=3" target="_blank" title="Licenciatura em Ensino Básico">Licenciatura em Ensino de Biologia</a></li>
        <li class="item_menu linkmoodle"><a href="http://moodle.brmz.uff.br/course/category.php?id=2" target="_blank" title="Licenciatura em Ensino Básico">Licenciatura em Ensino de Matemática</a></li>
    </ul><br />

    <!--  Início NavBar Moodle    -->
    <!--<div id="moodle_login" class="">
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
    </div>-->

    <script>        
        jQuery(function(){
            jQuery('input[placeholder], textarea[placeholder]').placeholder();
            jQuery('.mostrarmoodles').click(function(){
                jQuery('.linkmoodle').toggle('fast');
            });
        });           
    </script>
    <!-- FIM NavBar Moodle    -->
</div>
<!--MENU-->
