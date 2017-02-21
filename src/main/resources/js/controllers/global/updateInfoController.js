    
    angular
        .module( "sms" )
        .controller( "updateInfoCrtl", updateInfoCtrl );
        
<<<<<<< HEAD
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService ) {
        var uic = this;

          // bindables
            // functions
        uic.toast = toast;
        uic.cancel = cancel;
        uic.submit = submit;

          // functions
            // for notifications
=======
        /**
         * @description AngularJS controller for updating a password (and eventually other info)
         */
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService, userService ) {
        var uic = this;

        
          // bindables
        uic.user = loginService.getUser();
        uic.currentSkills = uic.user.skill;
        
        // functions
        /**@var {function} toast function reference variable. */
        uic.toast = toast;
        /**@var {function} cancel function reference variable. */
        uic.cancel = cancel;
        /**@var {function} submit function reference variable. */
        uic.submit = submit;
        
          // initializations
        
          // functions
             /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function toast( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        }
        
<<<<<<< HEAD
            // when user decides to cancel password update
=======

        /**
         * @description Cancel the currently opened dialog window.
         */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function cancel() {
            $mdDialog.cancel();
        }
        
<<<<<<< HEAD
            // when user submits updated password
=======
           
            /**
             * @description Submit the changed password to update the users password.
             */
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
        function submit() {
              //check for empty passwords
            if(oldPass.value === ""){
                uic.toast("Enter your password.");
                return;
            }
            
            if(newPass.value === ""){
                uic.toast("Enter a new password.");
                return;
            }
            
            if(confirmPass.value === ""){
                uic.toast("Confirm your new password.");
                return;
            }

            if(loginService.getUser().username === newPass.value){
                uic.toast("Your password cannot be your username.");
                return;
            }
              // hash passwords
            var oldPassH = CryptoJS.SHA1(oldPass.value).toString();
            var newPassH = CryptoJS.SHA1(newPass.value).toString();
            var confirmPassH = CryptoJS.SHA1(confirmPass.value).toString();
<<<<<<< HEAD
            
            if(newPassH == confirmPassH){
=======
                        if(newPassH == confirmPassH){
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
                  // new passwords match
                if(oldPassH != newPassH){
                      // old and new passwords are different
                    
<<<<<<< HEAD
                    uic.user = loginService.getUser();
=======
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
                    uic.token = loginService.getToken();
                    var data={"username": uic.user.username, 
                        "oldPassword": oldPassH, 
                        "newPassword":newPassH};
                    
                    loginService.changePass(data,
                            function(){
                                  // password changed successfully
                                $mdDialog.hide();
                            },function(response){
                                  // password change went wrong
                                uic.toast(response.data.errorMessage);
                            });
<<<<<<< HEAD
                    
                    uic.user = "";
=======
 
                    
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
                    uic.token = "";
                    
                }else{
                      // old password and new password are the same
                    uic.toast("Old password and new password are the same.");
                }
            }
            else{
                  // passwords don't match
                uic.toast("Password confirmation does not match.");
            }
        }
<<<<<<< HEAD
    }
=======
    }
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
