
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
    
