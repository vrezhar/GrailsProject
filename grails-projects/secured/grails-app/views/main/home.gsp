<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:38
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
    </title>

    <div style = "alignment: center" >
        <h1>Welcome, ${user}!</h1>



    </div>


</head>

<body>


    <div class = "navbar">
        <sec:ifAllGranted roles="ROLE_ADMIN" >
            <g:link controller="main" action="list">
                List of all users
            </g:link>
        </sec:ifAllGranted>
        <sec:ifLoggedIn>
            <g:link controller="logout" >
                Logout
            </g:link>
        </sec:ifLoggedIn>
    </div>

</body>

</html>