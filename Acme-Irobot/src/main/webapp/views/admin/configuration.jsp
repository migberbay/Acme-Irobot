<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- PRUEBA FORM -->

<security:authorize access="hasRole('ADMIN')">
	<form:form action="admin/configuration.do" modelAttribute="configuration">
	
	<form:hidden path="id"/>

	<acme:textbox path="systemName" code="admin.systemName"/>
	<acme:textbox path="banner" code="admin.banner"/> 
	<acme:textbox path="defaultPhoneCode" code="admin.defaultPhoneCode"/>
	<acme:textbox path="welcomeTextEnglish" code="admin.welcomeTextEnglish"/>
	<acme:textbox path="welcomeTextSpanish" code="admin.welcomeTextSpanish"/>
	
	<acme:submit name="save" code="admin.save"/>
	<acme:cancel url="" code="admin.cancel"/>			

<%-- <h2><spring:message code="admin.manageSpamWords"/></h2>
<spring:message code="admin.currentSpamWords"/>:<br>

<jstl:forEach var="i" items="${configurationForm.spamWords}">
<jstl:if test="${language==false}"><jstl:out value="${i.englishName} "/></jstl:if>
<jstl:if test="${language}"><jstl:out value="${i.spanishName} "/></jstl:if>
<a href="admin/deleteSpam.do?wordId=${i.id}"><spring:message code= "admin.remove"/></a><br>
</jstl:forEach>
	
	<acme:textbox path="wordEnglishName" code="admin.enWord"/>
	<acme:textbox path="wordSpanishName" code="admin.esWord"/>
	
	<acme:submit name="save" code="admin.save"/> --%>

</form:form>

</security:authorize>