<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>${errorMsg}</div>
<form action="/internet_shop_war_exploded/login" method="post">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to sign into.</p>
        <hr>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter password" name="psw" required>

        <button type="submit" class="registerbtn">Login</button>
    </div>

    <div class="container signin">
        <p>Don't have an account? <a href="#">Sign up</a>.</p>
    </div>
</form>
</body>
</html>
