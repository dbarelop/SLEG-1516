angular.module("Prac3SLEGApp.controllers", []).controller("ProgramsCtrl", function($scope, ProgramsService) {

    $scope.program_id = "";
    $scope.program_name = "";

    $scope.search = function() {
        ProgramsService.getProgram($scope.program_id).then(null, null, function(data) {
            console.log(data);
            $scope.result = data;
        });
    };

    $scope.search_by_name = function() {
        ProgramsService.getProgramByName($scope.program_name).then(null, null, function(data) {
            console.log(data);
            $scope.result = data;
        });
    };

});
