/*$(function(){
	$.ajax({
		type:"post",
		url:"/test1/sse",
		data:"json",
		success:function(data){
			$("#dataSpan").val(data);
		}
	});
});*/ //不用AJAX获取请求

//使用SSE技术让服务器端推送
$(function(){
	/*if(window.EventSource){
		var source = new EventSource("/test1/push/sse");
		source.onmessage = function(e) {
			$("#dataSpan").text(e.data);
		};
			$("#dataSpan").text(e.data);
	
		source.addEventListener('open',function(e){
			console.log("SEE方式推送打开了");
		},false);
		source.addEventListener('error',function(e){
			if(e.readyState == EventSource.CLOSED){
				console.log("SEE方式推送关闭");
			}else{
				console.log("SEE方式推送出错了，状态为"+e.readyState);
			}
		},false);
	}*/
	if(!!window.EventSource) {
	       var source = new EventSource('/test1/push/sse'); //为http://localhost:8080/testSpringMVC/push
	       s='';
	       source.addEventListener('message', function(e) {
	    	 /*  console.log(e.data);
	           s+=e.data+"<br/>"
	           $("#dataSpan").html(s);*/
	    	   console.log(eval(e.data)[0].id);

	       },false);

	       source.addEventListener('open', function(e) {
	            console.log("连接打开.");
	       }, false);

	       source.addEventListener('error', function(e) {
	            if (e.readyState == EventSource.CLOSED) {
	               console.log("连接关闭");
	            } else {
	                /*console.log(e.readyState);    */
	            }
	       }, false);
	    } else {
	            console.log("没有sse");
	    }
	
});