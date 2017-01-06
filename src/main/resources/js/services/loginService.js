
    var sms = angular.module( "sms" );

    sms.service( "loginService", function($resource){
        var loginResource = $resource("api/v1/login");
        var ls = this;

        ls.login = function(loginCred, success, error) {
            loginResource.save(loginCred, success, error);
        };
    });