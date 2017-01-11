
    var sms = angular.module( "sms" );
    sms.controller( "batchAddCtrl", function($scope) {
        var bac = this;

          // function
        bac.addNew = function(isValid) {
            if (isValid) {
                bac.associates.push( { firstName: bac.firstName, lastName: bac.lastName } );
                bac.firstName = "";
                bac.lastName = "";
                $scope.newAssociate.$setUntouched();
                $scope.newAssociate.$setPristine();
            }
        };

          // data
        bac.associates = [];

    });