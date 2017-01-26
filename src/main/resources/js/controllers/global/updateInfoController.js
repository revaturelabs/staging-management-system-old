    
    angular
        .module( "sms" )
        .controller( "updateInfoCrtl", updateInfoCtrl );
        
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService, skillService ) {
        var uic = this;

        function populateSkills(){
        	var skills = [];

        	skills.push({"ts_id": 1, "skill": "Java"});
            skills.push({"ts_id": 2, "skill": "html"});
            return skills;
        }
        
          // bindables
        uic.user = loginService.getUser();
        uic.currentSkills = uic.user.skill;
        
        //need GET ENDPOINT
//        uic.availSkills = skillService.getSkills();
        //uic.availSkills = populateSkills();
        
        // functions
        uic.toast = toast;
        uic.cancel = cancel;
        uic.submit = submit;
        uic.getSkills = getSkills;
        uic.submitSkills = submitSkills;

          // initializations
        uic.getSkills();
        
          // functions
            // for notifications
        function toast( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        }
        
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
        
        function getSkills() {
        	skillService.getAll(function(response) {
        		uic.availSkills = response;
        	}, function(error) {
        	})
        }
        function submitSkills() {
        	//Test code REMOVE WHEN DONE--------
        	console.log(uic.user);
        	uic.currentSkills.push({"ts_id": 1, "skill": "Java"});
        	console.log(uic.currentSkills);
        	//----------------------------------
        	
        }
        
        
        
    }