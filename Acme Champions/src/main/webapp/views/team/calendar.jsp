<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

	<h3><spring:message code="calendar.games" /></h3>
	<display:table name="games" id="row" requestURI="${requestURI }" pagesize="5">

	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="formatTime"/>
	<display:column titleKey="game.gameDate"> 
		<fmt:formatDate type="date" value="${row.gameDate }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row.gameDate }" pattern="${formatTime}" />
		
	</display:column>
	
	<acme:column property="place" titleKey="game.place" value= "${row.place}: "/>
	
	<display:column titleKey="game.friendly"> 
			<spring:message code="game.${row.friendly }" />
	</display:column>
	
	<acme:column property="homeTeam.name" titleKey="game.homeTeam" value= "${row.homeTeam.name}: "/>
	
	<acme:column property="visitorTeam.name" titleKey="game.visitorTeam" value= "${row.visitorTeam.name}: "/>
	
	<acme:column property="referee.name" titleKey="game.referee" value= "${row.referee.name}: "/>
	
	<acme:url href="game/display.do?gameId=${row.id}" code="game.display" />

	</display:table>

	<h3><spring:message code="calendar.trainings" /></h3>
	<display:table name="trainings" id="row2" requestURI="${requestURI }" pagesize="5">

	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="formatTime"/>
	<display:column titleKey="training.startDate"> 
		<fmt:formatDate type="date" value="${row2.startDate }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row2.startDate }" pattern="${formatTime}" />
	</display:column>
	
	<display:column titleKey="training.endingDate"> 
		<fmt:formatDate type="date" value="${row2.endingDate }" pattern="${format}" />
		<fmt:formatDate type="time" value="${row2.endingDate }" pattern="${formatTime}" />
		
	</display:column>
	
	<acme:column property="place" titleKey="training.place" value= "${row2.place}: "/>
	
	<acme:column property="description" titleKey="training.description" value= "${row2.description}: "/>
	
	<acme:url href="training/manager/display.do?trainingId=${row2.id }" code="training.display" />

	</display:table>
	
	<a href="training/manager/create.do"><spring:message code="training.createe"/></a>	
		
	<acme:button name="back" code="training.back" onclick="javascript: relativeRedir('welcome/index.do');" />




