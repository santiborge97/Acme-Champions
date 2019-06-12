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

<spring:message code="dateFormat" var="format"/>
<spring:message code="timeFormat" var="formatTime"/>

<acme:display code="message.sender" property="${message1.sender.name} "/>
	
<div><spring:message code="message.moment" />:
<fmt:formatDate type="date" value="${message1.moment}" pattern="${format}"/>
<fmt:formatDate type="time" value="${message1.moment}" pattern="${formatTime}"/>	
</div>
	
<acme:display code="message.subject" property="${message1.subject} "/>
	
<acme:display code="message.body" property="${message1.body} "/>
	
<acme:display code="message.priority" property="${message1.priority} "/>
	
<acme:display code="message.tags" property="${message1.tags} "/>

<acme:button name="delete" code="message.delete" onclick="javascript: relativeRedir('message/actor/delete.do?messageId=${message1.id}&boxId=${box.id}');" />

<acme:button name="back" code="message.back" onclick="javascript: relativeRedir('message/actor/list.do?boxId=${box.id}');" />