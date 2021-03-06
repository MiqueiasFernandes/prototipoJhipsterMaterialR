(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBModeloGenerico', TBModeloGenerico);

    TBModeloGenerico.$inject = ['$resource'];

    function TBModeloGenerico ($resource) {
        var resourceUrl =  'api/t-b-modelo-genericos/:id';

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
