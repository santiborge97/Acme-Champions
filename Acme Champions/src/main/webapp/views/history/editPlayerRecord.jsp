<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="playerRecord/player/edit.do" modelAttribute="playerRecord">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox path="startDate" code="history.startDate" placeholder = "yyyy/MM/dd" obligatory="true"/>
	
	<acme:textbox path="endDate" code="history.endDate" placeholder = "yyyy/MM/dd" obligatory="true"/>
	
	<acme:textbox path="salary" code="history.salary" obligatory="true"/>
	
	<acme:textbox path="squadNumber" code="history.squadNumber" obligatory="true"/>
  	
	<acme:submit name="save" code="history.save" />	
	
	<jstl:if test="${playerRecord.id != 0 }">
		<acme:submit name="delete" code="history.delete" />
	</jstl:if>

	<acme:button name="back" code="history.back" onclick="javascript: relativeRedir('history/player/display.do');" />

</form:form>