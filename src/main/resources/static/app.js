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

function connect_server() {
    
	var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function (frame) {
        
    	setConnected(true);
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/print-count-down', function (greeting) {
        	
        	console.log('[trap1.1]greeting: ' + greeting);
        	
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
    
    //stompClient.send("/app/hello1", {}, JSON.stringify({'name': 'twenty'}));
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

// countDown
function countDown() {
    stompClient.send("/app/start-count-down", {}, JSON.stringify({'name': 'twenty'}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    
	$("form").on('submit', function (e) {
        e.preventDefault();
    });
        
    $( "#disconnect" ).click(function() { disconnect(); });    
    
    $( "#connect_server" ).click(function() { connect_server(); });
    
    //countdown
    $( "#countdown" ).click(function() { countDown(); });
    
});