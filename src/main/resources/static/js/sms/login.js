var vm = new Vue({
    el: '#sms',
    data: {
        userId: "用户名",
        userPwd: "密码",
        alexa: "10000"
    },
    methods: {
        details: function() {
            return  this.site + " - 学的不仅是技术，更是梦想！";
        }
    }
})