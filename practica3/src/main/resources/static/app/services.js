angular.module("Prac3SLEGApp.services").service("ProgramsService", function($q, $http) {
    var service = {};

    service.getNumPrograms = function() {
        var listener = $q.defer();
        $http.get('http://localhost:8080/programs/count').then(function(res) {
            listener.resolve(res.data);
        }, function(err) {
            listener.reject(err);
        });
        return listener.promise;
    };

    service.getPrograms = function() {
        var listener = $q.defer();
        $http.get('http://localhost:8080/programs').then(function(res) {
            listener.resolve(res.data);
        }, function(err) {
            listener.reject(err);
        });
        return listener.promise;
    };

    service.getProgram = function(id) {
        var listener = $q.defer();
        $http.get('http://localhost:8080/program/' + id).then(function(res) {
            listener.resolve(res.data);
        }, function(err) {
            listener.reject(err);
        });
        return listener.promise;
    };

    service.getProgramByName = function(name) {
        var listener = $q.defer();
        $http.get('http://localhost:8080/program/name/' + name).then(function(res) {
            listener.resolve(res.data);
        }, function(err) {
            listener.reject(err);
        });
        return listener.promise;
    };

    service.getProgramByTape = function(tape) {
        var listener = $q.defer();
        $http.get('http://localhost:8080/programs/tape/' + tape).then(function(res) {
            listener.resolve(res.data);
        }, function(err) {
            listener.reject(err);
        });
        return listener.promise;
    };

    return service;
});
