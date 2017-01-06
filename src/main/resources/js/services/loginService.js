
    var sms = angular.module( "sms" );

    sms.service( "loginService", function($resource){
        var ls = this;

        var loginResource = $resource("api/v1/login/");
        ls.login = function(loginCred, success, error) {
            loginResource.save(loginCred, success, error);
        };
    });