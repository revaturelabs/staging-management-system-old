    
    angular
        .module( "sms" )
        .service( "taskTypeService", taskTypeService );
    /**
     * @description AngularJs service for taskTypes
     */ 
    function taskTypeService( $resource ) {
         /**@var {object} Resoruce object for connecting to the REST service */
        var taskTypeResource = $resource("/api/v1/taskType");
        var tts = this;
        /**
         * @description Gets all task types from the database.
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        tts.getAll = function(success, error) {
            taskTypeResource.query(success, error);
        }
    }