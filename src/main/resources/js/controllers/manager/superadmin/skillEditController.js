angular.module("sms").
controller("skillEditCtrl", editSkillController);

function editSkillController($scope, $mdDialog, $mdToast, $q, skillService, skillEditFactory){

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


        sec.skillPromiseList = [];
        sec.skillRemovePromiseList = [];
        sec.skillUpdatePromiseList = [];
        sec.skillAddPromiseList = [];
  
        /**
         * @prop {number} ADDSKILLDEFAULTVAL Constant that's the default id value for newly added skills
         */
        const ADDSKILLDEFAULTVAL = 0;

        

        


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
                sec.skillAddPromiseList.push(skillService.create(skill, function(){
                  //empty function
                }, function(){
                    //empty function
                    
                }));
            }

        /**
         * @description Function that updates a skill in the database, replacing the old name with the new one.
         * @param {string} oldSkillName The old name of the skill.
         * @param {string} newSkillName The new name for the skill.
         */
        function updateSkillInDB(oldSkillName, newSkillName){
           sec.skillUpdatePromiseList.push( skillService.update(oldSkillName, newSkillName, function(){
                //empty to make it work
            }, function(){
               //empty to make it work
            }));
        }

        /**
         * @description Function that removes the specific skill from the database. 
         * @param {string} skillName The name of the skill to be deleted.
         */
         function removeSkillFromDB(skillName){
                
               sec.skillRemovePromiseList.push(
                   skillService.remove(skillName, function(){
                   //empty function to make this work
                }, function(){
                    //empty function to make this work
                })
               );
                
   
        }
      
        /**
         * @description Function that's called to add, remove, and update the skills in the database
         * according to what the user wants to have done.
         */
         function updateAll(){
             var removeList = [];
             var updateList = [];
             var addList = [];

             //check the origin skill array and see if any skills have been removed.
            for (var i=0; i<sec.currentSkills.length; i++){
                var j = sec.skillIds.indexOf(sec.currentSkills[i].id); //gets skill id
                if (j==-1){ // skill was marked for deletion in the view
                   
                    
                    removeList.push(sec.currentSkills[i].skill);
                }
                
                var skillNameIn = sec.newSkillList.indexOf(sec.currentSkills[i].skill);
                if (!(j==skillNameIn)){
                  // something got changed, need to edit the skill
                    if (sec.newSkillList[j] != undefined){
                    
                        var updateSkill = {oldSkillName: sec.currentSkills[i].skill, newSkillName: sec.newSkillList[j]};
                        updateList.push(updateSkill);
                    }
                }
             
            }
            for (var k = 0; k<sec.skillIds.length; k++){//now we need to search through 
                //skillIds to find any new skills.
                if (sec.skillIds[k] != ADDSKILLDEFAULTVAL){
                    continue;
                }
                var newSkill = {skill : sec.newSkillList[k]};
                addList.push(newSkill);
               

            }
           

            //resolve remove skills
            for ( i=0; i<removeList.length; i++){
                sec.removeSkillFromDB(removeList[i]);
            }

            $q.all(sec.skillRemovePromiseList).then(function(){
                //successful on removeList
                for (var index1 = 0; index1<updateList.length; index1++){
                    sec.updateSkillInDB(updateList[index1].oldSkillName, updateList[index1].newSkillName);
                }
                $q.all(sec.skillUpdatePromiseList).then(function(){
                    //successful update
                    for (var index2=0; index2<addList.length; index2++){
                        sec.addSkillToDB(addList[index2]);
                    }
                }, function(){
                    //failed update
                    for (var index3=0; index3<addList.length; index3++){
                        sec.addSkillToDB(addList[index3]);
                    }
                });
            }, function(){
                //error on remove list
                for (var index4 = 0; index4<updateList.length; index4++){
                    sec.updateSkillInDB(updateList[index4].oldSkillName, updateList[index4].newSkillName);
                }
                $q.all(sec.skillUpdatePromiseList).then(function(){
                    //successful update
                    for (var index5=0; index5<addList.length; index5++){
                        sec.addSkillToDB(addList[index5]);
                    }
                }, function(){
                    //failed update
                    for (var index6=0; index6<addList.length; index6++){
                        sec.addSkillToDB(addList[index6]);
                    }
                });
                
            });
            sec.skillPromiseList = sec.skillRemovePromiseList
            .concat(sec.skillUpdatePromiseList)
            .concat(sec.skillAddPromiseList);

            $q.all(sec.skillPromiseList).then(function(){
                sec.toast("Skill edits successful!");
                $mdDialog.hide();
            }, function(){
                sec.toast("Problem occurred while editing skills.")
                $mdDialog.hide();
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