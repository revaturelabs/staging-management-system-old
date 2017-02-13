
    angular
        .module( "sms" )
        .controller( "managerViewUserInfoCtrl", managerViewUserInfoCtrl );

    function managerViewUserInfoCtrl( $scope, $filter, loginService, skillService, userService, taskTypeService ) {
        var mic = this;

          // bindables
            // data
        mic.user = $scope.$parent.manAttCtrl.user;
        mic.showChips = false;
        mic.selectedSkills = mic.user.skill;

            //functions
        mic.toast = toast;
        mic.getTaskTypes = getTaskTypes;
        mic.getSkills = getSkills;
        mic.divideTasks = divideTasks;
        mic.formatTaskListItemTitle = formatTaskListItemTitle;
        mic.formatTaskListItemContent = formatTaskListItemContent;
        mic.divideEvents = divideEvents;
        mic.addCert = addCert;
        mic.editSkills = editSkills;
        mic.updateSkills = updateSkills;
        mic.joinArrayBySkill = joinArrayBySkill;
        mic.transformChip = transformChip;
        mic.skillSearch = skillSearch;
        mic.createFilterFor = createFilterFor;

          // initialization
        mic.getTaskTypes();
        mic.getSkills();
        // mic.divideTasks();
        // mic.divideEvents();

          // functions
            // calls global toast function
        function toast( message ) {
            $scope.$emit( "toastMessage", message );
        }   
        
            // retrieves list of all task types
        function getTaskTypes() {
            taskTypeService.getAll( function(response) {
                mic.taskTypes = response;
            }, function(error){
            });
        }

            // retrieves list of all skills
        function getSkills() {
            skillService.getAll( function(response) {
                mic.skills = response;
                console.log("runs");
            }, function(){});
        }

            // divides user tasks into categories
              // sets flag if certification is scheduled for the future
        function divideTasks() {
            var allTasks = {};
            var newCert = true;
            var today = new Date();
            mic.user.tasks.forEach( function(task) {
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
            mic.allTasks = allTasks;
            mic.newCert = newCert;
        }

            // formats task list title by type
        function formatTaskListItemTitle(task) {
            if ( task.taskType.type.toLowerCase() == "panel" ) {
                return (task.passed) ? "Passed" : "Failed";
            } else {
                return task.note;
            }
        }

            // formats task list content by type
        function formatTaskListItemContent(task) {
            /*var today = new Date();
            if ( task.taskType.type.toLowerCase() == "project" ) {*/
                return $filter( "date" )( task.date, "MMMM dd, yyyy" );
            /*} else {
                return $filter( "date" )( task.date, "MMMM dd, yyyy" ); 
            }*/
        }

            // divides user events into categories
        function divideEvents() {
            var allEvents = {};
            mic.user.events.forEach( function(event) {
                if ( Object.keys(allEvents).indexOf(event.type.type) == -1 ) {
                    allEvents[event.type.type] = [event];
                } else {
                    allEvents[event.type.type].push(event);
                }
            });
            mic.allEvents = allEvents;
        }

        function addCert() {
            // console.log( "Add cert here." );
        }

            // swaps showing skill list and skill chips
        function editSkills() {
            mic.showChips = !mic.showChips;
        }

            // update user object with new skillset
        function updateSkills() {
            userService.update( mic.user, function() {
                mic.toast( "Skills updated." );
                mic.showChips = false;
            }, function() {
                mic.toast( "Skills failed to update." );
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
            var results = text ? mic.skills.filter( mic.createFilterFor( text ) ) : [];
            return results;
        }

            // filter for use by skill serach autocomplete
        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);

            return function filterFn(skill) {
                return (skill.skill.toLowerCase().indexOf(lowercaseQuery) === 0);
            }
        }

          // watcher
        $scope.$on( "newSelectedUser", function( event, data ) {
            mic.user = data;
            /*mic.getTaskTypes();
            mic.getSkills();*/
            mic.divideTasks();
            mic.divideEvents();
        })
    }