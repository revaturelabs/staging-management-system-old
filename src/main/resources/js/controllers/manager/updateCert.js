var sms = angular.module("sms")
				 .controller( "updateCertification", updateCertification );

function updateCertification( $scope, $state, $filter, $mdDialog, userService, cert, user) {
	var ucc = this;
	
	//bindables
	ucc.user = user;
	ucc.cert = cert;
	
	ucc.oldDate = cert.dateDisplay;
	ucc.oldNote = cert.note;
	ucc.oldPassed = cert.passed;
	
	//functions
	ucc.submit = submit;
	ucc.cancel = cancel;
	
    /**
     * @description Called when user clicks submit button. Saves updated information and updates it in the database and closes the dialog.
     */
	function submit() {
		$mdDialog.hide();
	}
	
    /**
     * @description Called when user clicks cancel button. Resets values to their original values and closes the dialog.
     */
	function cancel(){
		ucc.cert.dateDisplay = ucc.oldDate;
		ucc.cert.note = ucc.oldNote;
		ucc.cert.passed = ucc.oldPassed;
		$mdDialog.hide();
	}
}
