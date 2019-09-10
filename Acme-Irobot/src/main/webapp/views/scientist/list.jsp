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
	
	<display:table name="scientists" id="row" requestURI="${requestURI}" pagesize="5">

		<display:column titleKey="actor.irobot">
			<a href = "irobot/list.do?scientistId=${row.id}" ><spring:message code="actor.irobot"/></a>
		</display:column>
		<display:column property="name" titleKey="actor.name" />
		<display:column property="surnames" titleKey="actor.surnames" />
		<display:column property="photo" titleKey="actor.photo" />
		<display:column property="email" titleKey="actor.email" />
		<display:column property="phone" titleKey="actor.phone" />
		<display:column property="VATNumber" titleKey="actor.vatNumber" />
	
	</display:table>
	
	