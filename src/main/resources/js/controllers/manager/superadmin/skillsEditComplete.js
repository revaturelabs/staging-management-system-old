angular
.module("sms")
.controller("skillsEditSuccess", skillEditSuc);

function skillEditSuc($scope, $mdDialog){
    var sESCtrl = this;
        sESCtrl.okay = okay;

        function okay() {
            $mdDialog.hide();
        }
}