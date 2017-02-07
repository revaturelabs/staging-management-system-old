angular
.module("sms")
.factory("skillEditFactory", skillEditFac);

function skillEditFac(){
    
    //data
    var skillsAddSuccess = [];
    var skillsAddFail = [];
    var skillsRemoveSuccess = [];
    var skillsRemoveFail = [];
    /**
     * @prop {obj} service Service returned by the factory.
     */
    var service = {
        addToAddSuccees: addToAddSuccees,
        addToAddFail: addToAddFail,
        addToRemoveSuccess: addToRemoveSuccess,
        addToRemoveFail : addToRemoveFail,
        getAddSuccess: getAddSuccess,
        getAddFail : getAddFail,
        getRemoveSuccess : getRemoveSuccess,
        getRemoveFail : getRemoveFail,
        clearAll : clearAll
    }
    return service;

    //functions
    /**
     * @description Function that adds the skill to the skillsAddSuccess array.
     */
    function addToAddSuccees(skillName){
        skillsAddSuccess.push(skillName);
    }
    /**
     * @description Function that adds the skill to the skillsAddFail array.
     */
    function addToAddFail(skillName, error){
        var obj = {skill: skillName, error: error};
        skillsAddFail.push(obj);
    }
    /**
     * @description Function that adds the skill to the skillsRemoveSuccess array.
     */
    function addToRemoveSuccess(skillName){
        skillsRemoveSuccess.push(skillName);
    }
    /**
     * @description Function that adds the skill to the skillsRemoveFail array.
     */
    function addToRemoveFail(skillName, error){
        var obj = {skill: skillName, error: error};
        skillsRemoveFail.push(obj);
    }
    /**
     * @description Function that returns the skillsAddSuccess array.
     */
    function getAddSuccess(){
        return skillsAddSuccess;
    }
    /**
     * @description Function that returns the skillsAddFail array.
     */
    function getAddFail(){
        return skillsAddFail;
    }
    /**
     * @description Function that returns the skillsRemoveSuccess array.
     */
    function getRemoveSuccess(){
        return skillsRemoveSuccess;
    }
    /**
     * @description Function that returns the skillsRemoveFail array.
     */
    function getRemoveFail(){
        return skillsRemoveFail;
    }
    /**
     * @description Function that clears all of the arrays.
     */
    function clearAll(){
        skillsAddSuccess.length = 0;
        skillsAddFail.length = 0;
        skillsRemoveSuccess.length = 0;
        skillsRemoveFail.length = 0;
    }

}




