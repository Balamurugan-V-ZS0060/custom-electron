/**
 * 
 */
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/electron/notifications');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/broadcast', function (greeting) {
            showGreeting(JSON.parse(greeting.body).text);
        });
        
        stompClient.subscribe('/user/queue/message', function (greeting) {
        	var obj = JSON.parse(greeting.body);
        	if(obj.text="LOGOUT"){
        		if(window.location.href.indexOf("alreadyLoggedIn") == -1){
        			window.location.href = "./logout";		
        		}
        	}
        	
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}




$(function () {
	connect();
});

