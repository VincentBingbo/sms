import main from './main.js'
Vue.prototype.MAIN = main

window.onload = function() {
    var app = new Vue({
        el: '#sms',
        data: {
            userId: '',
            userPwd: ''
        },
        methods: {
            submit: function () {
                //发送post请求                
                console.log("userId is " + this.userId + "; userPwd is " + this.userPwd);
                this.$http.post('http://127.0.0.1:10088/toLogin', {'userId': this.userId, 'userPwd': this.userPwd}, {emulateJSON:true})
                    .then(function (value) {
                        console.log(this.userId);
                        this.MAIN.setUserId(this.userId);//修改全局变量的值
                        console.log(this.MAIN.userId)
                        // window.location.href="index.html";//跳转页面
                        this.$router.push({
                            name: 'index.html',
                            query: {userId: this.userId}
                          })
                }, function (reason) {
                    console.log(reason.toString());
                });
            },
            key: function() {
                this.userId = this.$refs.userIdVal.value;
                this.userPwd = this.$refs.userPwdVal.value;
            }
        }
    });
}