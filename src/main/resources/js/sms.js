var sms = angular.module( "sms", ["ngAria", "ngMessages", "ngAnimate", "ngMaterial", "ngResource", "ngCookies", "ui.router"]);

      // URL routing
    sms.config( function( $stateProvider, $urlRouterProvider, $locationProvider ) {

        $locationProvider.html5Mode(true);
		$urlRouterProvider.otherwise("/login");

		$stateProvider
              // login
			.state( "login", {
				url: "/login",
                views: {
                    "mainView": {
    				    templateUrl: "html/views/login.html",
	    			    controller: "loginCtrl as logCtrl"
                    }
                }
			})
			
			
		
			
			  // Associate update information
                .state( "ASupdateInfo", {
                    url: "^/AupdateInformation",
                    parent: "assoc",
                    views: {
                        "mainAssociateView": {
                            templateUrl: "html/views/updateInformation.html",
                            controller: "updateInfoCrtl as uInfoctrl"
                        }
                    }
                })
                
             // Admin update information
                .state( "ADupdateInfo", {
                    url: "^/AupdateInformation",
                    parent: "assoc",
                    views: {
                        "mainAssociateView": {
                            templateUrl: "html/views/updateInformation.html",
                            controller: "updateInfoCrtl as uInfoctrl"
                        }
                    }
                })
			
             // SuperAdmin update information
                .state( "SAupdateInfo", {
                    url: "^/SA-updateInformation",
                    parent: "super",
                    views: {
                        "mainAssociateView": {
                            templateUrl: "html/views/updateInformation.html",
                            controller: "updateInfoCrtl as uInfoctrl"
                        }
                    }
                })
			
			
			
			
              // superadmin page
			.state( "super", {
				url: "/super",
                views: {
                    "topBarView": {
                        templateUrl: "html/views/topBar.html"
                    },
                    "mainView": {
    				    templateUrl: "html/views/superadmin/super.html",
	    			    controller: "superCtrl as suCtrl"
                    }
                }
			})
                  // superadmin view all attendance
                .state( "superAttendance", {
                    url: "^/attendance",
                    parent: "super",
                    views: {
                        "mainSuperView": {
                            templateUrl: "html/views/superadmin/superAttendance.html",
                            controller: "superAttendanceCtrl as supAttCtrl"
                        }
                    }
                })
              // admin page
			.state( "admin", {
				url: "/admin",
                views: {
                    "topBarView": {
                        templateUrl: "html/views/topBar.html"
                    },
                    "mainView": {
    				    templateUrl: "html/views/admin/admin.html",
	    			    controller: "adminCtrl as adCtrl"
                    }
                }
			})
              // associate page
			.state( "assoc", {
				url: "/assoc",
                views: {
                    "topBarView": {
                        templateUrl: "html/views/topBar.html"
                    },
                    "mainView": {
    				    templateUrl: "html/views/associate/associate.html",
	    			    controller: "associateCtrl as assoCtrl"
                    }
                }
			})
	});

      // theme config
    sms.config( function($mdThemingProvider) {
        $mdThemingProvider.theme("default")
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
    