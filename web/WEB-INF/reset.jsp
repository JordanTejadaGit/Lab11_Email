<%-- 
    Document   : reset
    Created on : Aug 16, 2021, 4:07:15 PM
    Author     : 694952
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
     <body>
        <c:if test="${resetPassword == null || resetPassword == false}">
            <h1>Reset Password</h1>
            <h2>Please enter email address to reset your password.</h2>
            <form action="reset" method="post">
                <table>
                    <tr>
                        <td>
                            Email Address:
                        </td>
                        <td>
                            <input type="text" name="resetEmail">
                        </td>
                    <tr>
                        <td>
                            <input type="submit" value="Submit">
                <input type="hidden" name="action" value="reset">
                        </td>
                        
                    </tr>
                    </tr>
                </table>
            </form>
        </c:if>
            <c:if test="${resetPassword == true}">
                <h1>Enter New Password</h1>
            <form action="reset" method="post">
                <table>
                    <tr>
                        <td>
                            <input type="password" name="resetPassword">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Submit">
                            <input type="hidden" name="action" value="newPasswordSubmit">
                        </td>
                    </tr>
                </table>
            </form>
            </c:if>
    </body>
</html>
