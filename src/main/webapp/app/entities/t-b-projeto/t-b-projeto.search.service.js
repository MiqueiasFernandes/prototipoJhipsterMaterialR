(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBProjetoSearch', TBProjetoSearch);

    TBProjetoSearch.$inject = ['$resource'];

    function TBProjetoSearch($resource) {
        var resourceUrl =  'api/_search/t-b-projetos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
