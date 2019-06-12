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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<security:authorize access="hasRole('PRESIDENT')">

<jstl:if test="${existTeam }">

<jstl:if test="${team.functional }">
<h3 style="color: green;"><spring:message code="team.functional.${team.functional }" /></h3>
</jstl:if>
<jstl:if test="${!team.functional }">
<h3 style="color: red;"><spring:message code="team.functional.${team.functional }" /></h3>
</jstl:if>

<acme:display code="team.name" property="${team.name }" />

<acme:display code="team.address" property="${team.address }" />

<acme:display code="team.stadiumName" property="${team.stadiumName }" />

<acme:display code="team.badgeUrl" property="${team.badgeUrl }" />

<acme:display code="team.trackRecord" property="${team.trackRecord }" />

<div><spring:message code="team.establishmentDate" />:
<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="formatTime"/>
		<fmt:formatDate type="date" value="${team.establishmentDate }" pattern="${format}" />
</div>

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


<acme:button name="edit" code="team.edit" onclick="javascript: relativeRedir('team/president/edit.do');" />

<acme:button name="members" code="team.members" onclick="javascript: relativeRedir('team/president,manager/listByPresident.do');" />

</jstl:if>

<jstl:if test="${!existTeam }">

<acme:button name="create" code="team.create" onclick="javascript: relativeRedir('team/president/create.do');" />

</jstl:if>


<acme:button name="back" code="team.back" onclick="javascript: relativeRedir('welcome/index.do');" />

</security:authorize>

