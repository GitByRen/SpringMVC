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
	/* 默认值: "application/x-www-form-urlencoded" */
	/* 
		ajax数据为url后缀的格式：
		1）提交方式使用POST时：
			contentType: "application/json; charset=utf-8",后台无法获取值
			contentType:"application/x-www-form-urlencoded",后台可以获取值
		2）当提交方式使用get时：
			两者都可以获取值
		
		ajax数据为json格式：
		1）提交方式使用POST时：
			contentType: "application/json; charset=utf-8",后台无法获取值
			contentType:"application/x-www-form-urlencoded",后台可以获取值。且中文不乱码
		2）当提交方式使用get时：
			两者都可以获取值，但是中文乱码
	*/
	
	$(function() {
		$("#send").click(function() {
			postJson();
			return false;
		});
	});

	function postJson() {
		var datas = {
			"id" : $('#ids').val(),
			"departmentName" : $('#departmentName').val()
		};
		
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : "${pageContext.request.contextPath}/testJson2/ajax",
			dataType : 'json',
			// contentType : 'application/json;charset=utf-8' 需要使用 JSON.stringify(datas) 
			// 如果没有指定contentType则可以data:datas，后台不需要@RequestBody
			data : JSON.stringify(datas),
			success : function(data) {
				console.log(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("出现异常，异常信息：" + textStatus, "error");
			}
		});
	};
	
	function getJson() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : "${pageContext.request.contextPath}/testJson2/ajaxGet",
			dataType : 'text',
			data : {
				id : $('#ids').val(),
				departmentName : $('#departmentName').val()
			},
			success : function(data) {
				console.log(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("出现异常，异常信息：" + textStatus, "error");
			}
		});
	}
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