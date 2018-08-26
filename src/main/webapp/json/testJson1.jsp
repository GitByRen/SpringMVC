<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		testGetOrPost();
	});

	function testLoad() {
		$("a").click(function() {
			var url = this.href;
			var args = {
				"time" : new Date()
			};
			// 任何一个html节点都可以使用load方法加载ajax,结果将直接插入到html节点中
			$("#content").load(url, args);
			return false;
		});
	}

	// 解析xml
	function testGetOrPost() {
		$("a").click(function() {
			var url = this.href;
			var args = {
				"time" : new Date()
			};
			// $.post也一样.   data为响应结果
			$.get(url, args, function(data) {
				// 返回xml时
				//var name = $(data).find("name").text();
				//var email = $(data).find("email").text();
				//var website = $(data).find("website").text();
				
				// 返回json时
				var name = data.tst.name;
				alert(name);
			}, "JSON"); // 将结果转为 json 格式
			return false;
		});
	}

	// 将结果转换为json对象
	function testGetJson() {
		$("a").click(function() {
			var url = this.href;
			var args = {
				"time" : new Date()
			};
			$.getJSON(url, args, function(data) {
				var name = data.tst.name;
				var age = data.tst.age;
				alert(name + age);
			});
			return false;
		});
	}
</script>
<body>

	<a href="index2.jsp">HelloAjax</a>
	<div id="content"></div>

</body>
</html>