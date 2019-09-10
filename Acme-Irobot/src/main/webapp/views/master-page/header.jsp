<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme-IRobot Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="admin/dashboard.do"><spring:message code="master.page.admin.dashboard" /></a></li>
			<li><a class="fNiv" href="admin/configuration.do"><spring:message code="master.page.admin.configuration" /></a></li>
			<li><a class="fNiv" href="actor/register.do?type=ADMIN"><spring:message code="master.page.register.admin" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a href = "purchase/customer/list.do" class="fNiv"><spring:message	code="master.page.pushchase.list"/></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('SCIENTIST')">
			<li><a href = "irobot/scientist/list.do" class="fNiv"><spring:message	code="master.page.irobot.list.own"/></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href = "irobot/list.do" class="fNiv"><spring:message	code="master.page.irobot.list"/></a></li>
			<li><a href = "scientist/list.do" class="fNiv"><spring:message	code="master.page.scientist.list"/></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register.do?type=CUSTOMER"><spring:message code="master.page.register.customer" /></a></li>
					<li><a href="actor/register.do?type=SCIENTIST"><spring:message code="master.page.register.scientist" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a href = "irobot/list.do" class="fNiv"><spring:message	code="master.page.irobot.list"/></a></li>
			<li><a href = "scientist/list.do" class="fNiv"><spring:message	code="master.page.scientist.list"/></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					<li><a href="actor/show.do"><spring:message code="master.page.profile" /> </a></li>
				</ul>
			</li>
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

