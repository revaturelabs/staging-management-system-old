
    var sms = angular.module( "sms" );

    sms.service( "loginService", function( $resource ){
        var ls = this;
        
        
        
        ls.checkPass = function(username, success,error){
        	//console.log(ls.getToken());
        	
        	loginResource2 = $resource("api/v1/login/:username", 
                    { username: "@username" }, 
                    { 
                        save: { url: "api/v1/login", method:"POST"}, 
                        cookie: { headers: { "Content-Type": "application/json", "Authorization": ls.token }, method: "GET" }, 
                        checkPass: { headers:{"Content-Type": "application/json", "Authorization": ls.getToken() }, method:"GET",  url:"/api/v1/login/checkpass?username=:username" }
                    }
                );
        	
        	loginResource2.checkPass({username: username}, success,error);
        
        	//console.log("testcheckpass");
        	};
        
        ls.login = function( loginCred, success, error ) {
            ls.loginResource.save( loginCred, success, error );
        };
        
        
        ls.cookieLogin = function( username, success, error ) {
            ls.loginResource.cookie( username, success, error );
        }

          // COOKIES BAD, MKAY
        ls.logout = function() {
            ls.user = {};
            ls.token = "";
        };

        ls.user = {};
        ls.token = "";

        
        //"OBL_VEDcV5j_;0\\WBQ_JOPa]OmkRB:h]^mMTXENC"
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
       
        ls.loginResource = $resource("api/v1/login/:username", 
                { username: "@username" }, 
                { 
                    save: { url: "api/v1/login", method:"POST"}, 
                    cookie: { headers: { "Content-Type": "application/json", "Authorization": ls.token }, method: "GET" }, 
                    checkPass: { headers:{"Content-Type": "application/json", "Authorization": ls.getToken() }, method:"GET",  url:"/api/v1/login/checkpass?username=:username" }
                }
            );
        return {
            login: ls.login,
            logout: ls.logout,
            addUser: ls.addUser,
            getUser: ls.getUser,
            addToken: ls.addToken,
            getToken: ls.getToken,
            checkPass : ls.checkPass
        };
        
    });