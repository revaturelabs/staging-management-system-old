
    angular
        .module( "sms" )
        .service( "batchTypeService", batchTypeService );

    function batchTypeService( $resource ) {
        var batchTypeResource = $resource("api/v1/batchType");
        var bts = this;

        bts.getAll = function(success, error) {
            batchTypeResource.query(success, error);
        }
    }