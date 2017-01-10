
    var sms = angular.module( "sms" );
    sms.controller( "batchAddCtrl", function($scope) {
        var bac = this;

          // function
        bac.addNew = function() {
            bac.associates.push( { firstName: bac.firstName, lastName: bac.lastName } );
            bac.firstName = "";
            bac.lastName = "";
        };

          // data
        bac.associates = [];

    });