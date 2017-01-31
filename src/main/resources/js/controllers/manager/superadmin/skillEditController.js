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
        sec.testAddSkill = testAddSkill;
        //initialization
        
        sec.getSkills();
        

        

        function getSkills() {
        	skillService.getAll(function(response) {
        		sec.currentSkills = response;                
        	},function(){
                //needed to make proper function call for some reason.
            });
        }

        function findSkill(skillName){
            skillService.retrieve(skillName, function(response){
                console.log(response);
            }, function(error){
                console.log(error);
            });
        }

        function printCurrentSkills(){
            console.log(sec.currentSkills);
        }

        function testAddSkill(){
            
            
            skillService.create(newSkill, function(response){
                console.log(response);
                sec.getSkills();
            }, function(error){
                console.log(error);
            })
        }



   

        
    }