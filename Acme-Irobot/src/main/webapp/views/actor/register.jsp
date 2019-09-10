<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Recieves:
		RegisterForm;
 -->


	
<form:form action="actor/register.do" modelAttribute="registerForm" id="myform">
	<form:hidden path="type"/>	
	
	<h3><spring:message code="actor.userAcc"/>:</h3>
	<acme:textbox code="actor.username" path="username"/>
	<br />
	
	<acme:password code="actor.password" path="password" />
	<br />

	<acme:password code="actor.password2" path="password2" />
	<br />
	
	<jstl:if test="${passMatch == false}">
		<div class="error">passwords do not match</div>
	</jstl:if><br />

	
	<h3><spring:message code="actor.personalData"/>:</h3>
	<acme:textbox code="actor.name" path="name"/>
	<br />

	<acme:textbox code="actor.surnames" path="surnames"/>
	<br />
	
	<acme:textbox code="actor.photo" path="photo"/>
	<br />

	<acme:textbox code="actor.email" path="email"/>
	<br />
	
	<jstl:if test="${registerForm.type == 'ADMIN'}">
		<form:hidden path="VATNumber" value = "ESX12345678"/>
	</jstl:if>
	<jstl:if test ="${registerForm.type == 'CUSTOMER' || registerForm.type == 'SCIENTIST'}">
		<acme:textbox code="actor.vatNumber" path="VATNumber" placeholder="ESX12341234"/>
		<br />
	</jstl:if>
	

	<spring:message code = "actor.phone"/>+<form:input path="countryCode"/>(<form:input path="areaCode"/>)-<form:input path="phoneNumber"/>
	<form:errors path="countryCode" cssClass="error" />
	<form:errors path="areaCode" cssClass="error" />
	<form:errors path="phoneNumber" cssClass="error" />
	<br />
	
	<spring:message code="actor.warning"/>
	<a href="welcome/TOS.do"><spring:message code="actor.TOS"/></a>
	
	<br/>
	
	
	<button type="submit" name="save" class="btn btn-primary" onclick="checkNumber();">
		<spring:message code="actor.submit" />
	</button>
	
	<acme:cancel url="/" code="actor.cancel"/>


</form:form>
<script>
function checkNumber() {
	var CC = document.getElementById("countryCode").value;
	var AC = document.getElementById("areaCode").value;
	var EmptyAC = AC == "";
	var EmptyCC = CC == "";
	if(EmptyCC && !EmptyAC){
		return confirm("Are you sure you want to input that phone number?");
	}
}
</script>
