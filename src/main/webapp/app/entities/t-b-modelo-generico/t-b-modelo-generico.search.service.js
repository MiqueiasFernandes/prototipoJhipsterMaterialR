(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBModeloGenericoSearch', TBModeloGenericoSearch);

    TBModeloGenericoSearch.$inject = ['$resource'];

    function TBModeloGenericoSearch($resource) {
        var resourceUrl =  'api/_search/t-b-modelo-genericos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
