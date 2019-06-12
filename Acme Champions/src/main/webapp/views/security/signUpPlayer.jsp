<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="register/savePlayer.do" modelAttribute="player">
	
	
	<acme:textbox code="actor.name" path="name" obligatory="true"/>
	
	<acme:textbox code="actor.surnames" path="surnames" obligatory="true"/>
	
	<acme:textbox code="actor.photo" path="photo" size="100"/>
	
	<acme:textbox code="actor.email" path="email" obligatory="true" size="35" placeholder="_@_._ / _<_@_._>" pattern="^[\\w]+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+>$"/>
	
	<acme:textbox code="actor.address" path="address" />
	
	<acme:textbox code="actor.phone" path="phone" id="phone" onblur="javascript: checkPhone();"/> 
	
	<acme:textbox code="player.squadNumber" path="squadNumber" obligatory="true"/>
	
	<acme:textbox code="player.squadName" path="squadName" obligatory="true"/>
	
	<jstl:if test="${language == 'es'}">
		<form:label path="position">
			<spring:message code="player.position"/>
		</form:label>
		<form:select path="position" >
			<form:option label="Portero" value="PORTERO"/>
			<form:option label="Defensa" value="DEFENSA"/>
			<form:option label="Centrocampista" value="CENTROCAMPISTA"/>
			<form:option label="Delantero" value="DELANTERO"/>
		</form:select>
		<form:errors cssClass="error" path="position"></form:errors>
		<br />	
	</jstl:if>
	
	<jstl:if test="${language == 'en'}">
		<form:label path="position">
			<spring:message code="player.position"></spring:message>
		</form:label>
		<form:select path="position" >
			<form:option label="Goalkeeper" value="GOALKEEPER"/>
			<form:option label="Defender" value="DEFENDER"/>
			<form:option label="Midfielder" value="MIDFIELDER"/>
			<form:option label="Striker" value="STRIKER"/>
		</form:select>
		<form:errors cssClass="error" path="position"></form:errors>
		<br />
	</jstl:if>
	
	
	<acme:textbox code="actor.username" path="username" obligatory="true"/>
	
	<acme:password code="actor.password" path="password" obligatory="true"/>
	
	<acme:password code="actor.confirmPassword" path="confirmPassword" obligatory="true"/>
	
	<acme:checkbox path="checkbox" code1="actor.checkBox1" code2="actor.checkBox2" href="termsAndConditions/show.do" />
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	
	<acme:cancel code="actor.cancel" url="welcome/index.do" />
	
</form:form>
 
<script type="text/javascript">
	function checkPhone() {
		var target = document.getElementById("phone");
		var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4}$)");

		if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == false) {
			if (confirm('<spring:message code="actor.phone.wrong" />') == false) {
				return true;
			
			}
		} else if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == true) {
			target.value = '${defaultCountry}' + " " + input;
		}
	}
</script>