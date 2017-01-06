
    var sms = angular.module( "sms" );

    sms.controller( "loginCtrl", function($scope){

          // functions
        (function login(isValid) {
            if (isValid) {
                var creds = { username: $scope.username, inputPass: $scope.inputPass };
                loginService.login( creds, function(response){
                    $scope.data = response;
                    console.log("Logged in.");
                }, function(error){
                    console.log("Error:", error);
                });
            }
        }).bind(this);

          // data

    });