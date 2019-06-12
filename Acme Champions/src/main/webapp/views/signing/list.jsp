<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<display:table name="signings" id="row" requestURI="${requestURI }" pagesize="5">

	<acme:column property="player.name" titleKey="signing.player" value= "${row.player.name}: "/>

	<display:column titleKey="signing.price">
		<fmt:formatNumber type="number" maxFractionDigits="5" value="${row.price}" />
	</display:column>
	
	<acme:column property="offeredClause" titleKey="signing.offeredClause" value= "${row.offeredClause}: "/>
	
	<acme:column property="president.name" titleKey="signing.president" value= "${row.president.name}: "/>
		
	<acme:url href="signing/${autoridad}/accept.do?signingId=${row.id }" code="signing.accept" />
	
	<acme:url href="signing/${autoridad}/reject.do?signingId=${row.id }" code="signing.reject" />

	</display:table>
		
	<acme:button name="back" code="signing.back" onclick="javascript: relativeRedir('welcome/index.do');" />




