<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="../scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#send").click(function() {
			login();
			return false;
		});
	});

	function login() {
		var datas = '{"id":"' + $('#ids').val() + '","departmentName":"'
				+ $('#departmentName').val() + '"}';
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : "${pageContext.request.contextPath}/testJson2/ajax",
			dataType : 'json',
			data : datas,
			success : function(data) {
				console.log(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("出现异常，异常信息：" + textStatus, "error");
			}
		});
	};
</script>
<body>

	id:
	<input type="text" name="ids" id="ids"/>
	
	<br />
	<br /> 
	
	departmentName:
	<input type="text" name="departmentNames" id="departmentName"/>
	
	<br />
	<br />
	
	<a id="send" href="">发送</a>

</body>
</html>