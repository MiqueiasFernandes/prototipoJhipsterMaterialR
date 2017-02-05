(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBUsuario', TBUsuario);

    TBUsuario.$inject = ['$resource'];

    function TBUsuario ($resource) {
        var resourceUrl =  'api/t-b-usuarios/:id';

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
