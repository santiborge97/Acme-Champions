<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<display:table name="sponsorships" id="row" requestURI="${requestURI }" pagesize="5">
	
	<display:column>
		<a target="_blank" href="${row.target }"><spring:message code="sponsorship.target"/></a>
	</display:column>
	
	<display:column titleKey="sponsorship.game">
		<jstl:out value="${row.game.homeTeam.name}" /> - <jstl:out value="${row.game.visitorTeam.name}" />
	</display:column>
	
	<acme:column property="player.name" titleKey="sponsorship.player.name" value= "${row.player.name}: "/>
	
	<acme:column property="team.name" titleKey="sponsorship.team.name" value= "${row.team.name}: "/>
	
	<acme:column property="creditCard.number" titleKey="sponsorship.creditCard.number" value= "${row.creditCard.number}: "/>
	
	<acme:url href="sponsorship/sponsor/display.do?sponsorshipId=${row.id }" code="sponsorship.display" />

	</display:table>
	
	<acme:button name="create" code="sponsorship.create" onclick="javascript: relativeRedir('sponsorship/sponsor/listCreate.do');" />
	
		
	<acme:button name="back" code="sponsorship.back" onclick="javascript: relativeRedir('welcome/index.do');" />




