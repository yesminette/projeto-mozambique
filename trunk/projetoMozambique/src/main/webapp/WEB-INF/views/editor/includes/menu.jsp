<%@page import="br.ciar.domain.configuracoes.CFG"%>
<div id="menu">
    <ul id="_menu">
        <li>
            <h2>Informativo</h2>
            <ul>
                <li><a href="<%=CFG.contextPath%>/informativos/novo">Novo</a></li>
                <li><a href="<%=CFG.contextPath%>/informativos/lista">Visualizar</a></li>
            </ul>
        </li>
        <li>
            <h2>Ocorrencia</h2>
            <ul>
                <li><a href="<%=CFG.contextPath%>/ocorrencias/novo">Novo</a></li>
                <li><a href="<%=CFG.contextPath%>/ocorrencias/lista">Visualizar</a></li>
            </ul>
        </li>
    </ul>
</div>