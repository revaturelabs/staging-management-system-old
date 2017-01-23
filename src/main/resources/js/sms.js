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
                    //abstract: true,
                    // template: "<ui-view/>",
                    views: {
                        "contentView@": {
                            templateUrl: "html/templates/mainTemplate.html",
                            controller: "templateCtrl",
                            controllerAs: "tempCtrl"
                        }
                    },
                    onEnter : function(loginService, $state, $timeout){
                            var userRole = loginService.getUser().userRole.name;
                            $timeout(function(){
                                if (userRole == "associate"){
                                    $state.go("associateAttendance");
                            } else {
                                $state.go("managerAttendance");
                            }
                            })
                            
                            
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

	});

      // theme config
    sms.config( function($mdThemingProvider) {
        $mdThemingProvider.theme("default")
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
