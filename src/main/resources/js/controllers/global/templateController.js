
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );
    /**@description AngularJs controller for the Staging Manager System application. This is one of the
     * parent controllers withc funcationaliy that is available regardless of the user who is
     * logged in.
     */
    function templateCtrl( $scope, $state, $mdDialog, loginService ) {
        var tc = this;

          // bindables
            // data
            /**@prop {object} user Currently logged in user. */
        tc.user = loginService.getUser();
        tc.associateTableIsOpen = false;

            // functions
         /**@var {function} checkPass function reference variable. */   
        tc.checkPass = checkPass;
       /**@var {function} toast function reference variable. */
        tc.toast = toast;
        /**@var {function} logout function reference variable. */
        tc.logout = logout;
        /**@var {function} settings function reference variable. */
        tc.settings = settings;
        
        tc.changeView = changeView;

          // initialization
        tc.checkPass();

          // functions
            // pops up dialog if password is default username
            /**
             * @description Checks to see if the password needs to be changed (in the case of a user first logging in)
             * and forces the user to change the password if they need to.
             */
        function checkPass() {
            loginService.checkPass( tc.user.username, function(response) {
                if ( response.update == true ) {
                    tc.settings( response.update );
                }
            }, function( error ) {
                tc.toast( "Could not check if password needs to be updated." );
            });
        }
        
            /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }

          
            /**
             * @description Logs the user out.
             */
        function logout() {
            loginService.logout();
            tc.toast("Logged out.");
            $state.go("login");
        }

            // opens dialog to allow changing of password
            /**
             * @description Opens up the dialog popup to allow the changing of the password.
             * @param {boolean} needChange Value that determines if this password change is forced or not, which
             * limits the options displayed in the view.
             */
        function settings( needChange ) {
            $mdDialog.show({
                templateUrl: "html/templates/updateInformation.html",
                controller: "updateInfoCrtl",
                controllerAs: "uInfoCtrl",
                locals: { needChangePass: needChange },
                bindToController: true,
                escapeToClose: !needChange,
                clickOutsideToClose: !needChange
            }).then( function() {
                tc.toast( "Password changed successfully." );
            });
        }

          // listeners
            // changes title and actions based on recieved data
            /**
             * @description Listenr that updates the title of the page and the actions
             * based on data given from the child controllers.
             * @param {array} events The events sent to this controller
             * @param {array} data The data sent to this controller.
             */
        $scope.$on( "setToolbar", function( events, data ) {
            tc.title = data.title;
            tc.actions = data.actions;
        })
        
        function changeView() {
        	associateTableIsOpen = tc.associateTableIsOpen;
        	if(associateTableIsOpen){
        		$scope.$broadcast( "setView", { 
                    title: "Associate Information", 
                    associateTableIsOpen }
                );
        	}
        	else{
        		$scope.$broadcast( "setView", { 
                    title: "Weekly attendance", 
                    associateTableIsOpen }
                );
        	}
        }

    }