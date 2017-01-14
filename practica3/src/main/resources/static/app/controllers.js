angular.module("Prac3SLEGApp.controllers", []).controller("ProgramsCtrl", function($scope, ProgramsService) {

    $scope.dosbox = false;

    $scope.programId = "";
    $scope.programName = "";
    $scope.numPrograms = "";
    $scope.tape = "";

    $scope.getNumPrograms = function() {
        $scope.dosbox = true;
        ProgramsService.getNumPrograms().then(function(data) {
            $scope.numPrograms = data;
        }, function(err) {
            console.log(err);
        }).finally(function() {
            $scope.dosbox = false;
        });
    };

    $scope.getPrograms = function() {
        $scope.dosbox = true;
        ProgramsService.getPrograms().then(function(data) {
            $scope.results = data;
        }, function(err) {
            console.log(err);
        }).finally(function() {
            $scope.dosbox = false;
        });
    };

    $scope.search = function() {
        $scope.dosbox = true;
        ProgramsService.getProgram($scope.programId).then(function(data) {
            $scope.result = data;
        }, function(err) {
            console.log(err);
        }).finally(function() {
            $scope.dosbox = false;
        });
    };

    $scope.searchByName = function() {
        $scope.dosbox = true;
        ProgramsService.getProgramByName($scope.programName).then(function(data) {
            $scope.result = data;
        }, function(err) {
            console.log(err);
        }).finally(function() {
            $scope.dosbox = false;
        });
    };

    $scope.searchByTape = function() {
        $scope.dosbox = true;
        ProgramsService.getProgramByTape($scope.tape).then(function(data) {
            $scope.results = data;
        }, function(err) {
            console.log(err);
        }).finally(function() {
            $scope.dosbox = false;
        })
    };

    $scope.getNumPrograms();

});
