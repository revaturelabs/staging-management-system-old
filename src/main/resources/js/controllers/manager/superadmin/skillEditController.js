angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, $mdDialog, $mdToast, $q, $timeout, skillService, skillEditFactory){

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
        /**
         * @prop {array} skillIds Array of skillIds that exist in currentSkills at the start.
         */
        sec.skillIds = [];


        sec.removeSkillPromiseList = [];
  
        /**
         * @prop {number} ADDSKILLDEFAULTVAL Constant that's the default id value for newly added skills
         */
        const ADDSKILLDEFAULTVAL = 0;

        var changesErrored = false;

        


        //functions
        sec.getSkills =  getSkills;
        sec.addSkillToDB= addSkillToDB;
        sec.removeSkillFromDB = removeSkillFromDB;
        sec.updateAll = updateAll;
        sec.toast = toast;
        sec.cancel = cancel;
        sec.updateSkillInDB = updateSkillInDB;
        sec.addNewSkillId = addNewSkillId;
        sec.removeSkillId = removeSkillId;

      
        //initialization
        
        sec.getSkills();
        
  
        /**
         * @description Function that runs when a new skill is created in the view to add an id into skillIds array.
         */
        function addNewSkillId(chip){
          for (var i=0; i<sec.newSkillList.length; i++){
                if (chip.toLowerCase() == sec.newSkillList[i].toLowerCase()){
                    //if the chip and a skill is the same when converted to lowerCase.
                    return null; //prevents chip from being added.
                }
            }
            if (sec.newSkillList.indexOf(chip)==-1){ //if the chip isn't already in the newSkillList
                sec.skillIds.push(ADDSKILLDEFAULTVAL); 
            }
        

        }

        /**
         * @description Function that runs when a skill is removed in the view to remove an id from skillIds array.
         */
        function removeSkillId(index){
            
            sec.skillIds.splice(index,1);
           
        }
        /**
         * @description Function that returns all skills in the database.
         */
        function getSkills() {
        	skillService.getAll(function(response) {

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
         * @description Function that adds the skill into the database. 
         * @param {boolean} isValid boolean value of the form's $valid property.
         */
        function addSkillToDB(skill){
                skillService.create(skill, function(){
                   skillEditFactory.addToAddSuccees(skill.skill);
                }, function(){
                    skillEditFactory.addToAddFail(skill.skill, "Issue"); //this actually shouldn't come up, but just in case here's something.
                
                    
                });
            }

        /**
         * @description Function that updates a skill in the database, replacing the old name with the new one.
         * @param {string} oldSkillName The old name of the skill.
         * @param {string} newSkillName The new name for the skill.
         */
        function updateSkillInDB(oldSkillName, newSkillName){
            skillService.update(oldSkillName, newSkillName, function(success){
                //empty to make it work
            }, function(error){
               //empty to make it work
            });
        }

        /**
         * @description Function that removes the specific skill from the database. 
         * @param {string} skillName The name of the skill to be deleted.
         */
         function removeSkillFromDB(skillName){
                
               sec.removeSkillPromiseList.push(
                   skillService.remove(skillName, function(){
                    skillEditFactory.addToRemoveSuccess(skillName); 
                }, function(error){
                    // if (changesErrored == false){
                    //     changesErrored = true;
                    // }
                     skillEditFactory.addToRemoveFail(skillName, error.data.errorMessage);               
                })
               );
                
   
        }
      
        /**
         * @description Function that's called to add, remove, and update the skills in the database
         * according to what the user wants to have done.
         */
         function updateAll(){
             
             //check the origin skill array and see if any skills have been removed.
            for (var i=0; i<sec.currentSkills.length; i++){
                var j = sec.skillIds.indexOf(sec.currentSkills[i].id); //gets skill id
                if (j==-1){ // skill was marked for deletion in the view
                   
                    sec.removeSkillFromDB(sec.currentSkills[i].skill);
                    continue;
                }
                var skillNameIn = sec.newSkillList.indexOf(sec.currentSkills[i].skill);
                if (j==skillNameIn){//skill is in same spot, everything is fine
                    continue;
                }
                else { // something got changed, need to edit the skill
                    sec.updateSkillInDB(sec.currentSkills[i].skill, sec.newSkillList[i]);
                    continue;
                }
            }
            for (var k = 0; k<sec.skillIds.length; k++){//now we need to search through 
                //skillIds to find any new skills.
                if (sec.skillIds[k] != ADDSKILLDEFAULTVAL){
                    continue;
                }
                var newSkill = {skill : sec.newSkillList[k]};
                sec.addSkillToDB(newSkill);//adds skill to DB

            }

            
        //   while($q.all(sec.removeSkillPromiseList).$$state.status === 0){
        //       $timeout(1000);
        //   }

         
         $mdDialog.hide();
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