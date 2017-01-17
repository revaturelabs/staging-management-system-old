
    angular
        .module( "sms" )
        .controller( "batchAddSuccessCtrl", bASCtrl );

    function bASCtrl( $scope, $mdDialog ) {
        var bASCtrl = this;
        bASCtrl.okay = okay;

        function okay() {
            $mdDialog.hide();
        }
    };