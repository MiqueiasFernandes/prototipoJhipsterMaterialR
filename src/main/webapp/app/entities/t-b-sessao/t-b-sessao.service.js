(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBSessao', TBSessao);

    TBSessao.$inject = ['$resource'];

    function TBSessao ($resource) {
        var resourceUrl =  'api/t-b-sessaos/:id';

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
