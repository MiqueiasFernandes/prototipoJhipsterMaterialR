(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBModeloExclusivoSearch', TBModeloExclusivoSearch);

    TBModeloExclusivoSearch.$inject = ['$resource'];

    function TBModeloExclusivoSearch($resource) {
        var resourceUrl =  'api/_search/t-b-modelo-exclusivos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
