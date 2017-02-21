
    angular
        .module( "sms" )
        .controller( "templateCtrl", templateCtrl );
<<<<<<< HEAD

=======
    /**@description AngularJs controller for the Staging Manager System application. This is one of the
     * parent controllers with functionality that is available regardless of the user who is
     * logged in.
     */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
    function templateCtrl( $scope, $state, $mdDialog, loginService ) {
        var tc = this;

          // bindables
            // data
<<<<<<< HEAD
        tc.user = loginService.getUser();

            // functions
        tc.checkPass = checkPass;
       
        tc.toast = toast;
        tc.logout = logout;
        tc.settings = settings;
=======
            /**@prop {object} user Currently logged in user. */
        tc.user = loginService.getUser();
        tc.associateTableIsOpen = false;
        tc.viewLabel = "Associate information";

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
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238

          // initialization
        tc.checkPass();

          // functions
            // pops up dialog if password is default username
<<<<<<< HEAD
=======
            /**
             * @description Checks to see if the password needs to be changed (in the case of a user first logging in)
             * and forces the user to change the password if they need to.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function checkPass() {
            loginService.checkPass( tc.user.username, function(response) {
                if ( response.update == true ) {
                    tc.settings( response.update );
                }
            }, function( error ) {
                tc.toast( "Could not check if password needs to be updated." );
            });
        }
        
<<<<<<< HEAD
            // calls toast function in rootController
=======
            /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }

<<<<<<< HEAD
            // logs out
=======
          
            /**
             * @description Logs the user out.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function logout() {
            loginService.logout();
            tc.toast("Logged out.");
            $state.go("login");
        }

            // opens dialog to allow changing of password
<<<<<<< HEAD
=======
            /**
             * @description Opens up the dialog popup to allow the changing of the password.
             * @param {boolean} needChange Value that determines if this password change is forced or not, which
             * limits the options displayed in the view.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
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
<<<<<<< HEAD
            }, function() {
                tc.toast( "Password change cancelled." );
=======
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
            });
        }

          // listeners
            // changes title and actions based on recieved data
<<<<<<< HEAD
=======
            /**
             * @description Listenr that updates the title of the page and the actions
             * based on data given from the child controllers.
             * @param {array} events The events sent to this controller
             * @param {array} data The data sent to this controller.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        $scope.$on( "setToolbar", function( events, data ) {
            tc.title = data.title;
            tc.actions = data.actions;
        })
<<<<<<< HEAD
=======
        
        function changeView() {
        	associateTableIsOpen = tc.associateTableIsOpen;
        	if(associateTableIsOpen){
        		$scope.$broadcast( "setView", { 
                    title: "Associate Information", 
                    associateTableIsOpen }
                );
        		tc.viewLabel = "Weekly attendance";
        	}
        	else{
        		$scope.$broadcast( "setView", { 
                    title: "Weekly attendance", 
                    associateTableIsOpen }
                );
        		tc.viewLabel = "Associate information";
        	}
        }
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238

    }