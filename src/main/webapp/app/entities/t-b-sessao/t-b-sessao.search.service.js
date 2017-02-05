(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBSessaoSearch', TBSessaoSearch);

    TBSessaoSearch.$inject = ['$resource'];

    function TBSessaoSearch($resource) {
        var resourceUrl =  'api/_search/t-b-sessaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
