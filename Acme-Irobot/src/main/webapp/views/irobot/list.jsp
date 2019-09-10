<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- 

-->
	<form:form action="irobot/list.do" modelAttribute="form">
		<acme:textbox code="irobot.search" path="keyword"/>
		<acme:submit name="save" code="irobot.save"/>
	</form:form>
	<jstl:out value=""/>
	<br>
	<jstl:if test="${isOwner and loggedUser}">
	<b><a href = "irobot/scientist/create.do">Create</a></b>
	</jstl:if>
	
	<display:table name="irobots" id="row" requestURI="${requestURI}" pagesize="5">
	
	<jstl:if test="${loggedUser}">
		<display:column titleKey="irobot.action">
			<a href = "comment/create.do?irobotId=${row.id}">Comment</a><br>
			<jstl:if test="${isCustomer and row.isDecomissioned == false}">
				<a href = "purchase/customer/create.do?irobotId=${row.id}">Purchase</a><br>
			</jstl:if>
		
			<jstl:if test="${isOwner}">
				<a href = "irobot/scientist/edit.do?irobotId=${row.id}">Edit</a><br>
				<a href = "irobot/scientist/show.do?irobotId=${row.id}">Show</a><br>
				<a href = "irobot/scientist/delete.do?irobotId=${row.id}">Delete</a><br>
			</jstl:if>
		</display:column>
	</jstl:if>

		<display:column titleKey="irobot.scientist">
			<a href = "actor/show.do?actorId=${row.scientist.id}" ><jstl:out value="${row.scientist.name}"/></a>
		</display:column>
		<display:column property="title" titleKey="irobot.title" />
		<display:column property="ticker" titleKey="irobot.ticker" />
		<display:column property="description" titleKey="irobot.description" />
		<display:column property="price" titleKey="irobot.price" />
	
	</display:table>
	
	