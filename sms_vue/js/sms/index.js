import main from './main.js'

Vue.prototype.MAIN = main

window.onload = function() {

    var vm = new Vue({
        el: '#index',
        data:  {
            userId: '',
            mp3: ''
        },
        created: function() {
            this.mp3 = 'mp3\\' + this.MAIN.userId + '.mp3';
            this.userId = this.MAIN.userId;
            console.log(this.mp3)
            console.log(this.MAIN.userId);
        },
        methods: {
            
        },
    });
}