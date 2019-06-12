<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<style>
#result{
	background-color: #4da6ff;
}
#goals{
	background-color: #66cc66;
}
#yellow{
	background-color: yellow;
}
#red{
	background-color: red;
}
</style>
<security:authorize access="hasRole('REFEREE')">
<h2><spring:message code="players.panel"/></h2>

<h3><spring:message code="players.home"/>
<spring:message code="players.scored" />: <jstl:out value="${countHome}" />
<spring:message code="players.yellowCards"/>: <jstl:out value="${countYellowHome}" />
<spring:message code="players.redCards"/>: <jstl:out value="${countRedHome}" /></h3>
</security:authorize>

<display:table name="players" id="row" requestURI="${requestURI }" pagesize="${pagesize }">
	
	<display:column titleKey="player.squad">
		<jstl:out value="${row.squadNumber} - ${row.squadName}" />
	</display:column>
	
	<acme:column property="name" titleKey="player.name" value= "${row.name}: "/>
	
	<acme:column property="surnames" titleKey="player.surnames" value= "${row.surnames}: "/>
	
	<jstl:if test="${language == 'en'}">
		<acme:column property="positionEnglish" titleKey="player.positionEnglish" value= "${row.positionEnglish} "/>
	</jstl:if>
	<jstl:if test="${language == 'es'}">
		<acme:column property="positionSpanish" titleKey="player.positionSpanish" value= "${row.positionSpanish} "/>
	</jstl:if>

	<display:column titleKey="player.injured"> 
				<spring:message code="player.${row.injured }" />
	</display:column>
	
	<display:column titleKey="player.punished"> 
				<spring:message code="player.${row.punished }" />
	</display:column>
	
	<acme:column property="team.name" titleKey="player.team.name" value= "${row.team.name}: "/>
	
	<security:authorize access="hasRole('MANAGER')">
	<acme:url href="training/manager/addPlayerPost.do?playerId=${row.id }&trainingId=${trainingId }" code="player.add" />
	</security:authorize>
	
	<security:authorize access="hasRole('REFEREE')">
	
	<acme:url href="minutes/referee/addPlayerScored.do?playerId=${row.id}&minutesId=${minutesId}" code="player.add.score" />
	<acme:url href="minutes/referee/addPlayerYellowCard.do?playerId=${row.id}&minutesId=${minutesId}" code="player.add.yellowCards" />
	<acme:url href="minutes/referee/addPlayerRedCard.do?playerId=${row.id}&minutesId=${minutesId}" code="player.add.redCards" />
	</security:authorize>
	

</display:table>

<security:authorize access="hasRole('REFEREE')">
<h3><div id="result">
<spring:message code="game.result" />: <jstl:out value="${minutes.homeScore} - ${minutes.visitorScore}" />
</div>
<div id="goals">
<spring:message code="players.scored.list" />: <c:forEach items="${listPlayersScore}" var="itemPlayerScore">
    ${itemPlayerScore.squadNumber} - ${itemPlayerScore.squadName} |
</c:forEach>
</div>
<div id="yellow">
<spring:message code="players.yellowCards.list"/>: <c:forEach items="${listPlayersYellow}" var="itemPlayersYellow">
    		${itemPlayersYellow.squadNumber} - ${itemPlayersYellow.squadName} |
	</c:forEach>
</div>
<div id="red">
<spring:message code="players.redCards.list"/>: <c:forEach items="${listPlayersRed}" var="itemPlayersRed">
    		${itemPlayersRed.squadNumber} - ${itemPlayersRed.squadName} |
	</c:forEach>
</div></h3>
<acme:button onclick="javascript: relativeRedir('minutes/referee/clear.do?minutesId=${minutesId }');" name="clear" code="minutes.clear"/>
<acme:button onclick="javascript: relativeRedir('minutes/referee/close.do?minutesId=${minutesId }');" name="close" code="minutes.close"/>
	
<h3><spring:message code="players.visitor"/>
<spring:message code="players.scored" />: <jstl:out value="${countVisitor}" />
<spring:message code="players.yellowCards"/>: <jstl:out value="${countYellowVisitor}" />
<spring:message code="players.redCards"/>: <jstl:out value="${countRedVisitor}" /></h3>



<display:table name="playersVisitor" id="row2" requestURI="${requestURI }" pagesize="${pagesize }">
	
	<display:column titleKey="player.squad">
		<jstl:out value="${row2.squadNumber} - ${row2.squadName}" />
	</display:column>
	
	<acme:column property="name" titleKey="player.name" value= "${row2.name}: "/>
	
	<acme:column property="surnames" titleKey="player.surnames" value= "${row2.surnames}: "/>
	
	<jstl:if test="${language == 'en'}">
		<acme:column property="positionEnglish" titleKey="player.positionEnglish" value= "${row2.positionEnglish} "/>
	</jstl:if>
	<jstl:if test="${language == 'es'}">
		<acme:column property="positionSpanish" titleKey="player.positionSpanish" value= "${row2.positionSpanish} "/>
	</jstl:if>

	<display:column titleKey="player.injured"> 
				<spring:message code="player.${row2.injured }" />
	</display:column>
	
	<display:column titleKey="player.punished"> 
				<spring:message code="player.${row2.punished }" />
	</display:column>
	
	<acme:column property="team.name" titleKey="player.team.name" value= "${row2.team.name}: "/>
	
	<security:authorize access="hasRole('REFEREE')">
	<acme:url href="minutes/referee/addPlayerScored.do?playerId=${row2.id}&minutesId=${minutesId}" code="player.add.score" />
	<acme:url href="minutes/referee/addPlayerYellowCard.do?playerId=${row2.id}&minutesId=${minutesId}" code="player.add.yellowCards" />
	<acme:url href="minutes/referee/addPlayerRedCard.do?playerId=${row2.id}&minutesId=${minutesId}" code="player.add.redCards" />
	</security:authorize>	

</display:table>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
<acme:button name="back" code="player.back" onclick="javascript: relativeRedir('calendar/manager/show.do');" />
</security:authorize>
<security:authorize access="hasRole('REFEREE')">
<acme:button name="back" code="player.back" onclick="javascript: relativeRedir('game/referee/listMyGames.do');" />
</security:authorize>

<security:authorize access="hasRole('MANAGER')">	
<script type="text/javascript">
	var trTags = document.getElementsByTagName("tr");
	for (var i = 0; i < trTags.length; i++) {
	  var tdStatus = trTags[i].children[4];
	  if (tdStatus.innerText == "NO") {
		  trTags[i].style.backgroundColor = "#98FB98";
	  } else if (tdStatus.innerText == "YES" || tdStatus.innerText == "Sí") {
		  trTags[i].style.backgroundColor = "#FFA07A";
	  }
	}
</script>
</security:authorize>
