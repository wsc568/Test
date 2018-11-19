<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="easyui.jsp"></jsp:include>
<script type="text/javascript">
	window.onload = function() {
		alert("bj bdj");
	}
	function inputclick() {
		alert("cbssdcsdc");
	}
</script>
<style type="text/css">
.div {
	width: 100px;
	height: 20px;
}
</style>
</head>
<body>
	<%
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
	%>
	<div>
		<div>
			<div style="width: 1200px">
				<div style="float: left">xscscsdcsdsdcds</div>
				<div style="float: left">xscscsdcsdsdcds</div>
			</div>
		</div>
		<div>uuuuuuuuuuuuuuuu</div>
	</div>
</body>
</body>
</html>