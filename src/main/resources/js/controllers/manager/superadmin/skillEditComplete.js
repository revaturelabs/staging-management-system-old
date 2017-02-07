angular
.module("sms")
.controller("skillsEditSuccess", skillEditSuc);
/**
 * @description AngularJS controller for the SkillEditComplete dialog.
 */
function skillEditSuc($scope, $mdDialog){
    var ses = this;
        ses.okay = okay;
        /**
         * @description Closes the dialog.
         */
        function okay() {
            $mdDialog.hide();
        }
}