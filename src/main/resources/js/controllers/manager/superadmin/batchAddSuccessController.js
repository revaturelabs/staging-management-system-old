
    angular
        .module( "sms" )
        .controller( "batchAddSuccessCtrl", bASCtrl );
/**
 * @description AngularJs controller for the batchAddSuccess notification
 */
    function bASCtrl( $scope, $mdDialog ) {
        var bASCtrl = this;
        bASCtrl.okay = okay;

        /**
         * @description Closes the window showing the events
         */
        function okay() {
            $mdDialog.hide();
        }
    }