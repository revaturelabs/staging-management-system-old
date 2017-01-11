
    var sms = angular.module( "sms" );

    sms.service( "batchTypeService", function($resource){
        var batchTypeResource = $resource("api/v1/batchType");
        var bts = this;

        bts.getAll = function(success, error) {
            batchTypeResource.query(success, error);
        };
    });