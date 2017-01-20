
    var sms = angular.module( "sms" );

    sms.service( "loginService", function( $resource, $cookies ) {
        var ls = this;

        var loginResource = $resource("api/v1/login", 
            {},
            { 
                save  : { 
                    method: "POST",
                    url: "api/v1/login" }, 
                cookie: { 
                    method: "POST",
                    url: "api/v1/login/cookieLogin",
                    headers: { 
                        "Content-Type": "application/json", 
                        "Authorization": function() { return ls.token; }
                    } 
                },
                checkPass: { 
                  	method: "POST",  
                	url: "/api/v1/login/checkpass",
                	headers: {
                		"Content-Type": "application/json", 
                		"Authorization": function(){ return ls.token; }
                	}
                },
                changePass: {
                	method:"PUT",
                	url:"/api/v1/login",
                	headers:{
                		"Content-Type":"application/json",
                		"Authorization": function(){ return ls.token; }
                	}
                }
            }
        );
        ls.changePass = function(data,success,error){
        	loginResource.changePass(data,success,error);
        }

        ls.checkPass = function(username, success,error){
        	loginResource.checkPass(username, success,error);
        };
        
        ls.login = function( loginCred, success, error ) {
            loginResource.save( loginCred, success, error );
        };
        
        ls.cookieLogin = function( username, success, error ) {
            loginResource.cookie( username, success, error );
            // username.$cookie( success, error );
        }

          // COOKIES BAD, MKAY
        ls.logout = function() {
            ls.user = {};
            ls.token = "";
            $cookies.remove("RevatureSMSUseraname");
            $cookies.remove("RevatureSMSToken");
        };

        ls.user = {};
        ls.token = "";

        ls.addUser = function(user) {
        	console.log(user);
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

            login       : ls.login,
            cookieLogin : ls.cookieLogin,
            logout      : ls.logout,
            addUser     : ls.addUser,
            getUser     : ls.getUser,
            addToken    : ls.addToken,
            getToken    : ls.getToken,
            checkPass : ls.checkPass,
            changePass : ls.changePass
        };
        
    });