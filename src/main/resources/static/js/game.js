var stompClient = null;
var privateStompClient = null;

let socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    var gameId = document.getElementById('gameId').value;
    privateStompClient.subscribe('/reload-board/' + gameId, function (result) {
        playerName = result.body;
        if(playerName==document.getElementById('playerName').value) updateBoard();
        else showPlayerAnswerInfo(playerName);
    });

     privateStompClient.subscribe('/next-round/' + gameId, function (result) {
             window.location.href = '/country-city-game/games/' + gameId;
     });
});

stompClient = Stomp.over(socket);

function updateBoard() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            $("#board").replaceWith(xhr.responseText);
        }
    }
    xhr.open('GET', "/country-city-game/games/" + document.getElementById("gameId").value + "/board/reload", true);
    xhr.send(null);
}

function showPlayerAnswerInfo(playerName){
 document.getElementById('playerAnswerName').innerHTML = playerName + ' send his answer!';
                $("#answer-player-alert").fadeTo(2000, 500).slideUp(500, function() {
                  $("#answer-player-alert").slideUp(500);
                });
}
