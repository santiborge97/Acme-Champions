<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="sportRecord/player/edit.do" modelAttribute="sportRecord">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="sportName" code="history.sportName" obligatory="true"/>
	
	<acme:textbox path="startDate" code="history.startDate" placeholder = "yyyy/MM/dd" obligatory="true"/>
	
	<acme:textbox path="endDate" code="history.endDate" placeholder = "yyyy/MM/dd" obligatory="true"/>
	
	<spring:message code="history.yes" var="yes"/>
	<spring:message code="history.no" var="no"/>
	<form:label path="teamSport">
		<spring:message code="history.teamSport"/>*
	</form:label>
	<form:select path="teamSport" >
		<form:option label="${yes}" value="true"/>
		<form:option label="${no}" value="false"/>
	</form:select>
	<form:errors cssClass="error" path="teamSport"></form:errors>
	<br />	
  	
	<acme:submit name="save" code="history.save" />	
	
	<jstl:if test="${sportRecord.id != 0 }">
		<acme:submit name="delete" code="history.delete" />
	</jstl:if>

	<acme:button name="back" code="history.back" onclick="javascript: relativeRedir('history/player/display.do');" />

</form:form>