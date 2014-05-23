<p>---------------------------------</p>
<!-- node2 = enfant -->
<c:forEach var="node2" items="${node1}">

<%-- <c:forEach items="${node}" var="comp"> --%>
<%-- 	<c:forEach items="${comp.competences}" var="compchild"> --%>

 	<c:set var="compchild2" value="${node2}"/>
 	<c:out value="${compchild2}.compIntitule" />
 	<c:out value="${node2}.compIntitule" />
 	<c:out value="${compchild2.compIntitule}" />
 	
<%--  	<c:out value="${node2.compIntitule}"/> --%>

<%-- 		<li><a><c:out value="+ ${compchild.compIntitule}" /></a></li> --%>
<%-- 		<li><a><c:out value="- ${compchild2}.compIntitule"/></a></li> --%>

</c:forEach>
<!-- http://stackoverflow.com/questions/4638653/displaying-tree-on-jsp-page -->