<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="easyui.jsp"></jsp:include>
<!-- <script type="text/javascript">
	function fun(temp) {
		//alert($);
		//alert("cbdjcd");
		var a = temp.getElementsByTagName("input")[0].value;
		alert(a);//document.getElementById("divid").getElementsByTagName("input")[0].value;
		var b = temp.getElementsByTagName("input")[1].value;
		alert(b);//document.getElementById("divid").getElementsByTagName("input")[0].value;
		//alert(temp.getElementsByTagName("input")[1].value);//document.getElementById("divid").getElementsByTagName("input")[0].value;
		//alert(temp.children("input"));
	}
</script> -->
<script type="text/javascript">
	function f() {
		alert("xcsdcsd");
		alert(document.getElementById("id").value);
	}
	function check(temp) {
		//var re = /^\d+(?=\.{0,1}\d+$|$)/;
		var re2 = /^[0-9]+(.[0-9]{2})?$/;
		alert(temp.value);
		if (temp.value != "") {
			if (!re2.test(temp.value)) {
				alert("请输入正确的数字");
				temp.value = "";
				temp.focus();
			}else{
				alert("输入正确");
			}
		}else{
			alert("hehe");
		}
	}
</script>
<style type="text/css">
body {
	font-size: 34px;
}
</style>
</head>
<body>



	<input type="text" name="name" onmouseout="check(this)">

	<%-- <input type="button" name="jump" value= "跳转" onclick="location.href='test2.jsp?id=<%=1%>'"/>
 --%>
	<!-- <input id="id" onmouseout="f()" value="cnsdjcd"> -->
	<%-- <%
		int i = 1;
		int j = 2;
	%>
	<div style="width: 300px; height: 200px;" onclick="fun(this);">
		xnsdcnsdcn <input type="hidden" value="<%=i%>"> <input
			type="hidden" value="<%=j%>">
	</div> --%>
	<!-- <img src="booksimg/1.jpg" title="点击查看详情"   />title="cjscbjs" alt="点击查看详情"  -->





	<!-- <div id="dlg" class="easyui-dialog"
		style="width: 570px; height: 270px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>您的评价：</td>
					<td width="80%">
					<textarea id="UAppraise" rows="4" cols="40" name="UAppraise"></textarea>
					<input type="text" id="UAppraise"
						name="UAppraise" class="easyui-validatebox" required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:saveappraise()" class="easyui-linkbutton"
			>保存</a> <a href="javascript:closesaveappraise()"
			class="easyui-linkbutton" >关闭</a>
	</div> -->


	<!-- <h2 class="h">xnnxscx</h2>
	<h5 class="h">99999999999</h5> -->











</body>
</html>