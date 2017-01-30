var sms = angular.module("sms")
				 .controller( "updateCertification", updateCertification );

function updateCertification( $scope, $mdDialog, userService, cert, user) {
	var ucc = this;
	
	//bindables
	/**@prop {object} user Variable holding user object of user to be updated. */
	ucc.user = user;
	/**@prop {obkect} cert Variable holding certification object */
	ucc.cert = cert;
	
	/**@prop {Date} oldDate Variable holding date of task for reference. */
	ucc.oldDate = new Date(cert.date);
	/**@prop {string} oldNote Variable holding note of task for reference. */
	ucc.oldNote = cert.note;
	/**@prop {boolean} oldPassed Variable holding status of task for reference. */
	ucc.oldPassed = cert.passed;
	/**@prop {Date} newDate Variable holding date value to be updated. */
	ucc.newDate = new Date(cert.date);
    /**@prop {string} newNote Variable holding note value to be updated. */
	ucc.newNote = cert.note;
    /**@prop {boolean} passed Variable with status of task*/
	ucc.newPassed = cert.passed;
	
	//functions
	/**@var {function} submit function reference variable. */
	ucc.submit = submit;
	/**@var {function} cancel function reference variable. */
	ucc.cancel = cancel;
	
    /**
     * @description Called when user clicks submit button. Saves updated information and updates it in the database and closes the dialog.
     */
	function submit() {
		for(var i = 0; i < ucc.user.tasks.length;i++){
			if(ucc.user.tasks[i].id == cert.id){
				
				if(ucc.newDate != null && ucc.newDate && ucc.newDate != ""){
					cert.date = ucc.newDate.getTime();
					ucc.user.tasks[i].date = ucc.newDate.getTime();
					cert.dateDisplay = (ucc.newDate.getMonth()+1)+"/"+ucc.newDate.getDate();

				}
				
				ucc.user.tasks[i].note = ucc.newNote;
				ucc.user.tasks[i].passed = ucc.newPassed;
				
				cert.note = ucc.newNote;
				cert.passed = ucc.newPassed
			}
		}
		
		userService.update(ucc.user,function(){
			$mdDialog.hide();
		});
		
	}
	
    /**
     * @description Called when user clicks cancel button. Resets values to their original values and closes the dialog.
     */
	function cancel(){
		ucc.cert.dateDisplay = (ucc.oldDate.getMonth()+1)+"/"+ucc.oldDate.getDate();
		ucc.cert.note = ucc.oldNote;
		ucc.cert.passed = ucc.oldPassed;
		$mdDialog.hide();
	}
}
