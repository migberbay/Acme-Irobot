<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="madruga" tagdir="/WEB-INF/tags" %>

<spring:message code="error.application.handyWorker"/>		

<jstl:if test="${creditCardNotValid}">

<div class = "error">
	<h2>
		You must have a valid credit card in order to perform this action go to your profile and update it.
		A valid credit card mustn't expire in less than 7 days.
	</h2>
</div>

</jstl:if>