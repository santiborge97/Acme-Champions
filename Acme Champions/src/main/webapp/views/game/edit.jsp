<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="game/referee/edit.do" modelAttribute="game">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="game.gameDate" path="gameDate" placeholder="yyyy/MM/dd HH:mm" obligatory="true"/>

	<spring:message code="game.homeTeam" />*: 
	<form:select path="homeTeam">
		<spring:message code="choose.team" var="choose"/>
		<form:option label="${choose}" value="${visitorTeam }"/>
		<form:options items="${teams }" itemLabel="name"
			itemValue="id" />
	</form:select>
	<form:errors path="homeTeam" cssClass="error" />
	
	<spring:message code="game.visitorTeam" />*: 
	<form:select path="visitorTeam">
		<spring:message code="choose.team" var="choose"/>
		<form:option label="${choose}" value="${visitorTeam }"/>
		<form:options items="${teams }" itemLabel="name"
			itemValue="id" />
	</form:select>
	<form:errors path="visitorTeam" cssClass="error" />
	
	
	<br>
	<spring:message code="game.confirmation" var="confirmation" />
	<input type="submit" name="save" value="<spring:message code="game.save"/>" onClick="confirm('${confirmation}')" />
	<jstl:if test="${game.id!=0 }">
	<acme:submit name="delete" code="game.delete" />
	</jstl:if>
	<acme:cancel code="game.cancel" url="game/referee/listMyGames.do" />


</form:form>  