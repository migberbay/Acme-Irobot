<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	<div>		
		<b><spring:message code="actor.username"/></b>: <jstl:out value="${actor.userAccount.username}"/> <br/>			
		<b><spring:message code="actor.name"/></b>: <jstl:out value="${actor.name}"/> <br/>
		<b><spring:message code="actor.surnames"/></b>: <jstl:out value="${actor.surnames}"/> <br/>
		<b><spring:message code="actor.photo"/></b>: <jstl:out value="${actor.photo}"/> <br/>				 				 
		<b><spring:message code="actor.email"/></b>: <jstl:out value="${actor.email}"/> <br/>			 				 
		<b><spring:message code="actor.phone"/></b>: <jstl:out value="${actor.phone}"/> <br/>
		
		<jstl:if test="${logged}">
			<b><a href="actor/editPersonal.do"><spring:message code="actor.edit" /></a> Personal Data</b> <br/>
		</jstl:if>
	</div>
	<br/>
	
	<jstl:if test="${(actorIsScientist or actorIsCustomer) and logged}">
  			<jstl:choose>
  				<jstl:when test="${actor.creditCard == null}">
  					you have no credit card created, make one <a href ="actor/editCreditCard.do" >here</a>.
  				</jstl:when>
  				<jstl:otherwise>
  					<b><spring:message code="actor.holder"/></b>: <jstl:out value="${actor.creditCard.holder}"/> <br/>
					<b><spring:message code="actor.make"/></b>: <jstl:out value="${actor.creditCard.make}"/> <br/>
					<b><spring:message code="actor.number"/></b>: <jstl:out value="${actor.creditCard.number}"/> <br/>
					<b><spring:message code="actor.CVV"/></b>: <jstl:out value="${actor.creditCard.CVV}"/> <br/>
					<b><spring:message code="actor.expiration"/></b>: <jstl:out value="${actor.creditCard.expirationDate.getMonth()+1}"/> / <jstl:out value="${actor.creditCard.expirationDate.getYear()+1900}"/> <br/>
  					
  					<b><a href="actor/editCreditCard.do"><spring:message code="actor.edit" /></a> CreditCard Data</b> <br/>
  				</jstl:otherwise>
  			</jstl:choose>
		</jstl:if>  
		
	<jstl:if test="${logged}">
			<br>
			<a href="actor/delete.do" onclick="confirmLeave();">delete ALL data</a><br>
			<a href="actor/generateData.do"> <spring:message code="actor.generate" /> </a> <br>
	</jstl:if>
		
		<input type="button" name="back"
		value="<spring:message code="actor.show.back" />"
		onclick="javascript: window.location.replace('')" />
	<br/>
	
<script>
	function confirmLeave() {
	if(!confirm("Are you sure about this? this operation is not reversible and will delete all your data...")) return;
	}
</script>