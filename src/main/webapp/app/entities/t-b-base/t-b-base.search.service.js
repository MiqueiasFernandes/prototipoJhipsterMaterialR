(function() {
    'use strict';

    angular
        .module('projeto1App')
        .factory('TBBaseSearch', TBBaseSearch);

    TBBaseSearch.$inject = ['$resource'];

    function TBBaseSearch($resource) {
        var resourceUrl =  'api/_search/t-b-bases/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
