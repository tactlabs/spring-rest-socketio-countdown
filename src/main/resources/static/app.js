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
        	
        	showTimeLeft(JSON.parse(greeting.body).content);
        });
    });
    
    //var room_name = $("#room_name").val();    
    //print("room name : "+room_name);
    
    //stompClient.send("/app/update-room", {}, JSON.stringify({'name': room_name}));
}

function startGame() {
	var room_name = $("#room_name").val();    
    print("room name : "+room_name);
	stompClient.send("/app/start-game", {}, JSON.stringify({'name': room_name}));
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

function extendTime() {
	
	var room_name = $("#room_name").val();    
    print(room_name);
	
    stompClient.send("/app/extend-time", {}, JSON.stringify({'name': room_name}));
}

// countDown
function countDown() {
    stompClient.send("/app/start-game", {}, JSON.stringify({'name': 'twenty'}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function print(obj){
	console.log(obj);
}

function showTimeLeft(message) {
    $("#greetings").html(message);
}

$(function () {
    
	$("form").on('submit', function (e) {
        e.preventDefault();
    });
        
    $( "#disconnect" ).click(function() { disconnect(); });    
    
    $( "#connect_server" ).click(function() { connect_server(); });
    
    $( "#start" ).click(function() { startGame(); });
    
    //countdown
    $( "#countdown" ).click(function() { countDown(); });
    
    
    // extend
    $( "#extend" ).click(function() { extendTime(); });
    
});