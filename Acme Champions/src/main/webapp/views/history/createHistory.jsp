<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="history/player/edit.do" modelAttribute="history">
	
	<acme:textbox path="photos" code="history.photos" size="100" placeholder="http(s)://www.___.___,http(s)://www.___.___"/>
	
	<acme:textbox path="socialNetworkProfilelink" code="history.socialNetworkProfilelink" obligatory="true" size="100"/>
  	
	<acme:submit name="save" code="history.save" />	

	<acme:button name="back" code="history.back" onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>