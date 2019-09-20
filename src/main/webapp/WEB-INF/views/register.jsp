<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="/internet_shop_war_exploded/registration" method="post">
    <div class="container">
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter name" name="name" required>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter password" name="psw" required>

        <label for="psw-repeat"><b>Repeat password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
        <hr>

        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit" class="registerbtn">Register</button>
    </div>

    <div class="container signin">
        <p>Already have an account? <a href="#">Sign in</a>.</p>
    </div>
</form>
</body>
</html>
