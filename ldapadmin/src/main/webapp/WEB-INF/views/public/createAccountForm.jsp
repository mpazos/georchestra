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
    <title>Create Account Form</title>
</head>
<body>
    <div id="formsContent" style="center">
        <h2>New Account</h2>
        <form:form id="form" method="post" modelAttribute="accountFormBean" cssClass="cleanform">

            <fieldset>

				<p>
					<form:label path="name">
	                    User Name <form:errors path="name" cssClass="error" />
					</form:label>
					<form:input path="name" />
				</p>

				<p>
					<form:label path="email">
	                    e-mail <form:errors path="email" cssClass="error" />
					</form:label>
					<form:input path="email" />
				</p>

				<p>
					<form:label path="phone">
					   Phone <form:errors path="phone" cssClass="error" />
					</form:label>
					<form:input path="phone" />
				</p>

				<p>
					<form:label path="org">
	                    Organization <form:errors path="org" cssClass="error" />
					</form:label>
					<form:input path="org" />
				</p>

				<p>
					<form:label path="geographicArea">
	                    Geographic Area <form:errors path="geographicArea" cssClass="error" />
					</form:label>
					<form:input path="geographicArea" />
				</p>

				<p>
					<form:label path="details">
	                    Details <form:errors path="details" cssClass="error" />
					</form:label>
					<form:textarea path="details" />
				</p>

				<p>
					<form:label path="role">
	                    Role
	                </form:label>
					<form:select path="role">
						<form:option value="admin">Admin</form:option>
						<form:option value="user">User</form:option>
						<form:option value="reader">Reader</form:option>
					</form:select>

				</p>
			</fieldset>

			<fieldset>
				<p>
					<form:label path="password">
                    Password <form:errors path="password" cssClass="error" />
					</form:label>
					<form:password path="password" />
				</p>

				<p>
					<form:label path="confirmPassword">
                    Confirm password <form:errors path="confirmPassword" cssClass="error" />
					</form:label>
					<form:password path="confirmPassword" />
				</p>
			</fieldset>

			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
    </div>
</body>
</html>