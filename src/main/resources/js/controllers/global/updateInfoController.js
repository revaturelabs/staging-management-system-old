    
    angular
        .module( "sms" )
        .controller( "updateInfoCrtl", updateInfoCtrl );
        
    function updateInfoCtrl ( $scope, $state, $mdToast, $mdDialog, loginService, skillService, userService ) {
        var uic = this;

        function populateSkills(){
        	var skills = [];

        	skills.push({"id": 1, "skill": "Java"});
            skills.push({"id": 2, "skill": "html"});
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
        uic.removeFromCurrentSkills = removeFromCurrentSkills;
        uic.saveSkills = saveSkills;

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
        	var add = removeFromAvailSkill($scope.skillToAdd);
        	console.log("add");
        	console.log(add);
        	if(add != null | add != undefined){
        		uic.currentSkills.push(add);
        	}
        	
        	uic.user.skill = uic.currentSkills;
        	$scope.skillToAdd="";
        }
        
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
        			console.log("availskils" + i);
        			console.log(uic.currentSkills[i]);
        			uic.availSkills.push({"id":uic.currentSkills[i].id, "skill":uic.currentSkills[i].skill});
        			uic.currentSkills.splice(i,1);
                	uic.user.skill = uic.currentSkills;
                	break;
        		}
        	}
        	
        }
        
        function saveSkills(){
        	console.log(uic.user);
        	
        	userService.update(uic.user,function(){
        		uic.toast("Skills updated");
        	});
        }
        
        
        
        
    }