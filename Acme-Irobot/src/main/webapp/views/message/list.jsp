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

<!-- 
	Recieves: List<Messages> messages - los mensajes de un box y un actor correspondientes.
			  List<Box> boxes - los boxes de un actor para mover el mensaje. (no se incluye la trashbox)
-->

<security:authorize access="isAuthenticated()">
	<a href="message/create.do"><spring:message code='m.create' /></a>

	<display:table name="messages" id="row" requestURI="message/list.do" pagesize="5">

		<display:column titleKey="m.messages">
			<table border='1' style="width:100%">
				<tr>
				<td>
					<spring:message code='m.priority'/><jstl:out value="${row.priority}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<spring:message code='m.sender' /><jstl:out value="${row.sender.username}" /> <br>
					<spring:message code='m.subject' /><jstl:out value="${row.subject}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<jstl:out value="${row.body}" />
				</td>
				</tr>
				
				<tr>
				<td>
					<a href="message/delete.do?messageId=${row.id}"><spring:message code='m.delete' /></a>
				</td>
				</tr>
				
				<tr>
				<td>
					<select id="boxToMove">
						<jstl:forEach var="i" items="${boxes}">
							<option value="${i.id}"><jstl:out value="${i.name}"/></option>
						</jstl:forEach>	
					</select>
					<div id="messageID" style="visibility: hidden"><jstl:out value="${row.id}"/></div>
					<input id="clickMe" type="button" value='<spring:message code="m.move"/>' onclick="myFunction();" />
				</td>
				</tr>
			</table>
		</display:column>

	</display:table>
	<!-- edit method must diferentiate between no attributes where 
	the sender will be obtained via the create method. -->
	<a href="message/create.do"><spring:message code='m.create' /></a><br/>
	
	<input type="button" name="back"
		value="<spring:message code="m.back" />"
		onclick="javascript: window.location.replace('/Sample-Project/box/list.do')" />
	<br />
</security:authorize>
<script>
function myFunction () { 
	var e = document.getElementById("boxToMove");
	var boxId = e.options[e.selectedIndex].value;
	var messageId = document.getElementById("messageID").innerHTML;
	var str = window.location.href;
	var res = str.split("?");
	window.location.href = "message/moveToBox.do?messageId="+messageId+"&newBoxId="+boxId+"&"+res[1];
};


</script>