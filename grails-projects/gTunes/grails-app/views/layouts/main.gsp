<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<%/*
<nav class="navbar navbar-expand-lg navbar-dark navbar-static-top" role="navigation">
    <a class="navbar-brand" href="/#"><asset:image src="grails.svg" alt="Grails Logo"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" aria-expanded="false" style="height: 0.8px;" id="navbarContent">
        <ul class="nav navbar-nav ml-auto">
            <g:pageProperty name="page.nav"/>
        </ul>
    </div>

</nav>
*/
%>

<g:layoutBody/>

<%
/*
<div class="footer row" role="contentinfo">
    <div class="col">
        <a href="http://guides.grails.org" target="_blank">
            <asset:image src="advancedgrails.svg" alt="Grails Guides" class="float-left"/>
        </a>
        <strong class="centered"><a href="http://guides.grails.org" target="_blank">Grails Guides</a></strong>
        <p>Building your first Grails app? Looking to add security, or create a Single-Page-App? Check out the <a href="http://guides.grails.org" target="_blank">Grails Guides</a> for step-by-step tutorials.</p>

    </div>
    <div class="col">
        <a href="http://docs.grails.org" target="_blank">
            <asset:image src="documentation.svg" alt="Grails Documentation" class="float-left"/>
        </a>
        <strong class="centered"><a href="http://docs.grails.org" target="_blank">Documentation</a></strong>
        <p>Ready to dig in? You can find in-depth documentation for all the features of Grails in the <a href="http://docs.grails.org" target="_blank">User Guide</a>.</p>

    </div>

    <div class="col">
        <a href="https://grails-slack.cfapps.io" target="_blank">
            <asset:image src="slack.svg" alt="Grails Slack" class="float-left"/>
        </a>
        <strong class="centered"><a href="https://grails-slack.cfapps.io" target="_blank">Join the Community</a></strong>
        <p>Get feedback and share your experience with other Grails developers in the community <a href="https://grails-slack.cfapps.io" target="_blank">Slack channel</a>.</p>
    </div>
</div>
*/
%>

<div id="spinner" class="spinner" style="display:none;">
    <g:message code="spinner.alt" default="Loading&hellip;"/>
</div>
<div id="loginBox" class="loginBox">
    <g:if test="${session?.user}">
        <div style="margin-top:20px">
            <div style="float:right;">
                <a href="#">Profile</a> | <g:link controller="user"
                action="logout">Logout</g:link><br>
            </div>
            Welcome back
            <span id="userFirstName">
                ${session?.user?.firstName}!
            </span><br><br>
            You have purchased (${session.user.purchasedSongs?.size() ?: 0}) songs.<br>
        </div>
    </g:if>
    <g:else>
        <g:form
            name="loginForm"
            url="[controller:'user',action:'login']">
            <div>Username:</div>
            <g:textField
                name="login"
                value="${fieldValue(bean:loginCmd, field:'login')}">
            </g:textField>
            <div>Password:</div>
            <g:passwordField
                name="password">
            </g:passwordField>
            <br/>
            <input type="image"
                src="${createLinkTo(dir:'images', file:'login-button.gif')}"
                name="loginButton" id="loginButton" border="0"></input>
        </g:form>
        <g:renderErrors bean="${loginCmd}"></g:renderErrors>
    </g:else>
</div>
<div id="navPane">
    <g:if test="${session.user}">
        <ul>
            <li>
                <g:link
                    controller="user"
                    action="music">My Music
                </g:link>
            </li>
            <li>
                <g:link
                    controller="store"
                    action="shop">The Store
                </g:link>
            </li>
        </ul>
    </g:if>
    <g:else>
        <div id="registerPane">
            Need an account?
            <g:link
                controller="user"
                action="register">Sign up now
            </g:link>
            to start your own personal Music collection!
            </div>
    </g:else>
</div>
<asset:javascript src="application.js"/>


</body>
</html>
