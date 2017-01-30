var sms = angular.module("sms");
/**@description AngularJs filter for associates. */
sms.filter("associateFilter", function(){
	/**
	 * @description Filters the users in the database, and only returns actual associates.
	 */
	return function(users){
		var associates = [];
		if(!users){return null}
		
		users.forEach(function(user){
			if(user.userRole.name=="associate"){
				associates.push(user);
			}
		})
		return associates;
	}
})