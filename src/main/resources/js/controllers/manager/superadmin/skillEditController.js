angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, $mdDialog, $mdToast, skillService, skillEditFactory){

        var sec = this;

        //bindables
        /**
         * @prop {array} currentSkills Array of skills currently in database. 
         */
        sec.currentSkills = [];
        /**
         * @prop {array} newSkillList Array of skillNames after the edits have been made
         */
        sec.newSkillList = [];
        /**
         * @prop {array} currentSkillNames Array of skillNames that exist in currentSkills at the start.
         */
        
        sec.skillIds = [];
        /**
         * @prop {array} currentSkills Arry of skills  to remove. 
         */
        sec.skillsToRemove = [];
        /**
         * @prop {array} currentSkills Arry of skills to add. 
         */
        sec.skillsToAdd = [];
        /**
         * @prop {number} ADDSKILLDEFAULTVAL Constant that's the default id value for newly added skills
         */
        const ADDSKILLDEFAULTVAL = 0;


        //functions
        sec.getSkills =  getSkills;
        sec.addSkill = addSkill;
        sec.removeFromAddArray = removeFromAddArray;
        sec.addSkillToDB= addSkillToDB;
        sec.removeSkillFromDB = removeSkillFromDB;
        sec.addToRemoveList = addToRemoveList;
        sec.updateAll = updateAll;
        sec.inRemoveList = inRemoveList;
        sec.toast = toast;
        sec.cancel = cancel;
        sec.editSkill = editSkill;
        sec.newChip = newChip;
        //sec.updateChip = updateChip;
        sec.removeChip = removeChip;
        
       
    


      
        //initialization
        
        sec.getSkills();
        
  

        function newChip(chip){
            sec.skillIds.push(ADDSKILLDEFAULTVAL); //adds the default value for skill ids into the ids array. Set to be 0 currently,
            //but can be any number as long as that number cannont actually be an idea.
           
            // if (angular.isObject(chip)){
                
            //     return chip;
            // }
            // return {skill: chip}
            
        }

        // function updateChip(chip){


        //     for (var i=0; i<sec.newSkillList.length; i++){
        //         if (sec.newSkillList[i] === chip && !angular.isObject(chip) ){
        //             sec.newSkillList[i] = {skill: chip};
        //             break;
        //         }
        //     }
        // }

        function removeChip(index){
            
            sec.skillIds.splice(index,1);
           
        }
        /**
         * @description Function that returns all skills in the database.
         */
        function getSkills() {
        	skillService.getAll(function(response) {
                // for (var i=0; i<response.length; i++){
                //     sec.currentSkillNames.push(response[i].skill);
                //     sec.newSkillList.push(response[i].skill);

                // }
        		sec.currentSkills = response;
             
              
               
                for (var i=0; i<sec.currentSkills.length; i++){
                    sec.newSkillList[i] = sec.currentSkills[i].skill;
                    sec.skillIds[i] = sec.currentSkills[i].id;
                }
                             
        	},function(){
                //needed to make proper function call for some reason.
            });
        }

        
        /**
         * @description Adds a skill to the sec.skillsToAdd array.
         * @param {boolean} isValid $valid attribute of the form.
         * @param {string} skillName Name of the skill to be created and added to the array.
         */
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
         * @description Check to see if a skill is in the remove list
         * @param {object} skill Skill to be checked to see if it is in the remove list (checks by the skill name).
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

        /**
         * @description Function that checks to see if a skill to be newly created is already in the database
         * or is already set to be added to the database. The only time it'll let a skill that's already in the
         * database be added is if the current database version is going to be removed.
         * @param {string} skillName The name of the skill to check.
         * @return {number} Returns the index number of the skill if it's found in either of the arrays, otherwise returns -1.
         */
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
         * @description Function that adds the skill into the database. Called as part of the addSkillsToDB function.
         * @param {boolean} isValid boolean value of the form's $valid property.
         */
        function addSkillToDB(skill){
                skillService.create(skill, function(){
                   skillEditFactory.addToAddSuccees(skill.skill);
                }, function(error){
                    skillEditFactory.addToAddFail(skill.skill, "Issue"); //this actually shouldn't come up, but just in case here's something.
                    console.log(error);
                    
                });
            }
        /**
         * @description Function that adds all the skills in the skillsToAdd array into the database.
         */
        function addSkillsToDB(){
            if (sec.skillsToAdd.length >0){
                var skill = sec.skillsToAdd.pop();
                addSkillToDB(skill);
                addSkillsToDB();
            }
        }

        /**
         * @description Method to remove and item from the skillsToAdd array. 
         */
        function removeFromAddArray(skill){
            
                for (var i=0; i<sec.skillsToAdd.length; i++){
                    if (skill.skill.toLowerCase() == sec.skillsToAdd[i].skill.toLowerCase()){
                        //if the skills have the same skill name.
                        sec.skillsToAdd.splice(i, 1); //remove that skill from the add array.  
                }
            }
        }

        /**
         * @description Function to add a skill to the skillsToRemove list.
         * @param {boolean} isValid The $valid property of the form used.
         * @param {object} skill The skill object to be added to the list.
         */
        function addToRemoveList(isValid, skill){
            if (isValid){
                
                sec.skillsToRemove.push(skill);
                sec.selected = undefined;
            }
        }

        /**
         * @description Function that's called to add all the new skills to the database and to remove the skills that
         * were selected to be removed.
         */
         function updateAll(){
             //check the origin skill array and see if any skills have been removed.
            for (var i=0; i<sec.currentSkills.length; i++){
                
            }

         }



         function updateAllOld(){
           //make sure to fix this tomorrow and use indexOf

           
             var j = 0; //variable to keep track of where we are in the newer arrays, as there can be different lengths compared
             //to the old one
            for (var i=0; i<sec.currentSkills.length; i++){
                if (i!= 0) j++; // if i isn't 0, then j needs to be incremented here.
                //checks original array to see if an id still exists
                if (j < sec.skillIds.length && sec.currentSkills[i].id == sec.skillIds[j] ){
                    //if it's still there, then check to see if name has been changed
                    if (sec.currentSkills[i].skill != sec.newSkillList[j]){
                        //if the names don't match then it's been changed,so we need to update the database
                        updateSkillInDB(sec.currentSkills[i].skill, sec.newSkillList[j]);
                        
                    }
                    // either we've updated the datbase, got an error, or the skill hadn't been changed.
                    continue; // either way, move on to next loop, as we're done here
                } 
                //need to check to see if the skill is one that's added, and if so break the loop entirely
                //since that means that we won't have anything else in the database to worry about this time.
                if (j < sec.skillIds.length && sec.skillIds[j] == ADDSKILLDEFAULTVAL){
                    j = i; //sets j equal to i to make sure we start at the right location.
                    break;
                }        
               
                    //if the currentSkills[i].id and sec.skillIds[i] don't match, j is greater than sec.skillIds.length
                    //then currentSkills[i] has
                    //been marked for deletion and needs to be removed.
                    console.log(i + " " + j);
                    console.log(sec.currentSkills[i].skill + " " + sec.newSkillList[j]);
                    removeSkillFromDB(sec.currentSkills[i].skill);
                    i++;//need to increment j here
                    continue; //we're done with this iteration, go on to the next one
                
                
            }
            //At this point, we may still have skills that need to be added, so let's go ahead and handle those.

            //picking up where our old skills ended, we continue going through the newSkillList to add the remaining skills
            //to the array
          
            // if (sec.skillsToAdd.length>0 || sec.skillsToRemove.length > 0){
            //     removeSkillsFromDB();
            //     addSkillsToDB();
            //     sec.getSkills();
                
            //     sec.toast("Skill updates successful.");
            //     $mdDialog.hide();
            // }
            // else {
            //     sec.toast("Either create a new skill or delete an existing skill.");
            // }
            $mdDialog.hide();
        }

        function updateSkillInDB(oldSkillName, newSkillName){
            skillService.update(oldSkillName, newSkillName, function(success){
                console.log(success);
            }, function(error){
                console.log(error);
            });
        }

        /**
         * @description Function that removes the specific skill from the database. Only used
         * as part of removeSkillsFromDB.
         * @param {string} skillName The name of the skill to be deleted.
         */
         function removeSkillFromDB(skillName){
                
                skillService.remove(skillName, function(){
                    skillEditFactory.addToRemoveSuccess(skillName); 
                }, function(error){
                    skillEditFactory.addToRemoveFail(skillName, error.data.errorMessage);               
                });
            
        }

        /**
         * @description Runs and removes all skills in the sec.skillsToRemove array from the database 
         */
        function removeSkillsFromDB(){
            
            if (sec.skillsToRemove.length >0 ){
                var skill = sec.skillsToRemove.pop();
                removeSkillFromDB(skill.skill);
                removeSkillsFromDB();
            }
        }

        function editSkill(isValid, skillName, newSkillName){
                if (isValid){
                skillService.update(skillName, newSkillName, function(){
                    sec.oldSkill = undefined;
                    sec.newSkillName = undefined;
                    sec.getSkills();

            }, function(error){
                console.log(error);
                sec.toast(error.data.errorMessage);
               
            });
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