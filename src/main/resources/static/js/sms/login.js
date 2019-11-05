window.onload = function () {
    var app = new Vue({
        el: '#sms',
        data: {
            userId: $('#userId').val(),
            userPwd: $('#userPwd').val()
        },
        methods: {
            submit: function () {
                //发送post请求

                this.$http({
                    url: 'http://127.0.0.1:10088/toLogin',
                    method: 'POST',
                    // 请求体重发送的数据
                    data: {
                        userId: 'vincent',
                        userPwd: 'song123'
                    },
                    // 设置请求头
                    headers: {
                        'Content-Type': 'x-www-from-urlencoded'
                    }
                }).then(function (msg) {
                    console.log(msg.bodyText);
                }, function (error) {
                    console.log(error.bodyText);
                });
                this.$http.post(this.url, {'userId': this.userId, 'userPwd': this.userPwd}, {emulateJSON: true})
                    .then(function (value) {
                        console.log(value.toString());
                        window.location.href = "index.ftl";
                    }, function (reason) {
                        console.log(reason.toString());
                    });
            }
        }
    });
}