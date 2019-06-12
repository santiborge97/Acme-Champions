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


<fieldset>
<h3><spring:message code="history.personalData" /></h3>

	<spring:message code="history.photos" />:
	<br/>
	<c:forEach items="${history.personalData.photos}" var="photos">
		<div style="margin-top: 1px">
			<img src="${photos}" style="max-width: 10%; max-heinght: 10%" />
		</div>
		<br/>
	</c:forEach>


<div><spring:message code="history.socialNetworkProfilelink" />:
<a href="${history.personalData.socialNetworkProfilelink}" target="_blank">${history.personalData.socialNetworkProfilelink }</a>
</div>

<br/>

<a href="personalData/player/edit.do"><spring:message code="history.editPersonal"/></a>
	
</fieldset>
<br/>
<br/>

<fieldset>
<h3><spring:message code="history.playerRecord" /></h3>

<display:table name="history.playerRecords" pagesize="5" id="row1" requestURI="${requestUri}">

	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="history.startDate"> 
		<fmt:formatDate value="${row1.startDate }" pattern="${format}" />
	</display:column>
	
	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="history.endDate"> 
		<fmt:formatDate value="${row1.endDate }" pattern="${format}" />
	</display:column>
	
	<acme:column property="salary" titleKey="history.salary" value= "${row1.salary} "/>
	
	<acme:column property="squadNumber" titleKey="history.squadNumber" value= "${row1.squadNumber} "/>
	
	
	<acme:url href="playerRecord/player/edit.do?playerRecordId=${row1.id}" code="history.edit"/>

	
</display:table>

<br/>

<a href="playerRecord/player/create.do"><spring:message code="history.create"/></a>

</fieldset>

<br/>
<br/>

<fieldset>
<h3><spring:message code="history.sportRecord" /></h3>

<display:table name="history.sportRecords" pagesize="5" id="row2" requestURI="${requestUri}">

	<acme:column property="sportName" titleKey="history.sportName" value= "${row2.sportName} "/>

	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="history.startDate"> 
		<fmt:formatDate value="${row2.startDate }" pattern="${format}" />
	</display:column>
	
	<spring:message code="dateFormat" var="format"/>
	<display:column titleKey="history.endDate"> 
		<fmt:formatDate value="${row2.endDate }" pattern="${format}" />
	</display:column>

	<display:column titleKey="history.teamSport">
		<jstl:if test="${row2.teamSport}">
			<spring:message code="history.yes" />
		</jstl:if>
		<jstl:if test="${!row2.teamSport}">
			<spring:message code="history.no" />
		</jstl:if>
	</display:column>


	<acme:url href="sportRecord/player/edit.do?sportRecordId=${row2.id }" code="history.edit"/>	
	
</display:table>
<br/>

<a href="sportRecord/player/create.do"><spring:message code="history.create"/></a>

</fieldset>
<br/>
<br/>

<a href="history/player/delete.do"><spring:message code="history.deleteall"/></a>

<acme:button name="back" code="history.back" onclick="javascript: relativeRedir('welcome/index.do');"/>


