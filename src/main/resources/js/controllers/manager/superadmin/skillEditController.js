angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, skillService){

        var sec = this;

        //bindables
        sec.currentSkills = undefined;

        //functions
        sec.getSkills = getSkills;
        
        //initialization
        
        sec.getSkills();
        

        

        function getSkills() {
        	skillService.getAll(function(response) {
        		sec.currentSkills = response;
               
               
                

        	}, function(){
                console.log("Just here to make work");
            });
        }

   

        
    }