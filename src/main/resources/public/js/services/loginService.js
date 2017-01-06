
    var sms = angular.module( "sms" );

    sms.service( "loginService", function($resource){
        var loginResource = $resource("api/v1/login/");
        (function login(loginCred, success, error) {
            loginResource.get(loginCred, success, error);
        }).bind(this);
    });