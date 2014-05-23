<li><a>
<c:out value="${compchild3.compIntitule}" />
<c:forEach var="node" items="${compchild3.competences}">
    <!-- TODO: print the node here -->
    <c:set var="node" value="${node}"/>
    <ul>
    	<jsp:include page="node3.jsp"/>
    </ul>
</c:forEach>
</a></li>
<!-- http://stackoverflow.com/questions/4638653/displaying-tree-on-jsp-page -->