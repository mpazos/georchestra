<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/styles/form.css" />" rel="stylesheet"  type="text/css" />     
    <title>Create Account Form</title>
</head>
<body>
    <div id="formsContent" style="center">
        <h2>New Account</h2>
        <form:form id="form" method="post" modelAttribute="accountFormBean" cssClass="cleanform">

            <div class="header">
                <c:if test="${not empty message}">
                    <div id="message" class="success">${message}</div>  
                </c:if>
                <s:bind path="*">
                    <c:if test="${status.error}">
                        <div id="message" class="error">Form has errors</div>
                    </c:if>
                </s:bind>
            </div>
			<fieldset>

				<p>
					<form:label path="firstName">First Name *</form:label>
					<form:input path="firstName" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="firstName" cssClass="error" />
				</p>

                <p>
                    <form:label path="surname">Surname Name *</form:label>
                    <form:input path="surname" size="30" maxlength="80"/>
                </p>
                <p>
                    <form:errors path="surname" cssClass="error" />
                </p>

				<p>
					<form:label path="email">e-mail *</form:label>
					<form:input path="email" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="email" cssClass="error" />
				</p>

				<p>
					<form:label path="phone">Phone</form:label>
					<form:input path="phone" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="phone" cssClass="error" />
				</p>
				
				<p>
					<form:label path="org">Organization </form:label>
					<form:input path="org" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="org" cssClass="error" />
				</p>


				<p>
					<form:label path="details"> Details </form:label>
					<form:textarea path="details" rows="3" cols="30" />
				</p>

			</fieldset>

			<fieldset>
				<p>
					<form:label path="password">Password *</form:label>
					<form:password path="password" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="password" cssClass="error" />
				</p>

				<p>
					<form:label path="confirmPassword">Confirm password *</form:label>
					<form:password path="confirmPassword" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="confirmPassword" cssClass="error" />
				</p>

			</fieldset>

			<fieldset>
			
			     <p>
					<%
					 ReCaptcha c = ReCaptchaFactory.newReCaptcha("6Lf0h-MSAAAAAOQ4YyRtbCNccU87dlGmokmelZjh", "6Lf0h-MSAAAAAI2nHJfNPDaEXXjsdmn8eKSZUrQZ", false);
					 out.print(c.createRecaptchaHtml(null, null));
					%>
			     </p>
                <p>
                    <form:errors path="recaptcha_response_field" cssClass="error" />
                </p>
			</fieldset>

			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
    </div>
</body>
</html>