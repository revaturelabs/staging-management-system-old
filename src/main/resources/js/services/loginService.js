
    var sms = angular.module( "sms" );

    sms.service( "loginService", function( $resource ){
        var loginResource = $resource("api/v1/login");
        var ls = this;

        ls.login = function(loginCred, success, error) {
            loginResource.save(loginCred, success, error);
        };

        ls.logout = function() {

        };

        ls.user = {};
        ls.addUser = function(user) {
            ls.user = user;
        };
        ls.getUser = function(user) {
            return ls.user;
        };

        return {
            login: ls.login,
            logout: ls.logout,
            addUser: ls.addUser,
            getUser: ls.getUser
        };
        
    });