
    angular
        .module( "sms" )
        .service( "batchTypeService", batchTypeService );
/**
 * @description AngularJs service for batchTypeService
 */
    function batchTypeService( $resource ) {
         /**@var {object} Resoruce object for connecting to the REST service */
        var batchTypeResource = $resource("api/v1/batchType");
        var bts = this;
        /**
         * @description Gets all batchTypes from the database
         * @param {function} success Function that runs on successful retrieval.
         * @param {function} error Function that runs on failed retrieval.
         */
        bts.getAll = function(success, error) {
            batchTypeResource.query(success, error);
        }
    }