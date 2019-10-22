<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>
<body>
<h1>This is Admin!</h1>

<@shiro.hasRole name = "admin">
    <p>I am Admin!</p>
</@shiro.hasRole>

</body>
</html>