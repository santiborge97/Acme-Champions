<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="boxes" id="row" requestURI="${requestURI }" pagesize="5">

	
	<acme:column property="name" titleKey="box.name" value= "${row.name}: "/>
		
	<display:column>
	
		<a href="message/actor/list.do?boxId=${row.id }"><spring:message code="box.displayMessages"/></a>
		
	</display:column>
	
</display:table>

	
	<a href="actor/list.do"><spring:message code="box.listActor"/></a>

<acme:button name="back" code="box.back" onclick="javascript: relativeRedir('welcome/index.do');" />