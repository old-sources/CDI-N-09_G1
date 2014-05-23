<ul>
	<!-- <p>-------</p> -->
	<!-- listcompchild = 1 liste d'enfants  -->
	<%-- <c:forEach items="${listcompchild}" var="node2"> --%>
<!-- 	<p>entree fichier</p> -->

<c:set var="compchild2" value="${noeud.compIntitule}" />

<c:out value="${compchild2}" />
	<c:forEach items="${noeud.competences}" var="enfant">
	 		<ul>
				<li><a>
				<c:out value="- ${enfant.compIntitule}" />
				
 				<c:if test="${not empty enfant.competences}">
 				<c:set var="compchild3" value="${node2.competences}" /> 
 				
				<jsp:include page="node.jsp"/> 
 				</c:if>
				</a></li>		
			</ul>
	</c:forEach>
</ul>
<!-- http://stackoverflow.com/questions/4638653/displaying-tree-on-jsp-page -->