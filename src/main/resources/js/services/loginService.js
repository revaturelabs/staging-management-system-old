
    var sms = angular.module( "sms" );

    sms.service( "loginService", function( $resource ){
        var ls = this;
        var loginResource = $resource("api/v1/login/:username", 
            { username: "@username" }, 
            { 
                save: { url: "api/v1/login" }, 
                cookie: { headers: { "Content-Type": "application/json", "Authorization": ls.token }, method: "GET" } 
            }
        );

        ls.login = function( loginCred, success, error ) {
            loginResource.save( loginCred, success, error );
        };
        
        ls.cookieLogin = function( username, success, error ) {
            loginResource.cookie( username, success, error );
        }

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