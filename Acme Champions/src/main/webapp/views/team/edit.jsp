
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="team/president/edit.do" modelAttribute="team">
	
	
	<form:hidden path="id" />
	<form:hidden path="version" />
		
	
	<acme:textbox path="name" code="team.name" obligatory="true"/>
	
	<acme:textbox path="address" code="team.address" obligatory="true"/>	
	
	<acme:textbox path="stadiumName" code="team.stadiumName" obligatory="true"/>
	
	<acme:textbox path="badgeUrl" code="team.badgeUrl" obligatory="true"/>
	
	<acme:textbox path="trackRecord" code="team.trackRecord" obligatory="true"/>
		
	<acme:textbox path="establishmentDate" code="team.establishmentDate" obligatory="true" placeholder="yyyy/MM/dd"/>
	
	
	<acme:submit name="save" code="team.save" />	

	<acme:cancel code="team.cancel" url="team/president/display.do" />
		
	

</form:form>   
