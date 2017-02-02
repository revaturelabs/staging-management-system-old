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
        sec.addSkill = addSkill;
        sec.removeFromAddArray = removeFromAddArray;
        sec.removeFromRemoveArray = removeFromRemoveArray;
        sec.addSkillToDB= addSkillToDB;
        sec.removeSkillFromDB = removeSkillFromDB;
        sec.addToRemoveList = addToRemoveList;
        sec.updateAll = updateAll;
        sec.inRemoveList = inRemoveList;
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


        function addSkill(isValid, skillName){
            
            if (isValid){
         
                var newSkill = {skill: skillName};
                var index = findSkill(skillName);
                
                if (index == -1){
                    sec.skillsToAdd.push(newSkill);
                    sec.newSkillAdd = "";

                }
            }
        }
        /**
         * @description Check to see if a skill is in the return list
         * @return {boolean} Value that matches if item is in the skilsToRemove list already or not.
         */
        function inRemoveList(skill){
            for (var i=0; i<sec.skillsToRemove.length; i++){
                if (sec.skillsToRemove[i].skill == skill.skill){
                    return true;
                }
            }
            return false;
        }

        function findSkill(skillName){
            //checks to see if skill is already in database.
            for (var i=0; i<sec.currentSkills.length; i++){
                if (sec.currentSkills[i].skill.toLowerCase() == skillName.toLowerCase()
                && !sec.inRemoveList(sec.currentSkills[i])){
                    sec.toast("Skill is already in the database.");
                    return i;

                }
                
            }
            //checks to see if skill is already going to be added.
            for (i=0; i<sec.skillsToAdd.length; i++){
                if (sec.skillsToAdd[i].skill.toLowerCase() == skillName.toLowerCase() &&
                !sec.inRemoveList(sec.skillsToAdd[i]))
                {
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
        function addSkillToDB(skill){
                skillService.create(skill, function(){
                   //empty function needed to make this work
                }, function(){
                    //for some reason, this second function has to exist even if it's empty
                    
                });
            }

        function addSkillsToDB(){
            if (sec.skillsToAdd.length >0){
                var skill = sec.skillsToAdd.pop();
                addSkillToDB(skill);
                addSkillsToDB();
            }
        }

        function removeFromAddArray(skill){
            
            var index = sec.skillsToAdd.indexOf(skill);
            if(index!=-1){
                sec.skillsToAdd.splice(index, 1);
            }
            else {
                for (var i=0; i<sec.skillsToAdd.length; i++){
                    if (skill.skill.toLowerCase() == sec.skillsToAdd[i].skill.toLowerCase()){
                        //if the skills have the same skill name.
                        sec.skillsToAdd.splice(i, 1); //remove that skill from the add array.
                    }
                }
            }
           
            
            
        }

        function removeFromRemoveArray(skill){
            
            var index = sec.skillsToRemove.indexOf(skill);
            if (index!=1){
                sec.skillsToRemove.splice(index, 1);        
                removeFromAddArray(skill);
            }
            
        }

        function addToRemoveList(isValid, skill){
            if (isValid){
                
                sec.skillsToRemove.push(skill);
                sec.selected = undefined;
            }
        }

         function updateAll(){
            
            if (sec.skillsToAdd.length>0 || sec.skillsToRemove.length > 0){
                removeSkillsFromDB();
                addSkillsToDB();
                sec.getSkills();
                
                sec.toast("Skill updates successful.");
                $mdDialog.hide();
            }
            else {
                sec.toast("Either create a new skill or delete an existing skill.");
            }
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

        function removeSkillsFromDB(){
            
            if (sec.skillsToRemove.length >0 ){
                var skill = sec.skillsToRemove.pop();
                removeSkillFromDB(skill.skill);
                removeSkillsFromDB();
            }
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