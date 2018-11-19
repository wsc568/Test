<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Goods"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>卖家后台</title>
<jsp:include page="easyui.jsp"></jsp:include>
<script type="text/javascript">
	window.onload = function() {
		if (!sessionStorage.getItem("sellerfirstpageflag")) {
			sessionStorage.setItem("sellerfirstpageflag", 1);
			$.ajax({
				type : "post",
				async : false,
				url : "goods?action=SellerGetGoods",
				datatype : "json",
				success : function(data) {
					window.location.reload();
				},
			})
		}
	}
	function sellerdeletegoods(temp) {
		var Goodsid = temp.getElementsByTagName("input")[0].value;
		$.ajax({
			type : "post",
			async : false,
			url : "goods?action=SellerDeleteGoods&goodsid=" + Goodsid + "",
			datatype : "json",
			success : function(data) {
				alert(data);
				window.location.reload();
			},
		})
	}
	function check() {
		var re = /^\d+(?=\.{0,1}\d+$|$)/;
		var price = document.getElementById("BPrice").value;
		var stock = document.getElementById("BStock").value;
		if (!re.test(price)) {
			alert("请输入正确形式的商品价格");
			return false;
		}
		if (!re.test(stock)) {
			alert("请输入正确形式的商品库存");
			return false;
		}
		return true;
	}
	function modifypassword() {
		url = "login?action=modifypassword";
		$("#dlg").dialog("open").dialog("setTitle", "修改密码");
	}
	function modifyPassword() {
		$("#fm2").form("submit", {
			url : url,
			onSubmit : function() {
				var oldPassword = $("#oldPassword").val();
				var newPassword = $("#newPassword").val();
				var newPassword2 = $("#newPassword2").val();
				if (!$(this).form("validate")) {
					return false;
				}
				if (oldPassword != '${currentuser.UPassword}') {
					$.messager.alert('系统提示', '用户名密码输入错误！');
					return false;
				}
				if (newPassword != newPassword2) {
					$.messager.alert('系统提示', '确认密码输入错误！');
					return false;
				}
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.alert('系统提示', result.errorMsg);
					return;
				} else {
					$.messager.alert('系统提示', '密码修改成功，下一次登录生效！');
					closePasswordModifyDialog();
				}
			}
		});
	}
	function closePasswordModifyDialog() {
		$("#dlg").dialog("close");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}
