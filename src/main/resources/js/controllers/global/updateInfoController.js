    
    angular
        .module( "sms" )
        .controller( "updateInfoCrtl", updateInfoCtrl );

        /**
         * @description AngularJS controller for updating a password (and eventually other info)
         */
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService, skillService, userService ) {

        var uic = this;

        
          // bindables

            // functions
       
        /**
         * @var {object} user The currently logged in user.
         */
        uic.user = loginService.getUser();
        /**
         * @var {array} currentSkills The logged in user's current list of skills.
         */
        uic.currentSkills = uic.user.skill;
        
        // functions

        uic.toast = toast;

        uic.cancel = cancel;

        uic.submit = submit;
        uic.getSkills = getSkills;
        uic.submitSkills = submitSkills;
        uic.removeFromCurrentSkills = removeFromCurrentSkills;
        uic.saveSkills = saveSkills;
        uic.removeFromCSkills = removeFromCSkills;
          // initializations
        uic.getSkills();
     
        
          // functions
             /**
             * @description Displays a toast notification.
             * @param {string} message The value of the message to be shown.
             */
        function toast( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        }
        

        /**
         * @description Cancel the currently opened dialog window.
         */
        function cancel() {
            $mdDialog.cancel();
        }
        
           
            /**
             * @description Submit the changed password to update the users password.
             */
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
        
        /**
         * @description Retrieves all the skills of the current user.
         */
        function getSkills() {
        	skillService.getAll(function(response) {
        		uic.availSkills = response;
        		
        		removeFromCSkills();
        	})
        }
        /**
         * @description Adds new skills to the users list of skills.
         */
        function submitSkills() {
            if($scope.skillToAdd == undefined || $scope.skillToAdd == "" ){
                uic.toast("Please Select a Skill");
                return; } 
        	var add = removeFromAvailSkill();
        	uic.currentSkills.push(add);
        	
        	uic.user.skill = uic.currentSkills;
        	$scope.skillToAdd="";
        }
        /**
         * @description Removes skills from the list of available skills.
         */
        function removeFromAvailSkill(){
        	for(var i =0; i < uic.availSkills.length; i++){
        		if($scope.skillToAdd == uic.availSkills[i].id){

        			var toReturn = {id:uic.availSkills[i].id, skill:uic.availSkills[i].skill};
        			
        			//remove from avail skills
        			uic.availSkills.splice(i,1);
        			
        			return toReturn;
        		}
        	}
        	return null;
        }
        
        function removeFromCurrentSkills(id){
        	for(var i =0; i < uic.currentSkills.length; i++){
        		if(id == uic.currentSkills[i].id){
        			//remove from avail skills
        			uic.availSkills.push({"id":uic.currentSkills[i].id, "skill":uic.currentSkills[i].skill});
        			uic.currentSkills.splice(i,1);
                	uic.user.skill = uic.currentSkills;
                	break;
        		}
        	}
        }
        
        function saveSkills(){
        	userService.update(uic.user,function(){
        		uic.toast("Skills updated");
        	});
        }
        
        function removeFromCSkills(){
        	for (var i = 0; i< uic.currentSkills.length;i++){
        		for(var j = 0; j < uic.availSkills.length; j++ ){
        			if(uic.currentSkills[i].skill == uic.availSkills[j].skill){
        				uic.availSkills.splice(j,1);
        				break;
        			}
        		}
        	}
        }
        
        
        
        
    }
