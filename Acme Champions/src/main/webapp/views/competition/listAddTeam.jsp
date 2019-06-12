<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>



<display:table name="teamsAll" id="row" requestURI="${requestURI }" pagesize="5">
	
	<acme:column property="name" titleKey="competition.teams" value= "${row.name}: "/>
	
	<acme:url href="competition/federation/addTeam.do?teamId=${row.id}&competitionId=${competitionId}" code="competition.addTeam"/>

</display:table>
		
	<acme:button name="back" code="competition.back" onclick="javascript: relativeRedir('competition/federation/list.do');" />




