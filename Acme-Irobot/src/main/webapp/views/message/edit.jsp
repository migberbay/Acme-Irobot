<%--
 * edit.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
	Recibe: Message userMessage: el mensaje a editar.
			List<Actor> actors: todos los actores del sistema.
			boolean isBroadcast: indica si el mensaje a crear es de braodcast o no.
 -->

<form:form action="message/edit.do" modelAttribute="messageForm">

	<jstl:if test="${isBroadcast==true}">
		<form:hidden path="recipients"/>
	</jstl:if>
	
	<security:authorize access="isAuthenticated()">
	
	<form:label path="priority">
		<spring:message code="m.priority" />
	</form:label>
	
	<form:select path="priority">
		<form:option label="HIGH" value="HIGH"/>
		<form:option label="NEUTRAL" value="NEUTRAL"/>
		<form:option label="LOW" value="LOW"/>
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	
	<jstl:if test="${isBroadcast==false}">
<%-- 	<form:label path="recipients">
	<spring:message code="m.recipient" />
	</form:label>
	
	<form:select multiple="true" path="recipients">
		<form:options items="${actors}" itemLabel="userAccount.username" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="recipients" /> --%>
	
	systems user accounts:
	<jstl:forEach items="${accounts}" var="account">
		<jstl:out value="${account.username}, "/>
	</jstl:forEach>
	<br/>
	<acme:textbox code="m.recipients" path="recipients" placeholder="useraccount1,useraccount2, useraccount3 "/>
	<br />
	</jstl:if>
	
	<acme:textbox code="m.subject" path="subject"/>
	<br />	
	<acme:textarea code="m.body" path="body"/>
	<br />
	<acme:textbox code="m.tags" path="tags" placeholder="tag1,tag2, tag3, tag4"/>
	<br />
	<acme:submit name="save" code="m.save"/>
	<acme:cancel url="message/list.do" code="m.cancel"/>
	<br />
	
	</security:authorize>

</form:form>