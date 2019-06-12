<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<display:table name="hirings" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="manager.name" titleKey="hiring.manager" value= "${row.manager.name}: "/>

	<display:column titleKey="hiring.price">
		<fmt:formatNumber type="number" maxFractionDigits="5" value="${row.price}" />
	</display:column>
	
	<acme:column property="president.name" titleKey="hiring.president" value= "${row.president.name}: "/>
		
	<acme:url href="hiring/${autoridad}/accept.do?hiringId=${row.id }" code="hiring.accept" />
	
	<acme:url href="hiring/${autoridad}/reject.do?hiringId=${row.id }" code="hiring.reject" />

	</display:table>
		
	<acme:button name="back" code="hiring.back" onclick="javascript: relativeRedir('welcome/index.do');" />




