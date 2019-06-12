<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor" />
	<form:hidden path="player" />
	<form:hidden path="game" />
	<form:hidden path="team" />
	
	
	<acme:textbox code="sponsorship.banner" size="100" path="banner" obligatory="true"/>
	
	<acme:textbox code="sponsorship.target" size="100" path="target" obligatory="true"/>
	
	<acme:submit name="save" code="sponsorship.save" />
	
	<acme:cancel code="sponsorship.cancel" url="sponsorship/sponsor/list.do" />


</form:form>    