var stompClient = null;
var privateStompClient = null;

let socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
        var gameId = document.getElementById('gameId').value;
        var roundId = document.getElementById('roundId').value;

        privateStompClient.subscribe('/round-summary/' + gameId, function (result) {
                roundId = result.body;
                window.location.href = '/country-city-game/games/' + gameId + "/round/" + roundId + "/summary";
        });

});

stompClient = Stomp.over(socket);
