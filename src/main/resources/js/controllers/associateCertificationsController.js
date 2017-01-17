var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state,
		loginService) {

	var acc = this;
	acc.user = loginService.getUser();
	acc.gradDate = acc.user.graduationDate;
	alert(new Date(acc.user.graduationDate));

	acc.myDate = new Date();
	acc.minDate = new Date();
	acc.maxDate = new Date(acc.myDate.getFullYear(), acc.myDate
			.getMonth() + 1, acc.myDate.getDate());
	acc.onlyWeekendsPredicate = function(date) {
		var day = date.getDay();
		return day === 0 || day === 6;
	}

});