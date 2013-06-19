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
<title>Insert title here</title>
</head>
<body>
    <div id="formsContent">
        <h2>New Account</h2>
        <p>
            See the <code>org.springframework.samples.mvc.form</code> package for the @Controller code  
        </p>
        <form:form id="form" method="post" modelAttribute="accountFormBean" cssClass="cleanform">
            <fieldset>
                <legend>User Info</legend>
                <form:label path="name">
                    Name <form:errors path="name" cssClass="error" />
                </form:label>
                <form:input path="name" />
    
                <form:label path="email">
                    e-mail <form:errors path="email" cssClass="error" />
                </form:label>
                <form:input path="email" />
                
                 
                <form:label path="phone">
                    Phone (in form (###) ###-####) <form:errors path="phone" cssClass="error" />
                </form:label>
                <form:input path="phone" />
    
            </fieldset>
    
            <fieldset>
                <legend>Role</legend>
                <form:label path="role">
                    Type (select one)
                </form:label>
                <form:select path="role">
                    <form:option value="admin">Admin</form:option>
                    <form:option value="user">User</form:option>
                    <form:option value="reader">Reader</form:option>
                </form:select>
                
            </fieldset>
    
            <p><button type="submit">Submit</button></p>
        </form:form>
<!--         
        <script type="text/javascript">
            $(document).ready(function() {
                $("#form").submit(function() {  
                    $.post($(this).attr("action"), $(this).serialize(), function(html) {
                        $("#formsContent").replaceWith(html);
                        $('html, body').animate({ scrollTop: $("#message").offset().top }, 500);
                    });
                    return false;  
                });         
            });
        </script>
 -->        
    </div>
</body>
</html>