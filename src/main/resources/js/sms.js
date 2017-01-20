
    var sms = angular.module( "sms", ["ngAria", "ngMessages", "ngAnimate", "ngMaterial", "md.data.table", "ngResource", "ngCookies", "ui.router"]);

      // global constants
    sms.constant( "weekdays", [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ] );

      // URL routing
    sms.config( function( $stateProvider, $urlRouterProvider, $locationProvider ) {

        $locationProvider.html5Mode(true);
		$urlRouterProvider.otherwise("/login");

		$stateProvider
              
              // root
            .state( "root", {
                // url: "",
                abstract: true,
                // template: "<ui-view/>",
                controller: "rootCtrl",
                controllerAs: "rootCtrl",
                views: {
                    "topBarView": {
                        templateUrl: "html/views/topBar.html"
                    }
                }
            })

                  // login
                .state( "login", {
                    parent: "root",
                    url: "/login",
                    views: {
                        "contentView@": {
                            templateUrl: "html/views/login.html",
                            controller: "loginCtrl",
                            controllerAs: "logCtrl"
                        },
                        "topBarView@": {
                            template: ""
                        }
                    }
                })

                  // attendance
                .state( "attendance", {
                    parent: "root",
                    url: "/attendance",
                   // abstract: true,
                    // template: "<ui-view/>",
                    views: {
                        "contentView@": {
                            templateUrl: "html/templates/mainTemplate.html",
                            controller: "templateCtrl",
                            controllerAs: "tempCtrl"
                        }
                    }
                })

                      // manager attendance
                    .state( "managerAttendance", {
                        parent: "attendance",
                        url: "",
                        // abstract: true
                        // template: "<ui-view/>"
                        views: {
                            "mainView" : {
                                templateUrl: "html/views/manager/managerAttendance.html",
                                controller: "managerAttendanceCtrl",
                                controllerAs: "manAttCtrl"
                            }
                        }
                    })
                    
                      // associate attendance
                    .state( "associateAttendance", {
                        parent: "attendance",
                        url: "",
                        views: {
                            "mainView": {
                                templateUrl: "html/views/associate/associateAttendance.html",
                                controller: "associateAttendenceCtrl",
                                controllerAs: "assocAttCtrl"
                            } 
                        }
                    })

            //   // superadmin page
			// .state( "super", {
			// 	url: "/super",
            //     views: {
            //         "topBarView": {
            //             templateUrl: "html/views/topBar.html"
            //         },
            //         "mainView": {
    		// 		    templateUrl: "html/views/superadmin/super.html",
	    	// 		    controller: "superCtrl as suCtrl"
            //         }
            //     }
			// })
            //       // superadmin view all attendance
            //     .state( "superAttendance", {
            //         url: "^/attendance",
            //         parent: "super",
            //         views: {
            //             "mainSuperView": {
            //                 templateUrl: "html/views/superadmin/superAttendance.html",
            //                 controller: "superAttendanceCtrl as supAttCtrl"
            //             }
                        
            //         }
            //     })

            //   // admin page
			// .state( "admin", {
			// 	url: "/admin",
            //     views: {
            //         "topBarView": {
            //             templateUrl: "html/views/topBar.html"
            //         },
            //         "mainView": {
    		// 		    templateUrl: "html/views/admin/admin.html",
	    	// 		    controller: "adminCtrl as adCtrl"
            //         }
            //     }
			// })
			// 	 // admin view all attendance
	        //         .state( "adminAttendance", {
	        //             url: "/attendance",
	        //             parent: "admin",
	        //             views: {
	        //                 "mainAdminView": {
	        //                     templateUrl: "html/views/admin/associate-attendance-table.html",
	        //                     controller: "adminAttendanceCtrl as adAttCtrl"
	        //                 }
	        //             }
	        //         })
			
            //   // associate page
			// .state( "assoc", {
			// 	url: "/assoc",
            //     views: {
            //         "topBarView": {
            //             templateUrl: "html/views/topBar.html"
            //         },
            //         "mainView": {
    		// 		    templateUrl: "html/views/associate/associate.html",
	    	// 		    controller: "associateCtrl as assoCtrl"
            //         }
            //     }
			// })

			// //.....................................................................
			// // superadmin view all attendance
            //     .state( "assocAttendance", {
            //         url: "^/weeklyattendence",
            //         parent: "assoc",
            //         views: {
            //             "mainAssociateView": {
            //                 templateUrl: "html/views/associate/associateWeeklyAttendence.html",
            //                 controller: "associateWeeklyAttendenceCtrl as assWeekAttCtrl"
            //             }
                        
            //         }
            //     })
			//........................................................................

			
            /*
             * User can change their password
             */
            // superAdmin update information
            //     .state( "SUupdateInfo", {
            //         url: "^/updateInformation-sa",
            //         parent: "super",
            //         views: {
            //             "mainSuperView": {
            //                 templateUrl: "html/views/updateInformation.html",
            //                 controller: "updateInfoCrtl as uInfoctrl"
            //             }
            //         }
            //     })
            // // Admin update information
            //     .state( "ADupdateInfo", {
            //         url: "^/updateInformation-a",
            //         parent: "admin",
            //         views: {
            //             "mainAdminView": {
            //                 templateUrl: "html/views/updateInformation.html",
            //                 controller: "updateInfoCrtl as uInfoctrl"
            //             } 
            //         }
            //     })
			// // Associate update information
            //     .state( "ASupdateInfo", {
            //         url: "^/updateInformation-as",
            //         parent: "assoc",
            //         views: {
            //             "mainAssociateView": {
            //                 templateUrl: "html/views/updateInformation.html",
            //                 controller: "updateInfoCrtl as uInfoctrl"
            //             }
            //         }
            //     })

	});

      // theme config
    sms.config( function($mdThemingProvider) {
        $mdThemingProvider.theme("default")
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
