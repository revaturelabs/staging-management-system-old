angular
.module("sms")
.controller("skillsEditSuccess", skillEditSuc);

function skillEditSuc($scope, $mdDialog){
    var ses = this;
        ses.okay = okay;

        function okay() {
            $mdDialog.hide();
        }
}