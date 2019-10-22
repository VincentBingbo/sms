<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<h1>This is User!</h1>
<@shiro.hasRole name = "user">
    <p>I am User!</p>
</@shiro.hasRole>

<@shiro.hasPermission name = "select">
    <p>I have select permission!</p>
</@shiro.hasPermission>
</body>
</html>