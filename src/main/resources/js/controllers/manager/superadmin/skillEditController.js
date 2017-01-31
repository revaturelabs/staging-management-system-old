angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, skillService){

        var sec = this;

        //bindables
        sec.currentSkills = undefined

        //functions
        sec.getSkills =  getSkills;
        sec.printCurrentSkills = printCurrentSkills;
        sec.findSkill = findSkill;
        sec.addSkill= addSkill;
        //initialization
        
        sec.getSkills();
        

        

        function getSkills() {
        	skillService.getAll(function(response) {
        		sec.currentSkills = response;                
        	},function(){
                //needed to make proper function call for some reason.
            });
        }

        // function findSkill(skillName){
        //     skillService.retrieve(skillName, function(response){
                
        //     }, function(){
                
        //     });
        // }

        function printCurrentSkills(){
            console.log(sec.currentSkills);
        }


        function addSkill(isValid){
            if (isValid){
                var newSkill = {skill: sec.newSkillAdd};
                
                
                skillService.create(newSkill, function(){
                    sec.newSkillAdd = '';
                    sec.getSkills();
                }, function(){
                    //for some reason, this second function has to exist even if it's empty
                    
                });
            }
        }



   

        
    }