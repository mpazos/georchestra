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
					<form:label path="name"> User Name *</form:label>
					<form:input path="name" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="name" cssClass="error" />
				</p>

				<p>
					<form:label path="email"> e-mail *</form:label>
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
					<form:label path="geographicArea">Geographic Area </form:label>
					<form:input path="geographicArea" size="30" maxlength="80"/>
				</p>
				<p>
					<form:errors path="geographicArea" cssClass="error" />
				</p>

				<p>
					<form:label path="details"> Details </form:label>
					<form:textarea path="details" rows="3" cols="30" />
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

			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
    </div>
</body>
</html>