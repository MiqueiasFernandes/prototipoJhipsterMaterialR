(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBProjeto', TBProjeto);

    TBProjeto.$inject = ['$resource'];

    function TBProjeto ($resource) {
        var resourceUrl =  'api/t-b-projetos/:id';

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
