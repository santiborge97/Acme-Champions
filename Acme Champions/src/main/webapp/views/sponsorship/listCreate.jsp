<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<h3><spring:message code="sponsorship.players" /></h3>
	
	<display:table pagesize="5" name="players" id="row" 
requestURI="${requestURI }" >
	
	<acme:column property="name" titleKey="sponsorship.player.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="sponsorship.player.surnames" value= "${row.surnames}: "/>
	
	<acme:column property="team.name" titleKey="sponsorship.player.team.name" value= "${row.team.name}: "/>
	
	<acme:url href="sponsorship/sponsor/sponsorPlayer.do?playerId=${row.id}" code="sponsorship.doSponsorship"/>
	
</display:table>

	
<h3><spring:message code="sponsorship.teams" /></h3>
	
		<display:table pagesize="5" name="teams" id="row2" 
requestURI="${requestURI }" >

	<acme:column property="name" titleKey="sponsorship.team.name" value= "${row2.name}: "/>
	
	<acme:url href="sponsorship/sponsor/sponsorTeam.do?teamId=${row2.id}" code="sponsorship.doSponsorship"/>
	
</display:table>

	
<h3><spring:message code="sponsorship.games" /></h3>
	
	<display:table pagesize="5" name="games" id="row3" 
requestURI="${requestURI }" >

	<acme:column property="homeTeam.name" titleKey="sponsorship.game.homeTeam" value= "${row3.homeTeam.name}: "/>
	
	<acme:column property="visitorTeam.name" titleKey="sponsorship.game.visitorTeam" value= "${row3.visitorTeam.name}: "/>
	
	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="formatTime"/>
	
	<display:column titleKey="sponsorship.game.gameDate"> 
		<fmt:formatDate type="date" value="${row3.gameDate }" pattern="${format }" />
		<fmt:formatDate type="time" value="${row3.gameDate }" pattern="${formatTime }" />
		
	</display:column>
	
	<acme:url href="sponsorship/sponsor/sponsorGame.do?gameId=${row3.id}" code="sponsorship.doSponsorship"/>
	
</display:table>
	
	<acme:button name="back" code="sponsorship.back" onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');"/>
