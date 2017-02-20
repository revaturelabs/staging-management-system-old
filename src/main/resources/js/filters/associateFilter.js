var sms = angular.module("sms");
sms.filter("associateFilter", function(){
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