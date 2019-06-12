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

<security:authorize access="hasRole('MANAGER')">


	<spring:message code="dateFormat" var="format"/>
	<spring:message code="timeFormat" var="formatTime"/>
	
<div><spring:message code="training.startDate" />:
<fmt:formatDate type="date" value="${training.startDate}" pattern="${format}"/>
<fmt:formatDate type="time" value="${training.startDate}" pattern="${formatTime}"/>
</div>

<div><spring:message code="training.endingDate" />:
<fmt:formatDate type="date" value="${training.endingDate}" pattern="${format}"/>
<fmt:formatDate type="time" value="${training.endingDate}" pattern="${formatTime}"/>

</div>

<acme:display code="training.place" property="${training.place }" />

<acme:display code="training.description" property="${training.description }" />


<spring:message code="training.players" />:  
	<c:forEach items="${training.players}" var="item">
    	${item.name} /
	</c:forEach>
<br>

<jstl:if test="${startDateGood }">
<acme:button name="edit" code="training.edit" onclick="javascript: relativeRedir('training/manager/edit.do?trainingId=${training.id }');" />


<acme:button onclick="javascript: relativeRedir('training/manager/addPlayer.do?trainingId=${training.id }');" name="add" code="training.add" />
</jstl:if>

<acme:button name="back" code="training.back" onclick="javascript: relativeRedir('calendar/manager/show.do');" />

</security:authorize>

