var app = angular.module('myApp', []);

app.controller('loginController', function($scope, $http) {
    $scope.submit = function() {
        alert($scope.userId);
        alert($scope.userPwd);
        $http({
            method : 'POST',
            url : '/loveStudy/login/index',
            data : {
                "userId" : $scope.userId,
                "userPwd" : $scope.userPwd
            }
        }).success(function(data) {
            alert("success");
            console.log(data);
        }).error(function(data) {
            alert("error");
            console.log(data);
        });
    }
});
