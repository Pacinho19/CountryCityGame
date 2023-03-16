var stompClient = null;
var privateStompClient = null;

let socket = new SockJS('/ws');
privateStompClient = Stomp.over(socket);
privateStompClient.connect({}, function (frame) {
    var gameId = document.getElementById('gameId').value;
    privateStompClient.subscribe('/reload-board/' + gameId, function (result) {
        var gameState = JSON.parse(result.body);
        var playerName = gameState.playerName;
        var isPlayer = playerName == document.getElementById('playerName').value;

        if (isPlayer) updateBoard();
        else showPlayerAnswerInfo(playerName);

        endRound(gameState, isPlayer);
    });

    privateStompClient.subscribe('/round-summary/' + gameId, function (result) {
        roundId = result.body;
        window.location.href = '/country-city-game/games/' + gameId + "/round/" + roundId + "/summary";
    });

    privateStompClient.subscribe('/unknown-answers/' + gameId, function (result) {
        window.location.href = '/country-city-game/games/' + gameId + "/unknown-answers";
    });
});

stompClient = Stomp.over(socket);

function endRound(gameState, isPlayer) {
    if (gameState.message != null && gameState.connection) {
        endRoundTimer(isPlayer);
    }
    else if (gameState.connection == false) {
        showConnectionLostAlert(gameState.message);
        setTimeout(function () { goToGamePage(); }, 3000);
    }
}

function goToGamePage() {
    var gameId = document.getElementById('gameId').value;
    window.location.href = '/country-city-game/games/' + gameId;
}

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

function showPlayerAnswerInfo(playerName) {
    document.getElementById('playerAnswerName').innerHTML = playerName + ' send his answer!';
    $("#answer-player-alert").fadeTo(2000, 500).slideUp(500, function () {
        $("#answer-player-alert").slideUp(500);
    });
}

function endRoundTimer(isPlayer) {
    console.log('endRoundTimer start');

    document.getElementById("endRoundGiv").style.display = 'block';
    document.getElementById('endRoundText').classList.add("zoom-in-out-box");

    var max = document.getElementById("progressBarEndRound").max;
    var timeleft = max;
    var downloadTimer = setInterval(function () {
        var nextPbValue = timeleft - 1;
        document.getElementById("progressBarEndRound").value = nextPbValue;
        document.getElementById("roundStartTime").innerHTML = nextPbValue + ' seconds';
        timeleft -= 1;

        if (timeleft <= 0) {
            clearInterval(downloadTimer);
            if (!isPlayer) sendAnswer();
            setTimeout(()=>checkOpponentLeft(), 500);
        }
    }, 1000);
}

function sendAnswer() {
    document.getElementById('answerForm').submit();
}

function showConnectionLostAlert(message) {
    document.getElementById('warningMessageDiv').classList.remove("zoom-in-out-box");
    document.getElementById("warningMessageTxt").innerHTML = message;
}


function checkOpponentLeft() {
    document.getElementById("warningMessageDiv").style.display = 'block';
    document.getElementById("endRoundGiv").style.display = 'none';
    document.getElementById('warningMessageDiv').classList.add("zoom-in-out-box");
}
