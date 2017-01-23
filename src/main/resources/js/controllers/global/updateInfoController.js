    
    angular
        .module( "sms" )
        .controller( "updateInfoCrtl", updateInfoCtrl );
        
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService ) {
        var uic = this;

          // bindables
            // functions
        uic.toast = toast;
        uic.cancel = cancel;
        uic.submit = submit;

          // functions
            // for notifications
        function toast( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        };
        
            // when user decides to cancel password update
        function cancel() {
            $mdDialog.cancel();
        }
        
            // when user submits updated password
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
            
            if(newPassH == confirmPassH){
                  // new passwords match
                if(oldPassH != newPassH){
                      // old and new passwords are different
                    
                    uic.user = loginService.getUser();
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
                    
                    uic.user = "";
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
    };