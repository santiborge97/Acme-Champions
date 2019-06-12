
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fieldset>
<legend><spring:message code="dashboard.trainingsPerManager" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgTM}"> N/A </c:if><c:if test="${not empty avgTM}">${avgTM}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minTM}"> N/A </c:if><c:if test="${not empty minTM}">${minTM}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxTM}"> N/A </c:if><c:if test="${not empty maxTM}">${maxTM}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdTM}"> N/A </c:if><c:if test="${not empty stdTM}">${stdTM}</c:if></p>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.lengthOfTrainings" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgLT}"> N/A </c:if><c:if test="${not empty avgLT}">${avgLT}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minLT}"> N/A </c:if><c:if test="${not empty minLT}">${minLT}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxLT}"> N/A </c:if><c:if test="${not empty maxLT}">${maxLT}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdLT}"> N/A </c:if><c:if test="${not empty stdLT}">${stdLT}</c:if></p>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.resultsPerFinder" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgRP}"> N/A </c:if><c:if test="${not empty avgRP}">${avgRP}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minRP}"> N/A </c:if><c:if test="${not empty minRP}">${minRP}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxRP}"> N/A </c:if><c:if test="${not empty maxRP}">${maxRP}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdRP}"> N/A </c:if><c:if test="${not empty stdRP}">${stdRP}</c:if></p>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.ratioplayers" /></legend>
<p><spring:message code="dashboard.goalkeepers" />: <c:if test="${empty rG}"> N/A </c:if><c:if test="${not empty rG}">${rG}</c:if></p>
<p><spring:message code="dashboard.defenders" />: <c:if test="${empty rD}"> N/A </c:if><c:if test="${not empty rD}">${rD}</c:if></p>
<p><spring:message code="dashboard.mildfiers" />: <c:if test="${empty rM}"> N/A </c:if><c:if test="${not empty rM}">${rM}</c:if></p>
<p><spring:message code="dashboard.strikers" />: <c:if test="${empty rS}"> N/A </c:if><c:if test="${not empty rS}">${rS}</c:if></p>
</fieldset>
<br/>
<fieldset>
<legend><spring:message code="dashboard.ratiomanagers" /></legend>
<p>Ratio: <c:if test="${empty rMan}"> N/A </c:if><c:if test="${not empty rMan}">${rMan}</c:if></p>
</fieldset>
<br/>
<p><spring:message code="dashboard.superiorTeams" /></p>
<p><c:if test="${empty sT}"> N/A </c:if><c:if test="${not empty sT}"><display:table name="sT" id="row"></display:table></c:if>
<br/>

<fieldset>
<legend><spring:message code="dashboard.matchesPerReferee" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgMR}"> N/A </c:if><c:if test="${not empty avgMR}">${avgMR}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minMR}"> N/A </c:if><c:if test="${not empty minMR}">${minMR}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxMR}"> N/A </c:if><c:if test="${not empty maxMR}">${maxMR}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdMR}"> N/A </c:if><c:if test="${not empty stdMR}">${stdMR}</c:if></p>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.yellowCardsPerPlayer" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgYP}"> N/A </c:if><c:if test="${not empty avgYP}">${avgYP}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minYP}"> N/A </c:if><c:if test="${not empty minYP}">${minYP}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxYP}"> N/A </c:if><c:if test="${not empty maxYP}">${maxYP}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdYP}"> N/A </c:if><c:if test="${not empty stdYP}">${stdYP}</c:if></p>
</fieldset>
<br/>

<p><spring:message code="dashboard.top5Player" /></p>
<p><c:if test="${empty tP}"> N/A </c:if><c:if test="${not empty tP}"><display:table name="tP" id="row"></display:table></c:if>
<br/>

<fieldset>
<legend><spring:message code="dashboard.teamsPerCompetition" /></legend>
<p><spring:message code="dashboard.avg" />: <c:if test="${empty avgTC}"> N/A </c:if><c:if test="${not empty avgTC}">${avgTC}</c:if></p>
<p><spring:message code="dashboard.min" />: <c:if test="${empty minTC}"> N/A </c:if><c:if test="${not empty minTC}">${minTC}</c:if></p>
<p><spring:message code="dashboard.max" />: <c:if test="${empty maxTC}"> N/A </c:if><c:if test="${not empty maxTC}">${maxTC}</c:if></p>
<p><spring:message code="dashboard.std" />: <c:if test="${empty stdTC}"> N/A </c:if><c:if test="${not empty stdTC}">${stdTC}</c:if></p>
</fieldset>
<br/>

<fieldset>
<legend><spring:message code="dashboard.oldestFederation" /></legend>
<p><c:if test="${empty oF}"> N/A </c:if><c:if test="${not empty oF}">${oF}</c:if></p>
</fieldset>


<br/>

<acme:button name="back" code="back" onclick="javascript: relativeRedir('welcome/index.do');" />

