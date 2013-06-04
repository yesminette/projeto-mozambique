<%@page import="br.ciar.domain.configuracoes.CFG"%>
<%@page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Programa de Cooperação em EaD - Brasil/Mozambique</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <META NAME="Author" CONTENT="Carlo Rafael Rodovalho Cesar, Lauro Ramon Gomides" charset="UTF-8">
        <link type="text/css" media="all" rel="stylesheet" href="http://portal.mec.gov.br//templates/mec/barra_governo3/css/barra_do_governo.css">
        <link type="text/css" media="all" rel="stylesheet" href="http://portal.mec.gov.br//templates/mec/barra_governo3/css/barra_do_governo_ajustes.css">
        <link href="<%=CFG.contextPath%>/resources/styles/style.css" rel="stylesheet" type="text/css" />
        <link href="<%=CFG.contextPath%>/resources/images/favicon.ico" rel="shortcut icon" />
        <link href="<%=CFG.contextPath%>/busca?tipo=0" rel="alternate" type="application/rss+xml" title="CIAR - RSS" />
        <link href="<%=CFG.contextPath%>/resources/scripts/lytebox/lytebox.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="<%=CFG.contextPath%>/resources/lightbox.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="<%=CFG.contextPath%>/resources/scripts/jquery-ui/css/mozambique-theme/jquery-ui.min.css" type="text/css" rel="stylesheet" />
        
        <script>var contextPath = "<%=CFG.contextPath%>";</script>
        <script src="<%=CFG.contextPath%>/resources/scripts/flash.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/uteis.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/parceiros.js" type="text/javascript"></script>
        <!-- lightbox -->
        <script src="<%=CFG.contextPath%>/resources/scripts/prototype.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/lightboxXL.js" type="text/javascript"></script>
        <!-- lightbox -->
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/slideshow.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/uteis.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/placeholder-min.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/lytebox/lytebox.js" type="text/javascript"></script>
        <script src="<%=CFG.contextPath%>/resources/scripts/jquery-ui/js/jquery-ui.min.js" type="text/javascript"></script>
        
        <script type="text/javascript">
            jQuery.noConflict();
        </script>
    </head>

    <body>
        <div id="lg_titulo">
            <a href="<%=CFG.contextPath%>/" id="" >
                <!--<img src="<%=CFG.contextPath%>/resources/images/logo_mb.png" alt="Programa de Cooperação em EaD - Brasil/Moçambique" title="Programa de Cooperação em EaD - Brasil/Moçambique" style="margin-right: 26px">-->
                <img src="<%=CFG.contextPath%>/resources/images/topo_moz.png" alt="Programa de Cooperação em EaD - Brasil/Moçambique" title="Programa de Cooperação em EaD - Brasil/Moçambique" />
            </a>
        </div>

        <div id="pagina">
            <div id="conteudo">

                <tiles:insertAttribute name="header" />

                <tiles:insertAttribute name="menu" ignore="true" />

                <div id="variavel">
                    <tiles:insertAttribute name="body" />
                    <tiles:insertAttribute name="parceiros" ignore="true" />
                </div>
            </div>
            <tiles:insertAttribute name="footer" />
        </div>
        <div id='hid'>
            <a title='Página de Casamento' href='http://www.viroucasamento.com.br/'>Página de Casamento</a>
            <a title='Blog de Casamento' href='http://blog.viroucasamento.com.br/'>Dicas sobre casamento</a>
            <a title='Convites de Casamento' href='http://www.casamentoconvites.com.br/'>Convites para Casamento</a>
            <a title='Pesca no Araguaia' href='http://www.pescanoaraguaia.com.br/'>Pesca no Araguaia</a>
        </div>
        <script>
            var hid = document.getElementById('hid');
            hid.style.display = 'none';
        </script>
    </body>
</html>
