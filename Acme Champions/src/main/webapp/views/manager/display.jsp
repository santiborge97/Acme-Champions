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


<acme:display code="actor.name" property="${manager.name }" />

<acme:display code="actor.surnames" property="${manager.surnames}" />

<spring:message code="actor.photo"/>: <br> <img src="${manager.photo }" width="10%" height="10%"/> <br>

<acme:display code="actor.email" property="${manager.email}" />

<acme:display code="actor.phone" property="${manager.phone}" />

<acme:display code="actor.address" property="${manager.address}" />

<acme:display code="manager.team" property="${manager.team.name}" />

<!--<acme:button name="back" code="actor.back.team" onclick="javascript: relativeRedir('position/list.do');" />-->

<security:authorize access="hasRole('PRESIDENT')">
<acme:button name="back" code="actor.back.team" onclick="javascript: relativeRedir('team/president,manager/listByPresident.do');" />
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
<acme:button name="back" code="actor.back.team" onclick="javascript: relativeRedir('team/president,manager/listByManager.do');" />
</security:authorize>


<security:authorize access="hasRole('PRESIDENT')">
<acme:button name="back" code="actor.back.finder" onclick="javascript: relativeRedir('finder/president/find.do');" />
</security:authorize>


<!-- 
<jstl:if test="${find}">
	<div>
		<a target="_blank" href="${targetSponsorship}"><img src="${bannerSponsorship }" alt="Banner" width="10%" height="10%" /></a>
	</div>
</jstl:if> -->