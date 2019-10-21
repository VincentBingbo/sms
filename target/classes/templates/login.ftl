<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="../static/css/bootstrap.css">
    <script src="../static/js/jquery-3.3.1.min.js"></script>
    <script src="../static/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<form action="/toLogin" method="post">
    <div class="input-group">
        <span class="glyphicon glyphicon-user"></span>
        <input type="text" id="userId" class="form-control"/>
    </div>
    <div class="input-group">
        <span class="glyphicon glyphicon-lock"></span>
        <input type="password" id="userPwd" class="form-control"/>
    </div>
    <div>
        <input type="submit" value="登陆" class="btn btn-default">
    </div>
</form>
</body>
</html>