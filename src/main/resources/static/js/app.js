/**
 * 
 */
$(function() {
	//alert("loaded");
	$("#forgetpwd").click(function() {
		/*$.post("http://localhost:8099/electron/forgetpwd", {
			emailId : $("#username").val(),
		}, function(data, status) {
			alert("Data: " + data + "\nStatus: " + status);
		});*/

		$.ajax({
			type : "POST",
			url : "http://localhost:8099/electron/forgetpwd",
			data : JSON.stringify({
				emailId : $("#username").val(),
			}),
			success : function(data, status) {
				//alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
				$("#forgetpwdresp").text(data.message);
				$("#forgetpwdresp").show();
			},
			error : function(data, status) {
				alert("Data: " + JSON.stringify(data) + "\nStatus: " + status);
			},
			dataType : "json",
			contentType : "application/json"
		});
	});
	
	$("#overridelogin").click(function() {
		window.location.href = "./clearprevioussession";
	});
	$("#gotologin").click(function() {
		window.location.href = "./clearcurrentsession";
	});
	
});

