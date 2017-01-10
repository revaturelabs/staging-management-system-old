
    var sms = angular.module( "sms" );

    sms.service( "loginService", function( $resource ){
        var loginResource = $resource("api/v1/login");
        var ls = this;

        ls.login = function(loginCred, success, error) {
            loginResource.save(loginCred, success, error);
        };

          // COOKIES BAD, MKAY
        ls.logout = function() {
            ls.user = {};
            ls.token = "";
        };

        ls.user = {};
        ls.token = "";

        ls.addUser = function(user) {
            ls.user = user;
        };
        
        ls.getUser = function(user) {
            return ls.user;
        };

        ls.addToken = function(token) {
            ls.token = token;
        };

        ls.getToken = function() {
            return ls.token;
        };

        return {
            login: ls.login,
            logout: ls.logout,
            addUser: ls.addUser,
            getUser: ls.getUser,
            addToken: ls.addToken,
            getToken: ls.getToken
        };
        
    });