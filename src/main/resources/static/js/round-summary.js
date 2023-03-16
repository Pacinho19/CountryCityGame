var stompClient = null;
var privateStompClient = null;

let socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    var gameId = document.getElementById('gameId').value;
    var roundId = document.getElementById('roundId').value;

    privateStompClient.subscribe('/next-round/' + gameId, function (result) {
        window.location.href = '/country-city-game/games/' + gameId;
    });

    privateStompClient.subscribe('/player-ready-alert/' + gameId + "/round/" + roundId, function (result) {
        updateResultTable(roundId);
    });

     privateStompClient.subscribe('/game-over/' + gameId , function (result) {
          window.location.href = '/country-city-game/games/' + gameId + "/over";
     });
});

stompClient = Stomp.over(socket);

function updateResultTable(roundId) {

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            $("#resultTable").replaceWith(xhr.responseText);
        }
    }
    xhr.open('GET', "/country-city-game/games/" + document.getElementById("gameId").value + "/round/" + roundId + "/reload-results", true);
    xhr.send(null);
}
