angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, skillService){

        var sec = this;

        //bindables
        sec.currentSkills = undefined

        //functions
        sec.getSkills =  getSkills;
        sec.printCurrentSkills = printCurrentSkills;
        //initialization
        
        sec.getSkills();
        

        

        function getSkills() {
        	skillService.getAll(function(response) {
        		sec.currentSkills = response;                
        	},function(){
                //needed to make proper function call for some reason.
            });
        }

        function printCurrentSkills(){
            console.log(sec.currentSkills);
        }



   

        
    }