(function() {
    'use strict';
    angular
        .module('projeto1App')
        .factory('TBBase', TBBase);

    TBBase.$inject = ['$resource'];

    function TBBase ($resource) {
        var resourceUrl =  'api/t-b-bases/:id';

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
