
	var sms = angular.module("sms");
	sms.controller( "deleteAssoCtrl", function( $scope, $mdDialog, $timeout, $q, $log, userService ) {
		
		var dac = this;
		
		dac.simulateQuery = false;
	    dac.isDisabled    = false;
	    
	 // list of `user` value/display objects
	    dac.users        = loadAll();
	    dac.querySearch   = querySearch;
	    dac.selectedItemChange = selectedItemChange;
	    dac.searchTextChange   = searchTextChange;
	    dac.getUsers = getUsers;
	    
	    dac.newUser = newUser;

	    function newUser(user) {
	        alert("Sorry! You'll need to create a user for " + state + " first!");
	      }
	    
	    // ******************************
	    // Internal methods
	    // ******************************

	    /**
	     * Search for users... use $timeout to simulate
	     * remote dataservice call.
	     */
	    function querySearch (query) {
	        var results = query ? dac.users.filter( createFilterFor(query) ) : dac.users,
	            deferred;
	        if (dac.simulateQuery) {
	          deferred = $q.defer();
	          $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
	          return deferred.promise;
	        } else {
	          return results;
	        }
	      }
	    
	    function searchTextChange(text) {
	        $log.info('Text changed to ' + text);
	      }
	    
	    function selectedItemChange(item) {
	        $log.info('Item changed to ' + JSON.stringify(item));
	      }
	    
	    /**
	     * Build `users` list of key/value pairs
	     */
	    function loadAll() {
	    	//get all users function **** need to be done
	    	 var allUsers = ;
	    	 return allUsers.split(/, +/g).map( function (user) {
	    	        return {
	    	          value: user.toLowerCase(),
	    	          display: user
	    	        };
	    	      });
	    }
	    
	    /**
	     * Create filter function for a query string
	     */
	    function createFilterFor(query) {
	      var lowercaseQuery = angular.lowercase(query);

	      return function filterFn(user) {
	        return (user.value.indexOf(lowercaseQuery) === 0);
	      };

	    }
	    
	    function getUsers( success ) {
            userService.getAll( function(response) {
            	dac.users = response;
            }, function(error) {
                dac.toast("Error retrieving all users.");
            });
        }
	    
		dac.cancel = function() {
			$mdDialog.cancel();
		}
		
	});
	