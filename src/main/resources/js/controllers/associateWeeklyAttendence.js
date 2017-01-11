
    var sms = angular.module( "sms" );

    sms.controller( "associateWeeklyAttendenceCtrl", function( $scope, $state, loginService, attendenceService){
        var asc = this;

        alert("here");
        $scope.$parent.mastCtrl.toast("Here");
          // data
        asc.user = loginService.getUser();
        asc.token = loginService.getToken();
        
        
        var as = attendenceService.getAll();
       
        alert(as[0].getNote());
        
        
        
    });