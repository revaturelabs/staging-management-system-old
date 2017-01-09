
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
				url: "/home",
                views: {
                    "mainView": {
    				    templateUrl: "html/views/super.html",
	    			    controller: "superCtrl as suCtrl"
                    }
                }
			})
              // admin page
			.state( "admin", {
				url: "/home",
                views: {
                    "mainView": {
    				    templateUrl: "html/views/admin.html",
	    			    controller: "adminCtrl as adCtrl"
                    }
                }
			})
              // associate page
			.state( "assoc", {
				url: "/home",
                views: {
                    "mainView": {
    				    templateUrl: "html/views/associate.html",
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
    