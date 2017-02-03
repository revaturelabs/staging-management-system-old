angular
.module("sms")
.factory("skillEditFactory", skillEditFac);

function skillEditFac(){
    
    //data
    var skillsAddSuccess = [];
    var skillsAddFail = [];
    var skillsRemoveSuccess = [];
    var skillsRemoveFail = [];

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
    function addToAddSuccees(skillName){
        skillsAddSuccess.push(skillName);
    }

    function addToAddFail(skillName, error){
        var obj = {skill: skillName, error: error};
        skillsAddFail.push(obj);
    }

    function addToRemoveSuccess(skillName){
        skillsRemoveSuccess.push(skillName);
    }

    function addToRemoveFail(skillName, error){
        var obj = {skill: skillName, error: error};
        skillsRemoveFail.push(obj);
    }

    function getAddSuccess(){
        return skillsAddSuccess;
    }

    function getAddFail(){
        return skillsAddFail;
    }

    function getRemoveSuccess(){
        return skillsRemoveSuccess;
    }

    function getRemoveFail(){
        return skillsRemoveFail;
    }

    function clearAll(){
        skillsAddSuccess.length = 0;
        skillsAddFail.length = 0;
        skillsRemoveSuccess.length = 0;
        skillsRemoveFail.length = 0;
    }

}




