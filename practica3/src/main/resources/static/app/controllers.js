angular.module("Prac3SLEGApp.controllers", []).controller("ProgramsCtrl", function($scope, ProgramsService) {

    $scope.programId = "";
    $scope.programName = "";
    $scope.numPrograms = "";

    $scope.getNumPrograms = function() {
        ProgramsService.getNumPrograms().then(function(data) {
            $scope.numPrograms = data;
        }, function(err) {
            console.log(err);
        });
    };

    $scope.getPrograms = function() {
        ProgramsService.getPrograms().then(function(data) {
            $scope.result = data;
        }, function(err) {
            console.log(err);
        });
    };

    $scope.search = function() {
        ProgramsService.getProgram($scope.programId).then(function(data) {
            $scope.result = data;
        }, function(err) {
            console.log(err);
        });
    };

    $scope.searchByName = function() {
        ProgramsService.getProgramByName($scope.programName).then(function(data) {
            $scope.result = data;
        }, function(err) {
            console.log(err);
        });
    };

    $scope.getNumPrograms();

});
