<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/styles/form.css" />" rel="stylesheet"  type="text/css" />     
    <title>User Details</title>
</head>
<body>
    <div id="formsContent" style="center">
        <h2>User Details</h2>
        <form:form id="form" method="post" modelAttribute="editUserDetailsFormBean" cssClass="cleanform">

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
					<form:label path="surname">Surname </form:label>
					<form:input path="surname" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="surname" cssClass="error" />
				</p>

				<p>
					<form:label path="givenName"> Given Name </form:label>
					<form:input path="givenName" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="givenName" cssClass="error" />
				</p>

                <p>
                    <form:label path="org">Organization </form:label>
                    <form:input path="org" size="30" maxlength="80"/>
                </p>
                <p>
                    <form:errors path="org" cssClass="error" />
                </p>

				<p>
					<form:label path="title" >Title</form:label>
					<form:input path="title" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="title" cssClass="error" />
				</p>
				
			</fieldset>
			
			<fieldset>
			    <legend>Address</legend>
                <p>
                    <form:label path="postOfficeBox">Post Office Box</form:label>
                    <form:input path="postOfficeBox" size="30" maxlength="80"/>
                </p>
                <p>
                    <form:errors path="postOfficeBox" cssClass="error" />
                </p>

                <p>
                    <form:label path="postalAddress">Postal Address</form:label>
                    <form:textarea path="postalAddress" rows="4" cols="30"/>
                </p>
                <p>
                    <form:errors path="postalAddress" cssClass="error" />
                </p>

                <p>
                    <form:label path="postalCode">Postcode</form:label>
                    <form:input path="postalCode" size="30" maxlength="80"/>
                </p>
                <p>
                    <form:errors path="postalCode" cssClass="error" />
                </p>
                
                <p>
                    <form:label path="registeredAddress">Postal Address</form:label>
                    <form:textarea path="registeredAddress" rows="4" cols="30"/>
                </p>
                <p>
                    <form:errors path="registeredAddress" cssClass="error" />
                </p>

                <p>
                    <form:label path="physicalDeliveryOfficeName">Postcode</form:label>
                    <form:input path="physicalDeliveryOfficeName" size="30" maxlength="80"/>
                </p>
                <p>
                    <form:errors path="physicalDeliveryOfficeName" cssClass="error" />
                </p>
                
                <a href="<c:url value="/public"/>">Change Password</a>
                
            </fieldset>

			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
    </div>
</body>
</html>