 var sms = angular.module("sms");

    sms.controller("skillEditCtrl", function($scope, skillService){

        var sec = this;

        //bindables
        sec.currentSkills = undefined;

        //functions
        sec.getSkills = getSkills;

        sec.getSkills();

        

        function getSkills() {
        	skillService.getAll(function(response) {
        		sec.currentSkills = response;
                

        	});
        }

        console.log(sec.currentSkills);
    });