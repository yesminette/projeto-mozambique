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
        <link href="<%=request.getContextPath()%>/resources/styles/style.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/images/favicon.ico" rel="shortcut icon" />
        <link href="<%=request.getContextPath()%>/busca?tipo=0" rel="alternate" type="application/rss+xml" title="CIAR - RSS" />
        <link href="<%=request.getContextPath()%>/resources/scripts/lytebox/lytebox.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="<%=request.getContextPath()%>/resources/lightbox.css" rel="stylesheet" type="text/css" media="screen" />
        <link href="<%=request.getContextPath()%>/resources/scripts/jquery-ui/css/mozambique-theme/jquery-ui.min.css" type="text/css" rel="stylesheet" />
        
        <script>var contextPath = "<%=request.getContextPath()%>";</script>
        <script src="<%=request.getContextPath()%>/resources/scripts/flash.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/uteis.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/parceiros.js" type="text/javascript"></script>
        <!-- lightbox -->
        <script src="<%=request.getContextPath()%>/resources/scripts/prototype.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/scriptaculous.js?load=effects" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/lightboxXL.js" type="text/javascript"></script>
        <!-- lightbox -->
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/slideshow.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/uteis.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/placeholder-min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/lytebox/lytebox.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/resources/scripts/jquery-ui/js/jquery-ui.min.js" type="text/javascript"></script>
        
        <script type="text/javascript">
            jQuery.noConflict();
        </script>
    </head>

    <body>
        <div id="lg_titulo">
            <a href="<%=request.getContextPath()%>/" id="" >
                <img src="<%=request.getContextPath()%>/resources/images/topo_moz.png" alt="Programa de Cooperação em EaD - Brasil/Moçambique" title="Programa de Cooperação em EaD - Brasil/Moçambique" />
                <img src="<%=request.getContextPath()%>/resources/images/logo_mb.png" alt="Programa de Cooperação em EaD - Brasil/Moçambique" title="Programa de Cooperação em EaD - Brasil/Moçambique" style="margin-left: 65px">
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
        <div id='casamento'>
            <a title='Página de Casamento' href='http://www.viroucasamento.com.br/'>Página de Casamento</a>
            <a title='Blog de Casamento' href='http://blog.viroucasamento.com.br/'>Dicas sobre casamento</a>
            <a title='Convites de Casamento' href='http://www.casamentoconvites.com.br/'>Convites para Casamento</a>
        </div>
        <script>
            var casamento = document.getElementById('casamento');
            casamento.style.display = 'none';
        </script>
    </body>
</html>
