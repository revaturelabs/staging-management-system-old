
    var sms = angular.module( "sms", ["ngAria", "ngMessages", "ngAnimate", "ngMaterial", "ngResource", "ngCookies", "md.data.table", "material.components.expansionPanels", "ui.router"]);

      // global constants
    sms.constant( "weekdays", [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ] );

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
<<<<<<< HEAD
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
                
                 // superadmin view all tasks
                .state( "superTask", {
                    url: "^/task",
                    parent: "super",
                    views: {
                        "mainSuperView": {
                            templateUrl: "html/views/superadmin/superTask.html",
                            controller: "superTaskCtrl as supTaskCtrl"
=======
                    views: {
                        "contentView@": {
                            templateUrl: "html/views/login.html",
                            controller: "loginCtrl",
                            controllerAs: "logCtrl"
                        },
                        "topBarView@": {
                            template: ""
>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
                        }
                    }
                })

<<<<<<< HEAD
                  // attendance
                .state( "attendance", {
                    parent: "root",
                    url: "/attendance",
                    //abstract: true,
                    // template: "<ui-view/>",
                    views: {
                        "contentView@": {
                            templateUrl: "html/templates/mainTemplate.html",
                            controller: "templateCtrl",
                            controllerAs: "tempCtrl"
                        }
                    },
                    onEnter : function( loginService, $state, $timeout ) {
                            var user = loginService.getUser();
                            var userRole = "";
                            if (user != undefined) {
                                userRole = user.userRole.name;
                            }
                            $timeout( function() {
                                if (userRole == "associate"){
                                    $state.go("associateAttendance");
                                } else if ( ( userRole == "superAdmin") || ( userRole == "admin" ) ) {
                                    $state.go("managerAttendance");
                                } else {
                                    $state.go("login");
                                }
                            }, 100);
                        }
                     })

=======
                 // superadmin view all tasks
                .state( "superTask", {
                    url: "^/task",
                    parent: "super",
                    views: {
                        "mainSuperView": {
                            templateUrl: "html/views/superadmin/superTask.html",
                            controller: "superTaskCtrl as supTaskCtrl"
                        }
                        
                    }
                })

                  // attendance
                .state( "attendance", {
                    parent: "root",
                    url: "/attendance",
                    //abstract: true,
                    // template: "<ui-view/>",
                    views: {
                        "contentView@": {
                            templateUrl: "html/templates/mainTemplate.html",
                            controller: "templateCtrl",
                            controllerAs: "tempCtrl"
                        }
                    },
                    onEnter : function( loginService, $state, $timeout ) {
                            var user = loginService.getUser();
                            var userRole = "";
                            if (user != undefined) {
                                userRole = user.userRole.name;
                            }
                            $timeout( function() {
                                if (userRole == "associate"){
                                    $state.go("associateAttendance");
                                } else if ( ( userRole == "superAdmin") || ( userRole == "admin" ) ) {
                                    $state.go("managerAttendance");
                                } else {
                                    $state.go("login");
                                }
                            }, 100);
                        }
                     })

>>>>>>> 8bd20877974ed80df91287cb995127748c2d5238
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
	});

      // theme config
    sms.config( function($mdThemingProvider) {
        $mdThemingProvider.theme("default")
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
