(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBModeloExclusivo', TBModeloExclusivo);

    TBModeloExclusivo.$inject = ['$resource'];

    function TBModeloExclusivo ($resource) {
        var resourceUrl =  'api/t-b-modelo-exclusivos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
