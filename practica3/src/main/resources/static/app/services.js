angular.module("Prac3SLEGApp.services").service("ProgramsService", function($q, $http) {
    var service = {}, listener = $q.defer();

    service.getNumPrograms = function() {
        $http.get('http://localhost:8080/programs/count').then(function(res) {
            listener.notify(res.data);
        }, function(err) {
            console.log(err);
        });
        return listener.promise;
    };

    service.getPrograms = function() {
        $http.get('http://localhost:8080/programs').then(function(res) {
            listener.notify(res.data);
        }, function(err) {
            console.log(err);
        });
        return listener.promise;
    };

    service.getProgram = function(id) {
        $http.get('http://localhost:8080/program/' + id).then(function(res) {
            listener.notify(res.data);
        }, function(err) {
            console.log(err);
        });
        return listener.promise;
    };

    service.getProgramByName = function(name) {
        $http.get('http://localhost:8080/program/name/' + name).then(function(res) {
            listener.notify(res.data);
        }, function(err) {
            console.log(err);
        });
        return listener.promise;
    };

    return service;
});
