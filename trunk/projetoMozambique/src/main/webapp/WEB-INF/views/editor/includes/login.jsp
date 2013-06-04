<%@page import="br.ciar.domain.configuracoes.CFG"%>
<%@page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
            boolean falhaAutenticacao = (request.getParameter("authfailed") != null) ? request.getParameter("authfailed").equals("true") : false;
%>

<sec:authorize access="!isAuthenticated()" >
    <h1>Acesso</h1>
    <form id="formid" method="post" action="<%=CFG.contextPath%>/resources/j_spring_security_check">
        <%if (falhaAutenticacao){%><span class="errorMessage">Nome de usu�rio ou senha incorretos!</span><%}%>
        <div id="login">
            <input id="j_username" name="j_username" type="text" class="lform" size="20" />
        </div> <br />
        <div id="senha">
            <input id="j_password" name="j_password" type="password" class="lform" size="20" />
        </div><br />
        <input name="button" type="submit" class="button" id="acesso" value="Entrar" />
    </form>

</sec:authorize>

<sec:authorize access="isAuthenticated()" >
    <h1>Adminisra��o do Site.</h1>
</sec:authorize>

<!-- Usu�rios Logados que n�o podem incluir noticias -->
<sec:authorize ifNotGranted="SITE_EDITOR,SITE_WEBCONF,SITE_ADMIN" >
    <sec:authorize access="isAuthenticated()" >
        <div id="main">Voc� n�o tem permiss�es suficientes para acessar esta p�gina.</div><br />
    </sec:authorize>
</sec:authorize>