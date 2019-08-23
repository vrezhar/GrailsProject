<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:38
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body id="body">
<h1>Registration</h1>
<p>Complete the form below to create an account!</p>
<g:hasErrors bean="${user}">
    <div class="errors">
        <g:renderErrors bean="${user}"></g:renderErrors>
    </div>
</g:hasErrors>
<g:form controller="user" action="register" name="registerForm">
    <div class="formField">
        <label for="username">Login:</label>
        <g:textField name="username" value="${user?.username}"></g:textField>
    </div>
    <div class="formField">
        <label for="password">Password:</label>
        <g:passwordField name="password" value="${user?.password}"></g:passwordField>
    </div>
    <div class="formField">
        <label for="confirm">Confirm Password:</label>
        <g:passwordField name="confirm" value="${params?.confirm}"></g:passwordField>
    </div>
    <div class="formField">
        <label for="firstName">First Name:</label>
        <g:textField name="firstName" value="${user?.firstName}"></g:textField>
    </div>
    <div class="formField">
        <label for="lastName">Last Name:</label>
        <g:textField name="lastName" value="${user?.lastName}"></g:textField>
    </div>
    <g:submitButton class="formButton" name="register" value="Register"></g:submitButton>
</g:form>
</body>
</html>