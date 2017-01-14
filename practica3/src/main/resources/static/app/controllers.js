angular.module("Prac3SLEGApp.controllers", []).controller("ProgramsCtrl", function($scope, ProgramsService) {

    $scope.programId = "";
    $scope.programName = "";
    $scope.numPrograms = "";

    $scope.getNumPrograms = function() {
        ProgramsService.getNumPrograms().then(null, null, function(data) {
            $scope.numPrograms = data;
        });
    };

    $scope.getPrograms = function() {
        ProgramsService.getPrograms().then(null, null, function(data) {
            $scope.result = data;
        });
    };

    $scope.search = function() {
        ProgramsService.getProgram($scope.programId).then(null, null, function(data) {
            $scope.result = data;
        });
    };

    $scope.searchByName = function() {
        ProgramsService.getProgramByName($scope.programName).then(null, null, function(data) {
            $scope.result = data;
        });
    };

    $scope.getNumPrograms();

});
