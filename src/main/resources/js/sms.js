
    var sms = angular.module( "sms", ["ngAria", "ngMessages", "ngAnimate", "ngMaterial", "ngResource", "ngCookies", "ui.router"]);

      // URL routing
    sms.config( function( $stateProvider, $urlRouterProvider ) {

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
	});

      // theme config
    sms.config( function($mdThemingProvider) {

        // var revOrangeMap = $mdThemingProvider.extendPalette("deep-orange", {
        //     "800": "#D9510D",
        //     "500": "#F26925"
        // });

        // var revBlueMap = $mdThemingProvider.extendPalette("blue-grey", {
        //     "A200": "#72A4C2",
        //     "100" : "#C9DCE8"
        // });

        // $mdThemingProvider.definePalette("revOrange", revOrangeMap);
        // $mdThemingProvider.definePalette("revBlue", revBlueMap);
            
        $mdThemingProvider.theme("default")
            // .primaryPalette("revOrange")
            // .accentPalette("revBlue");
            .primaryPalette("indigo")
            .accentPalette("pink");
    });
    