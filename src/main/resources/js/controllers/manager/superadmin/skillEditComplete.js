angular
.module("sms")
.controller("skillsEditSuccess", skillEditSuc);
/**
 * @description AngularJS controller for the SkillEditComplete dialog.
 */
function skillEditSuc($scope, $mdDialog, $timeout){
    var ses = this;
        ses.okay = okay;
        ses.toast = toast;

        
       /**
        * @description Displays a toast notification.
        * @param {string} message The value of the message to be shown.
        */
        function toast( message ) {
            $mdToast.show( $mdToast.simple().textContent( message ).action("OKAY").position("top right").highlightAction(true) );
        }

        /**
         * @description Closes the dialog.
         */
        function okay() {
        
                $mdDialog.hide();
           
            
        }
}