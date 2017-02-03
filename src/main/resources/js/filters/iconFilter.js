
    angular
        .module("sms")
        .filter("iconFilter", iconFilter);
        
    function iconFilter() {
	    return function(thisObject, expr){
		
            // a list of all usable icons
            var iconData = [
                {name: 'done'  , color: "#00A", tooltip: "Checked in" },
                {name: 'close', color: "#A00", tooltip: "Absent" },
                {name: 'done_all' , color: "#59E2A8", tooltip: "Verified" },
                {name: 'looks', color: "gray"},
                {name: 'turned_in' , color: "gray"},
                {name: 'library_books', color: "gray"},
                {name: 'looks_one', color: "#E91E63"},
                {name: 'looks_two', color: "#E91E63"},
                {name: 'looks_3', color: "#E91E63"},
                {name: 'looks_4', color: "#E91E63"},
                {name: 'looks_5', color: "#E91E63"},
                {name: 'looks_6', color: "#E91E63"},
            ];
            
            //created a switch statement to see what icons we are filtering for
            switch (expr) {
                case "week":
        
                    if(thisObject.verified){
                        thisObject.icon = iconData[2];
                    }
                    else if(thisObject.checkedIn){
                        thisObject.icon = iconData[0];
                    }
                    else {
                        thisObject.icon = iconData[1];
                    }
                    break;
                    
                case "task":
                    if(thisObject.type == "Certification"){
                        thisObject.icon = iconData[3];
                    }else{
                        thisObject.icon = iconData[4];
                    }
                    break;
                    
                case "pastTask":
                    if(thisObject.type == "Certification"){
                        thisObject.icon = iconData[5];
                    }else{
                        thisObject.icon = iconData[6];
                    }
                    break;
                    
                case "notification":
    
                    var x = thisObject.number;
                    if(x == 1){
                        thisObject.icon = iconData[7];
                    }
                    if(x == 2){
                        thisObject.icon = iconData[8];
                    }
                    if(x == 3){
                        thisObject.icon = iconData[9];
                    }
                    if(x == 4){
                        thisObject.icon = iconData[10];
                    }
                    if(x == 5){
                        thisObject.icon = iconData[11];
                    }
                    if(x > 5){
                        thisObject.icon = iconData[12];
                    }
                    break;
                case "passed":
                    if ( thisObject.taskType.type.toLowerCase() != "project" ) {
                        if ( thisObject.passed ) {
                            return iconData[0].name;
                        } else {
                            return iconData[1].name;
                        }
                    } else {
                        return "";
                    }
            default:
            }
            
            return thisObject;
        }
    }