angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, $mdDialog, $mdToast, skillService){

        var sec = this;

        //bindables
        /**
         * @prop {array} currentSkills Arry of skills currently in database. 
         */
        sec.currentSkills = undefined;
        /**
         * @prop {array} currentSkills Arry of skills  to remove. 
         */
        sec.skillsToRemove = [];
        /**
         * @prop {array} currentSkills Arry of skills to add. 
         */
        sec.skillsToAdd = [];


        //functions
        sec.getSkills =  getSkills;
        sec.printCurrentSkills = printCurrentSkills;
        sec.addSkill = addSkill;
        sec.removeFromAddArray = removeFromAddArray;
        sec.addSkillToDB= addSkillToDB;
        sec.removeSkillFromDB = removeSkillFromDB;
        sec.toast = toast;
        sec.cancel = cancel;
       
      
        //initialization
        
        sec.getSkills();
        

        
        /**
         * @description Function that returns all skills in the database.
         */
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


        function addSkill(isValid, skillName){
            
            if (isValid){
         
                var newSkill = {skill: skillName};
                var index = findSkill(skillName);
                console.log(index);
                if (index == -1){
                    sec.skillsToAdd.push(newSkill);
                    sec.newSkillAdd = "";

                }
               
               
                

            }
        }

        function findSkill(skillName){
            //checks to see if skill is already in database.
            for (var i=0; i<sec.currentSkills.length; i++){
                if (sec.currentSkills[i].skill.toLowerCase() == skillName.toLowerCase()){
                    sec.toast("Skill is already in the database.");
                    return i;

                }
                
            }
            //checks to see if skill is already going to be added.
            for (i=0; i<sec.skillsToAdd.length; i++){
                if (sec.skillsToAdd[i].skill.toLowerCase() == skillName.toLowerCase()){
                    sec.toast("Skill is already going to be added.");
                    return i;
                }
            }
            return -1;
        }
        /**
         * @description Function that adds the skill into the database.
         * @param {boolean} isValid boolean value of the form's $valid property.
         */
        function addSkillToDB(isValid){
            if (isValid){
                var newSkill = {skill: sec.newSkillAdd};
                skillService.create(newSkill, function(){
                   
                    sec.getSkills();
                }, function(){
                    //for some reason, this second function has to exist even if it's empty
                    
                });
            }
        }

        function removeFromAddArray(skill){
            
            var index = sec.skillsToAdd.indexOf(skill);
            sec.skillsToAdd.splice(index, 1);
            
        }

        /**
         * @description Function that removes the skill from the database.
         * 
         */
         function removeSkillFromDB(skillName){
         
                        
                
                skillService.remove(skillName, function(){
                    sec.selected = '';
                    sec.getSkills();
                }, function(){
                   
                    //for some reason, this second function has to exist even if it's empty
                    
                });
            
        }

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
            sec.toast("Skill Edit Cancelled");
            $mdDialog.cancel();
        }

       



   

        
    }