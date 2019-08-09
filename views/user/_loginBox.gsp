<div class="left">
    <h1>Need an account?</h1>
    <p class="legend">
        <g:link controller="user" action="register">
            Sign up now
        </g:link>
        to start your own personal Music collection!
    </p>
    <g:link controller="user" action="register" class="btn">Sign up now</g:link>
</div>
<div class="right" id="loginBox">
    <h1>Already a member?</h1>
    <g:render template="/user/loginForm"/>
</div>