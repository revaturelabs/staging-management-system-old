var sms = angular.module("sms");
sms.filter("iconFilter", function(){
	return function(day){
		
		// a list of all usable icons
        var iconData = [
            {name: 'done'  , color: "#00A" },
            {name: 'close', color: "#A00" },
            {name: 'done_all' , color: "rgb(89, 226, 168)" },

            {name: '     ' , color: "#777" }

         ];
        
        if(day.verified){
        	day.icon = iconData[2];
        }
        else if(day.checkedIn){
        	day.icon = iconData[0];
        }
        else {
        	day.icon = iconData[1];
        }
		
		return day;
	}
})