angular.module("StocksApp", []).controller("StocksController", function($scope){

    var ws = new WebSocket("ws://localhost:9000/stocks");
    var stocks = this;
    $scope.infoList = {};

    // what happens when user enters message
    $scope.addStock = function(symbol) {
        var request  = {
        "action" : "Add",
        "symbol" : symbol
        };
        ws.send(JSON.stringify(request));
    };

    $scope.removeStock = function(symbol){
        var request ={
            "action" : "Remove",
            "symbol" : symbol
        };
        ws.send(JSON.stringify(request))
    };

    ws.onopen = function(){
        $scope.open = true
        console.log("Socket has been opened!");
    };

    ws.onmessage = function(msg) {
        var data = msg.data;
        var dataObj = JSON.parse(data);
        $scope.infoList = dataObj.infoList;
        $scope.$digest();
    };
});