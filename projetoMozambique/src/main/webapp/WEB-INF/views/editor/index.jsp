<%@page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="br.ciar.domain.configuracoes.CFG"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>CIAR - Editor</title>
        <link href="<%=CFG.contextPath%>/resources/styles/style.css" rel="stylesheet" type="text/css" />
        <link href="<%=CFG.contextPath%>/resources/styles/standard.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="<%=CFG.contextPath%>/resources/scripts/jquery-ui/css/mozambique-theme/jquery-ui.min.css" type="text/css" rel="stylesheet" />
        
        <script>var contextPath = "<%=CFG.contextPath%>";</script>
        <script src="<%=CFG.contextPath%>/resources/scripts/tinymce/jscripts/tiny_mce/tiny_mce.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery.validate.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/uteis.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery-ui/js/jquery-ui.min.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery.meio.mask.js" type="text/javascript"></script>

        <script type="text/javascript">
            tinyMCE.init({
                mode : "textareas",
                theme : "advanced",
                convert_urls : false,
                theme_advanced_disable : "justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,sub,sup,visualaid,help,indent,outdent"
            });
        </script>

    </head>
    <body>

        <tiles:insertAttribute name="header" />

        <tiles:insertAttribute name="menu" />

        <div id="main">
            <!-- Mostrar nome do usuário e logout -->
            <sec:authorize access="isAuthenticated()">
                <div id="welcome">
                    <sec:authentication property="principal.dn" var="userDn" />
                    Olá <strong><c:out value="${fn:substringAfter(fn:substringBefore(userDn, ','), 'cn=')}" /></strong> =)
                    <a href="<%=CFG.contextPath%>/resources/j_spring_security_logout">Logout</a>
                </div>
            </sec:authorize>

            <tiles:insertAttribute name="body" />
            
        </div>
    </body>
</html>
