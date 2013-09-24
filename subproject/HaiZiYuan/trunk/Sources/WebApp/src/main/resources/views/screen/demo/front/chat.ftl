<!DOCTYPE html>
<html>
<head>
<title>Testing websockets</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
        
        昵称:<input type="text" id="nike"/><br/>
        内容:<input type="text" id="talk"/><input type="button" id="send" onClick="send();" value="发送"/>          
        
	<div id="messages"></div>
	<script type="text/javascript">
		var webSocket =  
			new WebSocket('ws://localhost:8080/hanzi/chat');

		webSocket.onerror = function(event) {
			onError(event)
		};

		webSocket.onopen = function(event) {
			onOpen(event)
		};
 
		webSocket.onmessage = function(event) {
			onMessage(event)
		};
		webSocket.onclose = function(event) {
			document.getElementById('messages').innerHTML = '连接已关闭！！<br/>';
		};

		function onMessage(event) {
			document.getElementById('messages').innerHTML += '<br />' + event.data;
		}

		function onOpen(event) {
			document.getElementById('messages').innerHTML = '已连接到服务器......<br/>';
		}

		function onError(event) {
			alert(event.data);
		}

                function send(){
                      var talk = $('talk');
                     var nike = $('nike');
                     webSocket.send('<strong style="color:red">'+nike.value+':</strong>'+talk.value);
                }
                
                function $(id){
                    return document.getElementById(id);
                }

	</script>    
                        

</body>
</html>