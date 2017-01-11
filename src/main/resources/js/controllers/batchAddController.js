
    var sms = angular.module( "sms" );
    sms.controller( "batchAddCtrl", function($scope, $mdDialog, userService) {
        var bac = this;
        
        
   
          // function
        bac.addNew = function(isValid) {
            if (isValid) {
                bac.associates.push( { firstName: bac.firstName, lastName: bac.lastName } );
                bac.firstName = "";
                bac.lastName = "";
                $scope.newAssociate.$setUntouched();
                $scope.newAssociate.$setPristine();
            }
        };

          // data
        bac.associates = [];

        bac.save = function(list){
        	if (list.length != 0){
        		var addUser = list.shift();
        		//call rest controller to save user via userService
        		
        		save(list);
        	}
        	else {
        		$mdDialog.hide();
        	}
        	
        }
        
        bac.cancel = function(){
        	$mdDialog.cancel();
        }
    });