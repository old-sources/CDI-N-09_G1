<c:set var="nomNoeud" value="${noeud.compIntitule}" />
<c:set var="fillesNoeud" value="${noeud.compIntitule}" />


<c:out value="${nomNoeud}"/>


	<c:forEach items="${noeud.competences}" var="enfant">
	 		<ul>
				<li><a>
				<c:out value="- ${enfant.compIntitule}" />
				
 				<c:if test="${not empty enfant.competences}">
 				
 				<c:set var="listeNoeudsEnfant" value="${enfant.competences}" /> 
 				
<%-- 					<jsp:include page="node.jsp"/>  --%>
 				</c:if>
				</a></li>		
			</ul>
	</c:forEach>

<!-- http://stackoverflow.com/questions/4638653/displaying-tree-on-jsp-page -->