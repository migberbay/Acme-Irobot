<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<security:authorize access="hasRole('ADMIN')">

<b><a href="admin/computeSpammers.do">compute spammers</a></b><br>
<b><a href="admin/computeScore.do">compute luck score</a></b><br>

<jstl:if test="${computedSpammers == true}">
	<b>SPAMMERS HAVE BEEN COMPUTED, CHECK THE ACTOR LIST FOR CHANGES</b>
</jstl:if>
 <jstl:if test="${computedScore == true}">
	<b>SCORE HAS BEEN COMPUTED, CHECK THE ACTOR LIST FOR CHANGES</b>
</jstl:if> 

<display:table name="${actors}" id="row" pagesize="5" requestURI="admin/listActors.do">

	<display:column titleKey="actor.name" property="name" />

	<display:column titleKey="actor.surnames" property="surnames" />
	
	<display:column>
	<a href="actor/show.do?actorId=${row.id}"><jstl:out value="${row.userAccount.username}"/></a><br>
	</display:column>
	
	<display:column titleKey="actor.isSuspicious" property="isSuspicious" />

	<display:column titleKey="actor.isBanned" property="isBanned" />
	
 	<display:column titleKey="admin.score" >
	<jstl:if test="${row.userAccount.authorities.contains(userAuth)}">
		<jstl:out value="${row.luckScore}"/>
	</jstl:if>
	</display:column> 

	<display:column titleKey="actor.action">
		<jstl:if test="${row.isBanned == false and row.isSuspicious == true}">
			<a href="admin/banActor.do?actorId=${row.id}"> <spring:message code="admin.banActor" /></a>
		</jstl:if>
		
		<jstl:if test="${row.userAccount.authorities.contains(userAuth)}">
		
		<jstl:if test="${row.isBanned == false and row.isSuspicious == false and row.luckScore >= 2.5}">
			<a href="admin/banActor.do?actorId=${row.id}"> <spring:message code="admin.banActor" /></a>
		</jstl:if>
				
		</jstl:if>
		
			
		<jstl:if test="${row.isBanned == true }">
			<a href="admin/unbanActor.do?actorId=${row.id}"> <spring:message code="admin.unBanActor" /></a>
		</jstl:if>
			
	</display:column>
	
</display:table>
</security:authorize>
