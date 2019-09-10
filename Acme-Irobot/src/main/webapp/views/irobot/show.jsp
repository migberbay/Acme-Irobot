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
	
	<b><spring:message code="irobot.ticker"/>:</b> <jstl:out value="${irobot.ticker}"/><br>
	<b><spring:message code="irobot.title"/>:</b> <jstl:out value="${irobot.title}"/><br>
	<b><spring:message code="irobot.description"/>:</b> <jstl:out value="${irobot.description}"/><br>
	<b><spring:message code="irobot.price"/>:</b><jstl:out value="${irobot.price}"/><br>
	
	<h2>PURCHASES:</h2>
	
	<display:table name="irobot.purchases" id="row" requestURI="irobot/show.do" pagesize="5">
		<display:column titleKey="irobot.customer" >
 			<a href="actor/show.do?actorId =${row.customer.id}"><jstl:out value="${row.customer.userAccount.username}"/></a>
		</display:column> 
		<display:column titleKey="irobot.moment" property="moment"/>
	</display:table>
	
	<h2>COMMENTS:</h2>
	
	<display:table name="comments" id="row" requestURI="irobot/show.do" pagesize="5">
		<display:column titleKey="irobot.comments" property="body"/>
	</display:table>

	<input type="button" name="back"
		value="<spring:message code="irobot.back" />"
		onclick="javascript: window.location.replace('/Acme-IRobot/irobot/scientist/list.do')" />
	<br />
