
    angular
        .module( "sms" )
        .factory( "batchAddFactory", batchAdd );

    function batchAdd() {

            // data
        var newAssociates = [];

        var service = {
            addOneAssociate: addOneAssociate,
            getNewAssociates: getNewAssociates,
            resetAssociates: resetAssociates
        }
        return service;

            // functions
        function addOneAssociate(newAssociate) {
            newAssociates.push(newAssociate);
        }

        function getNewAssociates() {
            return newAssociates;
        }

        function resetAssociates() {
            newAssociates = [];
        }

    }
    