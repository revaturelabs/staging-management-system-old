var sms = angular.module("sms");

sms.controller("associateCertificationsCtrl", function($scope, $state, $mdDialog,
		loginService) {

	var acc = this;
	acc.user = loginService.getUser();
	acc.gradDate = new Date(acc.user.graduationDate);
	acc.formattedGradDate = ((acc.gradDate.getMonth()) + 1) + "/" + acc.gradDate.getDate() + "/" + acc.gradDate.getFullYear();
	/*alert(new Date(acc.user.graduationDate));*/

	acc.myDate = new Date();
	acc.minDate = new Date();
	acc.maxDate = new Date(acc.gradDate.getFullYear(), acc.gradDate
			.getMonth() + 1, acc.gradDate.getDate());
	acc.onlyWeekendsPredicate = function(date) {
		var day = date.getDay();
		return day === 0 || day === 6;
	}
	
	acc.cancel = function() {
		$mdDialog.cancel();
	}
	
	acc.sumbitCert = function() {
		alert("Certification added, but not really");
	}

});