</script>
<style type="text/css">
.p {
	font-size: 16px;
}
</style>
</head>
<body>
	<center>
		<div class="easyui-tabs" style="width: 80%; height: 600px">
			<!-- 这里是下面的显示div -->
			<div class="SellerFirstPagediv" title="全部商品">
				<!-- 全部商品div -->
				<form id="fm" method="post">
					<!-- 负责这个界面的内容 -->
					<%
						final int PAGESIZE = 6;//每页放8个商品;
						ArrayList<Goods> list = new ArrayList<Goods>();
						if (session.getAttribute("sellergoodslist") != null) {//如果有这个商品的话
							list = (ArrayList<Goods>) session
									.getAttribute("sellergoodslist");// 窄化转换，没办法去掉警告 
						}
					%>
					<%
						int PageCount = 0;
						int CurrPage = 1;//当前页，首先设置为1；
						if (list.size() > 0) {//如果商品的数量不为0

							int Size = list.size();
							PageCount = (Size % PAGESIZE == 0) ? (Size / PAGESIZE) : (Size
									/ PAGESIZE + 1);
							String tmp = request.getParameter("CurrPage");//获取带过来的页面数
							if (tmp == null) {
								tmp = "1";
							}
							CurrPage = Integer.parseInt(tmp);
							CurrPage = CurrPage == 0 ? 1 : CurrPage;
							CurrPage = CurrPage > PageCount ? PageCount : CurrPage;
							int count = (CurrPage - 1) * PAGESIZE;
							for (int i = count; i < count + PAGESIZE && i < Size; i++)//
							{
					%>
					<div style="float: left; width: 20%; margin-left: 1%">
						<!-- 显示商品的图片 -->
						<img src="booksimg/<%=list.get(i).getBID() + ".jpg"%>">
					</div>
					<div style="float: left; width: 10%; margin-left: 2%">
						<h2><%=list.get(i).getBName()%></h2>

						<h2>
							<font color="red">￥：<%=list.get(i).getBPrice()%></font>
						</h2>

						<h2>
							库存：<%=list.get(i).getBStock()%></h2>
						商品描述：<%=list.get(i).getBDescripe()%>
						<input type="button" value="修改商品信息"
							onclick="location.href='SellerModifyGoods.jsp?goosid=<%=list.get(i).getBID()%>'">
						<div onclick="sellerdeletegoods(this)">
							<input type="hidden" value="<%=list.get(i).getBID()%>"> <input
								type="button" value="下架此商品">

						</div>
						<!-- 显示商品的其他信息 -->
					</div>


					<%
						}

						}
					%>
					<div style="position: absolute; bottom: 50px; margin-left: 650px;">
						<div style="text-align: center">
							<a href="SellerFirstPage.jsp?CurrPage=1">首页</a> <a
								href="SellerFirstPage.jsp?CurrPage=<%=CurrPage - 1%>">上一页</a> <a
								href="SellerFirstPage.jsp?CurrPage=<%=CurrPage + 1%>">下一页</a> <a
								href="SellerFirstPage.jsp?CurrPage=<%=CurrPage%>">尾页</a> 第<%=CurrPage%>页/共<%=PageCount%>页
						</div>
					</div>
				</form>
			</div>

			<div title="添加商品" style="margin-top: 60px; margin-left: 120px;">
				<!-- 这里是添加商品div -->
				<form action="goods?action=selleraddgoods" method="post">
					<table>
						<tr>
							<td>商品名称</td>
							<td><input class="easyui-textbox" type="text" name="BName"
								data-options="required:true"></td>
						</tr>
						<tr>
							<td>商品价格</td>
							<td><input class="easyui-textbox" type="text" name="BPrice"
								data-options="required:true" id="BPrice"></td>
						</tr>
						<tr>
							<td>库存数量</td>
							<td><input class="easyui-textbox" type="text" name="BStock"
								data-options="required:true" id="BStock"></td>
						</tr>
						<tr>
							<td>商品描述</td>
							<td><input type="text" data-options="multiline:true"
								class="easyui-textbox" name="BDescripe"></td>
						</tr>
						<tr>
							<td>商品分类</td>
							<td><select class="easyui-combobox" name="BClassify"><option
										value="1">书籍</option>
									<option value="2">服装</option>
									<option value="3">饮食</option>
									<option value="4">汽车</option>
									<option value="5">电器</option></select></td>
						</tr>
					</table>
					<input onclick="return check()" type="submit" value="添加">
				</form>
			</div>
			<div title="个人信息" style="margin-top: 60px; margin-left: 120px;">
				<div style="width: 50%;  border: 1px solid #000">
					<br> <br> <br> <br> <br> <br>
					<table>
						<tr>
							<td><p class="p">用 户 名:</td>
							<td><p class="p">${currentuser.UName}</td>
						</tr>
						<tr>
							<td><p class="p">账户余额：</td>
							<td><p class="p">${currentuser.UBalance}</td>
						</tr>
						<tr>
							<td><p class="p">电 话：</td>
							<td><p class="p">${currentuser.UTel}</td>
						</tr>
						<tr>
							<td><p class="p">身份证号：</td>
							<td><p class="p">${currentuser.UIdentify}</td>
						</tr>
						<tr>
							<td><p class="p">收货地址：</td>
							<td><p class="p">${currentuser.UAddress}</td>
						</tr>
						<tr>
							<td><input type="button" value="修改密码"
								onclick="modifypassword()"></td>
						</tr>
					</table>
					<br> <br>
				</div>
			</div>
		</div>

	</center>
	<!-- 商城首页 -->

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 180px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons" data-options="iconCls:'icon-modifyPassword'">
		<form id="fm2" method="post">
			<table cellspacing="4px;">
				<tr>
					<td>用户名：</td>
					<td><input type="hidden" name="userId" id="userId"
						value="${currentuser.UID }"><input type="text"
						name="userName" id="userName" readonly="readonly"
						value="${currentuser.UName}" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td>原密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="oldPassword" id="oldPassword" style="width: 200px;"
						required="true" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="newPassword" id="newPassword" style="width: 200px;"
						required="true" /></td>
				</tr>
				<tr>
					<td>确认新密码：</td>
					<td><input type="password" class="easyui-validatebox"
						name="newPassword2" id="newPassword2" style="width: 200px;"
						required="true" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:modifyPassword()" class="easyui-linkbutton">保存</a>
		<a href="javascript:closePasswordModifyDialog()"
			class="easyui-linkbutton">关闭</a>
	</div>
	
</body>
</html>