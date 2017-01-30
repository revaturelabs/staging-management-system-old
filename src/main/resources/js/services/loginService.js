
    angular
        .module( "sms" )
        .service( "loginService", loginService );
    /**
     * @description AngularJs service for login
     */
    function loginService( $resource, $state, $cookies ) {
        var ls = this;
        /**@var {object} Resoruce object for connecting to the REST service */
        var loginResource = $resource("api/v1/login", 
            {},
            { 
                save      : { 
                    method: "POST",
                    url: "api/v1/login" }, 
                cookie    : { 
                    method: "POST",
                    url: "api/v1/login/cookieLogin",
                    headers: { 
                        "Content-Type": "application/json", 
                        "Authorization": function() { return ls.token; }
                    } 
                },
                checkPass : { 
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
        )

        /**
         * @description Changes the password in the database.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        ls.changePass = function(data,success,error){
        	loginResource.changePass(data,success,error);
        }
        /**
         * @description Checks to see if the password needs to be changed
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        ls.checkPass = function(username, success,error){
        	loginResource.checkPass(username, success,error);
        }
        /**
         * @description Sends the login credentials to the server
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        ls.login = function( loginCred, success, error ) {
            loginResource.save( loginCred, success, error );
        }
        /**
         * @description Checks the cookies and sends the login credentials to the server.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        ls.cookieLogin = function( username, success, error ) {
            loginResource.cookie( username, success, error );
        }

        /**
         * @description Logs the user out, removing the cookies from the system.
         */
        ls.logout = function() {
            ls.user = {};
            ls.token = "";
            $cookies.remove("RevatureSMSUseraname");
            $cookies.remove("RevatureSMSToken");
        }
        /**
         * Initial blanking of the user object and token in the server.
         */
        ls.user = {};
        ls.token = "";

        ls.addUser = function(user) {
            ls.user = user;
        }
        /**
         * @description Gets the currently logged in user, or reroutes to login if the user isn't logged in.
         */
        ls.getUser = function() {
        	if (ls.user.username == undefined) {
        		// THIS IS BAD, BUT IT WORKS. FOR NOW.
				$state.go("login");
			}
        	else{
        		return ls.user;
        	}
        }
        /**
         * @description Adds the token while logging in.
         */
        ls.addToken = function(token) {
            ls.token = token;
        }
        /**
         * @description Gets the current login token
         */
        ls.getToken = function() {
        	if(ls.token){
        		return ls.token;
        	}
        	else{
        		var cookieToken = $cookies.get("RevatureSMSToken");
        		if (cookieToken) {
        			return cookieToken;
        		}
        	}
        }
        /**
         * Retruns the service object containing all of the above functions.
         */
        return {
            login       : ls.login,
            cookieLogin : ls.cookieLogin,
            checkPass   : ls.checkPass,
            changePass  : ls.changePass,
            logout      : ls.logout,
            addUser     : ls.addUser,
            getUser     : ls.getUser,
            addToken    : ls.addToken,
            getToken    : ls.getToken
        }
    }