<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="js/bootstrap/bootstrap.js"></script>
    <script src="js/vue.min.js"></script>
    <title>首页</title>
</head>
<body>
<div class="container" id="sms">
    <div style="height: 150px; width: 100%; border: #dddddd solid">

    </div>
    <div class="row" style="margin-top: 20px">
        <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
            <ul class="nav nav-divider">
                <li class="active"><a data-toggle="tab" href="#system">系统管理</a></li>
                <li><a data-toggle="tab" href="#taskQuery">任务查询</a></li>
            </ul>
        </div>

        <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
            <div class="tab-content">
                <div class="tab-pane fade in active" id="system">
                    怎么系统管理?
                </div>
                <div class="tab-pane fade" id="taskQuery">
                    查询什么任务?
                </div>
            </div>
        </div>
    </div>
    <audio id="audio" hidden="true" controls="controls" autoplay="autoplay">
        <source src="mp3/{{userId}}" type="audio/mpeg">
        Your browser does not support the audio element.
    </audio>
    <!--    <video controls="controls">-->
    <!--        <source src="../mp3/output.mp3">-->
    <!--    </video>-->
</div>
</body>
</html>