(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBUsuarioSearch', TBUsuarioSearch);

    TBUsuarioSearch.$inject = ['$resource'];

    function TBUsuarioSearch($resource) {
        var resourceUrl =  'api/_search/t-b-usuarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
