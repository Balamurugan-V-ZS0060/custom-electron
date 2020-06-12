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
        //stompClient.subscribe('/topic/greetings', function (greeting) {
          //  showGreeting(JSON.parse(greeting.body).content);
        //});
        
        stompClient.subscribe('/topic/broadcast', function (greeting) {
            showGreeting(JSON.parse(greeting.body).text);
        });
        
        stompClient.subscribe('/user/queue/message', function (greeting) {
        	showGreeting(JSON.parse(greeting.body).text);
        	window.location.href = "./login?logout=true";
        	//$( "#logoutlink" ).click();
        });
        
      //stompClient.subscribe('/topic/greetings', function (greeting) {
          //showGreeting(greeting.body);
      //});
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendHello2() {
    stompClient.send("/app/hello2", {}, $("#name").val());
}

function sendHello() {
    stompClient.send("/app/hello1", {}, JSON.stringify({'name': $("#name").val()}));
}

function sendHi() {
    stompClient.send("/app/message", {}, JSON.stringify({to:'jithu.p@zucisystems.com','text': $("#name").val()}));
}

function sendGreet() {
    stompClient.send("/app/sendgreet", {}, JSON.stringify({'name': $("#name").val()}));
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendMsg(){
	$.ajax({
		type : "POST",
		url : "http://localhost:8099/electron/broadcast",
		data : JSON.stringify({
			to : "",
			text: $("#messagetosend").val()
		}),
		success : function(data, status) {
			alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		},
		error : function(data, status) {
			alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		},
		dataType : "json",
		contentType : "application/json"
	});
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendHi(); });
    $( "#deliver" ).click(function() { sendMsg(); });
});

