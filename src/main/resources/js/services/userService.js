
    angular
        .module( "sms" )
        .service( "userService", userService );
        /**
         * @description AngularJs service for users.
         */
    function userService( $resource, loginService ) {
        var us = this;
        /**@var {object} Resoruce object for connecting to the REST service */
        us.userResource = $resource("api/v1/user/:username", 
            { id: "@username" }, 
            { 
                save  : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "PUT", url: "api/v1/user" }, 
                query : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, isArray: true }, 
                get   : { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } }, 
                update: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() }, method: "POST", url: "api/v1/user" },
                remove: { headers: { "Content-Type": "application/json", "Authorization": loginService.getToken() } } 
            } 
        )
       /**
         * @description Creates user in the database.
         * @param {object} user The user to create.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        us.create = function(user, success, error) {
        	us.userResource.save(user, success, error);
        }
        /**
         * @description Gets all users from the database.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        us.getAll = function(success, error) {
            us.userResource.query(success, error);
        }
        /**
         * @description Gets a single user from the database.
         * @param {string} username The username of the user to get the information of
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        us.retrieve = function(username, success, error) {
            userResource.get({username: username}, success, error);
        }
        /**
         * @description Updates user in the database.
         * @param {object} user The user to update.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        us.update = function(user, success, error) {
            us.userResource.update(user, success, error);
        }
        /**
         * @description Removes user from the database.
         * @param {object} user The user to delete.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        us.remove = function(user, success, error) {
            user.$remove(success, error);
        }
    }