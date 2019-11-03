window.onload = function() {
    var app = new Vue({
        el: '#sms',
        url: '/toLogin',
        data: {
            userId: $('#userId').val(),
            userPwd: $('#userPwd').val()
        },
        methods: {
            submit: function () {
                //发送post请求
                console.log(this.userId);
                this.$http.post(this.url, {'userId': this.userId, 'userPwd': this.userPwd}, {emulateJSON:true}).then(function (value) {
                    window.location.href="/index.ftl";
                }).catch(function (reason) {
                    console.log(reason.toString());
                })
                // axios.post(this.url, {'userId': this.userId, 'userPwd': this.userPwd}, {
                //     headers: {
                //         'Content-Type': 'application/json'
                //     }
                // }).then(function (value) {
                //     console.log(value);
                // }).catch(function (reason) {
                //     console.log(reason);
                // });
            }
        }
    });
}