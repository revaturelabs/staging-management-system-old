var sms = angular.module( "sms", ["ngAria", "ngMessages", "ngAnimate", "ngMaterial", "md.data.table", "ngResource", "ngCookies", "ui.router"]);

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
				 // admin view all attendance
	                .state( "adminAttendance", {
	                    url: "^/associate-attendance",
	                    parent: "admin",
	                    views: {
	                        "mainAdminView": {
	                            templateUrl: "html/views/admin/associate-attendance-table.html",
	                            controller: "adminAttendanceCtrl as adAttCtrl"
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

			//.....................................................................
			// associate view all attendance
                .state( "assocAttendance", {
                    url: "^/weeklyattendence",
                    parent: "assoc",
                    views: {
                        "mainAssociateView": {
                            templateUrl: "html/views/associate/associateWeeklyAttendence.html",
                            controller: "associateWeeklyAttendenceCtrl as assWeekAttCtrl"
                        }
                        
                    }
                })
            // associate view certifications
                .state( "assocCertifications", {
                    url: "^/certifications",
                    parent: "assoc",
                    views: {
                        "mainAssociateView": {
                            templateUrl: "html/views/associate/associateCertifications.html",
                            controller: "associateCertificationsCtrl as assCertCtrl"
                        }
                        
                    }
                })
			//........................................................................

	});

      // theme config
    sms.config( function($mdThemingProvider) {
        $mdThemingProvider.theme("default")
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
