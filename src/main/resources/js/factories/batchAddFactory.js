
    angular
        .module( "sms" )
        .factory( "batchAddFactory", batchAdd );
/**@description Handles the functionality for adding a batch */
    function batchAdd() {

            // data
            /**@var{array} Variable that holds the new associates */
        var newAssociates = [];
        /**@var{object} Variable that holds the functions needed to add new associates */
        var service = {
            addOneAssociate: addOneAssociate,
            getNewAssociates: getNewAssociates,
            resetAssociates: resetAssociates
        }
        return service;

            // functions
        /**
         * @description Adds an associate to the array.
         */
        function addOneAssociate(newAssociate) {
            newAssociates.push(newAssociate);
        }
        /**
         * @description Gets the new associates array.
         */
        function getNewAssociates() {
            return newAssociates;
        }
        /**
         * @description Empties the array of new associates.
         */
        function resetAssociates() {
            newAssociates = [];
        }

    }
    