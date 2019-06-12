<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<h3><spring:message code="player.squad" />: <jstl:out value="${player.squadNumber}-${player.squadName}"/></h3>

<h3><spring:message code="player.buyout" />: <fmt:formatNumber type="number" maxFractionDigits="5" value="${player.buyoutClause }" /></h3>

<acme:display code="player.team" property="${player.team.name}" />

<acme:display code="player.name" property="${player.name }" />

<acme:display code="player.surnames" property="${player.surnames}" />

<spring:message code="player.photo"/>: <br> <img src="${player.photo }" width="10%" height="10%"/> <br>

<acme:display code="player.email" property="${player.email}" />

<acme:display code="player.phone" property="${player.phone}" />

<acme:display code="player.address" property="${player.address}" />

<jstl:if test="${language == 'en'}">
	<acme:display code="player.positionEnglish" property= "${player.positionEnglish}"/>
</jstl:if>
<jstl:if test="${language == 'es'}">
	<acme:display code="player.positionSpanish" property= "${player.positionSpanish}"/>
</jstl:if>

<spring:message code="player.injured" />: <spring:message code="player.${player.injured }" /><br>

<spring:message code="player.punished" />: <spring:message code="player.${player.punished }" /><br>

<h3><spring:message code="player.statisticalData" /></h3>

<acme:display code="statisticalData.yellowCards" property="${statisticalData.yellowCards}" />

<acme:display code="statisticalData.redCards" property="${statisticalData.redCards}" />

<acme:display code="statisticalData.goals" property="${statisticalData.goals}" />

<acme:display code="statisticalData.matchsPlayed" property="${statisticalData.matchsPlayed}" />

<acme:display code="statisticalData.accumulatedYellowCard" property="${statisticalData.accumulatedYellowCard}" /><br>

<jstl:if test="${sponsorships!=null}">
	<div>		
		<c:forEach items="${sponsorships}" var="item">
    		<fieldset>
				<a target="_blank" href="${item.target}"><img src="${item.banner }" alt="Banner" width="10%" height="10%" /></a>
			</fieldset>
    		<br>
</c:forEach>
	</div>
</jstl:if> 

<security:authorize access="hasRole('PRESIDENT')">
<acme:button name="back" code="actor.back.team" onclick="javascript: relativeRedir('team/president,manager/listByPresident.do');" />
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
<acme:button name="back" code="actor.back.team" onclick="javascript: relativeRedir('team/president,manager/listByManager.do');" />
</security:authorize>


<security:authorize access="hasRole('PRESIDENT')">
<acme:button name="back" code="actor.back.finder" onclick="javascript: relativeRedir('finder/president/find.do');" />
</security:authorize>

 
