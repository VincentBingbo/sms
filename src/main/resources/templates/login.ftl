<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
</head>
<body>
<form action="/toLogin" method="post">
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
        <input type="text" name="userId" class="form-control"/>
    </div>
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
        <input type="password" name="userPwd" class="form-control"/>
    </div>
    <div>
        <input type="submit" value="登陆" class="btn btn-default">
        <button class="btn bg-info"><a href="/register">注册</a></button>
    </div>
</form>
</body>
</html>