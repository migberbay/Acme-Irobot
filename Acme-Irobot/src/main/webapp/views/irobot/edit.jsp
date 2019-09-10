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

<form:form action="irobot/scientist/edit.do" modelAttribute="irobot">

	<form:hidden path="id"/>
	<form:hidden path="purchases"/>
	<form:hidden path="scientist"/>
	<form:hidden path="ticker"/>
	
	<acme:textbox code="irobot.description" path="description"/>
	<acme:textbox code="irobot.title" path="title"/>
	<acme:textbox code="irobot.price" path="price"/>
	
	<spring:message code ="irobot.isDecomissioned"/>
	<form:checkbox path="isDecomissioned"/>
	
	<br/>
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="irobot.save" />
	</button>
	
	<acme:cancel url="irobot/scientist/list.do" code="irobot.back"/>
	<br />	

</form:form>
	