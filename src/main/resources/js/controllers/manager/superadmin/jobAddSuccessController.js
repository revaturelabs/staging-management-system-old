
    angular
        .module( "sms" )
        .controller( "jobAddSuccessCtrl", bASCtrl );

    function bASCtrl( $scope, $mdDialog ) {
        var bASCtrl = this;
        bASCtrl.okay = okay;

        function okay() {
            $mdDialog.hide();
        }
    }