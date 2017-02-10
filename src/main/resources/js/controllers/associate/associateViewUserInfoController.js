
 angular
        .module( "sms" )
        .controller( "associateViewUserInfoCtrl", associateViewUserInfoCtrl );

    function associateViewUserInfoCtrl( $scope, $filter, loginService, skillService, userService, taskTypeService ) {
        var vic = this;

          // bindables
            // data
        vic.user = loginService.getUser();
        vic.showChips = false;
        vic.selectedSkills = vic.user.skill;

            //functions
        vic.toast = toast;
        vic.getTaskTypes = getTaskTypes;
        vic.getSkills = getSkills;
        vic.divideTasks = divideTasks;
        vic.formatTaskListItemTitle = formatTaskListItemTitle;
        vic.formatTaskListItemContent = formatTaskListItemContent;
        vic.divideEvents = divideEvents;
        vic.addCert = addCert;
        vic.editSkills = editSkills;
        vic.updateSkills = updateSkills;
        vic.joinArrayBySkill = joinArrayBySkill;
        vic.transformChip = transformChip;
        vic.skillSearch = skillSearch;
        vic.createFilterFor = createFilterFor;

          // initialization
        vic.getTaskTypes();
        vic.getSkills();
        vic.divideTasks();
        vic.divideEvents();

          // functions
            // calls global toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }   
        
            // retrieves list of all task types
        function getTaskTypes() {
            taskTypeService.getAll( function(response) {
                vic.taskTypes = response;
            });
        }

            // retrieves list of all skills
        function getSkills() {
            skillService.getAll( function(response) {
                vic.skills = response;
            }, function(){});
        }

            // divides user tasks into categories
              // sets flag if certification is scheduled for the future
        function divideTasks() {
            var allTasks = {};
            var newCert = true;
            var today = new Date();
            vic.user.tasks.forEach( function(task) {
                if ( Object.keys(allTasks).indexOf(task.taskType.type) == -1 ) {
                    allTasks[task.taskType.type] = [task];
                } else {
                    allTasks[task.taskType.type].push(task);
                }
                if (task.taskType.type.toLowerCase() == "certification") {
                    if (task.date > today.getTime()) {
                        newCert = false;
                    }
                }
            });
            vic.allTasks = allTasks;
            vic.newCert = newCert;
        }

            // formats task list title by type
        function formatTaskListItemTitle(task) {
            if ( task.taskType.type.toLowerCase() == "panel" ) {
                return (task.passed) ? "Passed" : "Failed";
            } else {
                return task.note;
            }
        }

            // formates task list content by type
        function formatTaskListItemContent(task) {
            var today = new Date();
            if ( task.taskType.type.toLowerCase() == "project" ) {
                return "Since " + $filter( "date" )( task.date, "MMMM dd, yyyy" );
            } else {
                var returnString;
                if ( today.getTime() < task.date ) {
                  // scheduled for the future
                    returnString = "Scheduled for ";
                } else {
                  // in the past
                    returnString = "On ";
                }
                return returnString + $filter( "date" )( task.date, "MMMM dd, yyyy" ); 
            }
        }

            // divides user events into categories
        function divideEvents() {
            var allEvents = {};
            vic.user.events.forEach( function(event) {
                if ( Object.keys(allEvents).indexOf(event.type.type) == -1 ) {
                    allEvents[event.type.type] = [event];
                } else {
                    allEvents[event.type.type].push(event);
                }
            });
            vic.allEvents = allEvents;
        }

        function addCert() {
            // console.log( "Add cert here." );
        }

            // swaps showing skill list and skill chips
        function editSkills() {
            vic.showChips = !vic.showChips;
        }

            // update user object with new skillset
        function updateSkills() {
            userService.update( vic.user, function() {
                vic.toast( "Skills updated." );
                vic.showChips = false;
            }, function() {
                vic.toast( "Skills failed to update." );
            });
        }

            // reformats how an array of objects is joined
        function joinArrayBySkill(elem) {
            return elem.skill;
        }

            // for use by chips
        function transformChip(chip) {
            return chip;
        }

            // for use by skill search autocomplete
        function skillSearch(text) {
            var results = text ? vic.skills.filter( vic.createFilterFor( text ) ) : [];
            return results;
        }

            // filter for use by skill serach autocomplete
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(skill) {
                return (skill.skill.toLowerCase().indexOf(lowercaseQuery) === 0);
            }
        }
    }