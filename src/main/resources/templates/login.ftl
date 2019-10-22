<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登陆</title>
    <link href="/images/ico/favicon-20180910095926548.ico" rel="shortcut icon" />
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap/bootstrap.js"></script>
    <script src="/js/vue.min.js"></script>
    <script src="/js/sms/login.js"></script>
    <style>
        .container {
            margin-top: 120px;
            padding-bottom: 20px;
        }
        .mylogin {
            width: 350px;
            padding: 20px;
            padding-top: 5px;
            margin: 0 auto;
            border: 1px solid #eee;
        }
        h3 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body style="background: url('/images/timg.jpg')">
<div class="container" id="sms">
    <form action="/toLogin" method="post" class="mylogin">
        <h3 class="text-center">用户登录</h3>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-user"></span>
                </div>
                <input type="text" name="userId" class="form-control" required>
            </div>
        </div>
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">
                    <span class="glyphicon glyphicon-lock"></span>
                </div>
                <input type="password" name="userPwd" class="form-control" required>
            </div>
        </div>
        <input type="submit" class="btn btn-primary form-control">
    </form>
</div>
</body>
</html>