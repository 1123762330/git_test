<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
</head>
<body>
<form class="form-signin" method="post" action="/pool/users/id">
    <h2 class="form-signin-heading">Please sign in</h2>
    <p>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="username" required autofocus>

    </p>
    <p>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="password" required>

    </p>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>

</body>
</html>
