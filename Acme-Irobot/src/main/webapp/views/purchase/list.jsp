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


	
	<display:table name="purchases" id="row" requestURI="purchase/customer/list.do" pagesize="5">
		<display:column titleKey="irobot.action">
			<a href = "purchase/customer/show.do?purchaseId=${row.id}" >Show</a>
		</display:column>
		<display:column property="irobot.title" titleKey="irobot.title" />
		<display:column property="moment" titleKey="purchase.moment" />
	
	</display:table>
	
	