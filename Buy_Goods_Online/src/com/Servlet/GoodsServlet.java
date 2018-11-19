package com.Servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import com.Dao.GoodsDao;
import com.Util.DbUtil;
import com.Util.ResponseUtil;
import com.model.Appraise;
import com.model.Goods;
import com.model.Order;
import com.model.OrderGood;
import com.model.User;

@WebServlet(name = "GoodsServlet", urlPatterns = "/goods")
public class GoodsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	GoodsDao goodsdao = new GoodsDao();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("GetGoods".equals(action)) {//买家获得当前所有商品
			GetGoods(request, response);
		} else if ("GetAppraise".equals(action)) {//买家获得指定商品的左右用户评价
			GetAppraise(request, response);
		} else if ("ImBuy".equals(action)) {//买家立即购买此商品
			ImBuy(request, response);
		} else if ("AddToShopCart".equals(action)) {//买家将此商品加入购物车
			AddToShopCart(request, response);
		} else if ("GetShopCart".equals(action)) {//买家获得自己的购物车信息
			GetShopCart(request, response);
		} else if ("DeleteGoods".equals(action)) {//买家删除自己指定的加入购物车的商品
			DeleteGoods(request, response);
		} else if ("GetMyShop".equals(action)) {//买家获得自己所有的已买商品
			GetMyShop(request, response);
		} else if ("AddAppraise".equals(action)) {//买家对自己制定已买商品进行评价
			AddAppraise(request, response);
		} else if ("DeleteOrder".equals(action)) {//买家删除自己指定的已买商品
			DeleteOrder(request, response);
		} else if ("SellerGetGoods".equals(action)) {//卖家获得自己卖的所有商品
			SellerGetGoods(request, response);
		} else if ("sellermodifygoods".equals(action)) {//卖家修改自己卖的商品的信息
			sellermodifygoods(request, response);
		} else if ("SellerDeleteGoods".equals(action)) {//卖家删除自己的指定商品
			SellerDeleteGoods(request, response);
		} else if ("selleraddgoods".equals(action)) {//卖家添加指定商品出售
			selleraddgoods(request, response);
		}
	}

	@SuppressWarnings({ "unchecked" })
	/*功能：卖家向GOODS表中添加一件商品出售，添加完后，更新卖家的session商品表，已在前端实时更新*/
	private void selleraddgoods(HttpServletRequest request,
			HttpServletResponse response) {
		String BName = request.getParameter("BName");
		double BPrice = Double.parseDouble(request.getParameter("BPrice"));
		int BStock = Integer.parseInt(request.getParameter("BStock"));
		String BDescripe = request.getParameter("BDescripe");
		int BClassify = Integer.parseInt(request.getParameter("BClassify"));

		HttpSession session = request.getSession();
		User currentuesr = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int BID = 1;
		int savenum = 0;
		try {
			// 获取最大的bid，在此基础上加一
			BID = goodsdao.sellergetbid(request, con) + 1;
			// 存入到数据库
			Goods goods = new Goods(BID, BName, BPrice, BStock, BDescripe,
					BClassify, currentuesr.getUID());
			savenum = goodsdao.selleraddgoods(request, con, goods);
			// 如果加入数据库成功，将此商品加入到goods显示session里面
			if (savenum > 0) {
				ArrayList<Goods> sellergoodslist = new ArrayList<Goods>();
				sellergoodslist = (ArrayList<Goods>) session
						.getAttribute("sellergoodslist");
				sellergoodslist.add(goods);
			}
			response.sendRedirect("SellerFirstPage.jsp");// 添加完商品信息重定向到商品的界面
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void SellerDeleteGoods(HttpServletRequest request,
			HttpServletResponse response) {
		int goodsid = Integer.parseInt(request.getParameter("goodsid"));
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int deletenum = 0;
		String s = "";
		try {
			
			goodsdao.sellerdeleteshopcart(request, con, goodsid);// 删除所有人购物车中的此商品
			goodsdao.sellerdeletemyshop(request, con, goodsid);// 删除所有人已买物品中的此商品
			deletenum = goodsdao.sellerdeletegoods(request, con, goodsid);// 删除商品表里面的此商品
			if (deletenum > 0) {
				s = "删除商品成功";
				HttpSession session = request.getSession();
				ArrayList<Goods> sellergoodslist = new ArrayList<Goods>();
				sellergoodslist = (ArrayList<Goods>) session
						.getAttribute("sellergoodslist");
				for (int i = 0; i < sellergoodslist.size(); i++) {
					if (sellergoodslist.get(i).getBID() == goodsid) {
						sellergoodslist.remove(i);
						i--;
					}
				}
			} else {
				s = "删除商品失败";
			}
			ResponseUtil.write(response, s);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sellermodifygoods(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取前端信息
		int GOodsId = Integer.parseInt(request.getParameter("GoodsId"));
		String GoodsName = request.getParameter("GoodsName");
		double GoodsPrice = Double.parseDouble(request
				.getParameter("GoodsPrice"));
		int GooodsStock = Integer.parseInt(request.getParameter("GoodsStock"));
		String GoodsDescripe = request.getParameter("GoodsDescripe");
		int GoodsClassify = Integer.parseInt(request
				.getParameter("GoodsClassify"));
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		Goods goods = new Goods(GOodsId, GoodsName, GoodsPrice, GooodsStock,
				GoodsDescripe, GoodsClassify, currentuser.getUID());
		int modify = 0;
		try {
			modify = goodsdao.sellermodifygoods(request, con, goods);
			if (modify > 0) {
				ArrayList<Goods> sellergoodslist = new ArrayList<Goods>();
				sellergoodslist = goodsdao.sellergetgoods(request, con,
						currentuser);
				session.setAttribute("sellergoodslist", sellergoodslist);
			}
			response.sendRedirect("SellerFirstPage.jsp");// 修改完商品信息重定向到商品的界面
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void SellerGetGoods(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		ArrayList<Goods> sellergoodslist = new ArrayList<Goods>();
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		try {
			sellergoodslist = goodsdao
					.sellergetgoods(request, con, currentuser);
			session.setAttribute("sellergoodslist", sellergoodslist);
			ResponseUtil.write(response, result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void GetGoods(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		ArrayList<Goods> clotheslist = new ArrayList<Goods>();
		ArrayList<Goods> bookslist = new ArrayList<Goods>();
		ArrayList<Goods> foodslist = new ArrayList<Goods>();
		ArrayList<Goods> carslist = new ArrayList<Goods>();
		ArrayList<Goods> machineslist = new ArrayList<Goods>();
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		result.put("success", "success");
		// 查询五种商品，分放在五个list中
		try {
			bookslist = goodsdao.getgoods(request, con, 1);
			clotheslist = goodsdao.getgoods(request, con, 2);
			foodslist = goodsdao.getgoods(request, con, 3);
			carslist = goodsdao.getgoods(request, con, 4);
			machineslist = goodsdao.getgoods(request, con, 5);
			HttpSession session = request.getSession();
			session.setAttribute("bookslist", bookslist);
			session.setAttribute("clotheslist", clotheslist);
			session.setAttribute("foodslist", foodslist);
			session.setAttribute("carslist", carslist);
			session.setAttribute("machineslist", machineslist);
			ResponseUtil.write(response, result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
	}

	private void DeleteOrder(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int orderid = Integer.parseInt(request.getParameter("orderid"));
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int result = 0;
		String s = "";
		try {
			result = goodsdao.deleteorder(request, con, orderid);
			if (result == 1) {
				@SuppressWarnings("unchecked")
				ArrayList<OrderGood> deleteorderlist = (ArrayList<OrderGood>) session
						.getAttribute("myshoplist");
				for (int i = 0; i < deleteorderlist.size(); i++) {
					if (deleteorderlist.get(i).getOId() == orderid) {
						deleteorderlist.remove(i);
						i--;
					}
				}
				session.setAttribute("myshoplist", deleteorderlist);
				s = "删除订单成功";
			} else {
				s = "删除订单失败";
			}
			ResponseUtil.write(response, s);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}

	}

	private void AddAppraise(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		// 获取前端的oid和用户的评价
		int OId = Integer.parseInt(request.getParameter("OId"));
		String app = request.getParameter("app");
		String app2 = null;
		// 解决url带中文的问题，前端加码，后台再解析
		try {
			app2 = new String(app.getBytes("iso8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int savenum = 0;
		try {
			JSONObject result = new JSONObject();
			// 将评价插入数据库
			savenum = goodsdao.addappraise(request, con, OId, app2);
			if (savenum == 1) {
				result.put("success", true);
				// 更新我的购物里面的评价，使评价后能立即看到自己的评价
				@SuppressWarnings("unchecked")
				ArrayList<OrderGood> appraisemyshoplist = (ArrayList<OrderGood>) session
						.getAttribute("myshoplist");
				for (int i = 0; i < appraisemyshoplist.size(); i++) {
					if (appraisemyshoplist.get(i).getOId() == OId) {
						appraisemyshoplist.get(i).setOAppraise(app2);
					}
				}
				session.setAttribute("myshoplist", appraisemyshoplist);
			} else {
				result.put("errorMsg", "评价失败");
			}
			ResponseUtil.write(response, result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
	}

	private void GetMyShop(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		ArrayList<OrderGood> myshop = new ArrayList<OrderGood>();
		try {
			// 遍历order表，找到所有的额我的购物
			myshop = goodsdao.getmyshop(request, con, currentuser);
			session.setAttribute("myshoplist", myshop);
			ResponseUtil.write(response, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}

	}

	private void DeleteGoods(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int GoodsId = Integer.parseInt(request.getParameter("goodsid"));
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int result = 0;
		String s = "";
		try {
			result = goodsdao.deletegoods(request, con, currentuser, GoodsId);
			ArrayList<Goods> shopcartlist = (ArrayList<Goods>) session
					.getAttribute("shopcartlist");
			for (int i = 0; i < shopcartlist.size(); i++) {
				if (shopcartlist.get(i).getBID() == GoodsId) {
					shopcartlist.remove(i);
					result = 1;
					break;
				}
			}
			if (result == 1) {
				s = "成功删除商品";
			} else {
				s = "删除商品失败";
			}
			session.setAttribute("shopcartlist", shopcartlist);
			ResponseUtil.write(response, s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void GetShopCart(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		ArrayList<Goods> shopcartlist = new ArrayList<Goods>();
		try {
			shopcartlist = goodsdao.getshopcartlist(request, con, currentuser);
			session.setAttribute("shopcartlist", shopcartlist);
			ResponseUtil.write(response, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
	}

	private void AddToShopCart(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int GoodsId = Integer.parseInt(request.getParameter("GoodsId"));
		int result = -1;
		int result2 = 0;
		int sid = 1;
		String s = "";
		try {
			sid = goodsdao.getsid(request, con) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result2 = goodsdao
					.queryshopcart(request, con, currentuser, GoodsId);
			if (result2 == 1) {
				result = goodsdao.addtoshopcart(request, con, currentuser,
						GoodsId, sid);
				if (result == 1) {
					s = "加入购物车成功";
					ArrayList<Goods> shopcartlist = new ArrayList<Goods>();
					shopcartlist = goodsdao.getshopcartlist(request, con,
							currentuser);
					session.setAttribute("shopcartlist", shopcartlist);
				} else {
					s = "加入购物车失败";
				}
			} else {
				s = "此商品已在购物车，请勿重复添加";
			}
			ResponseUtil.write(response, s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void ImBuy(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int GoodsId = Integer.parseInt(request.getParameter("GoodsId"));
		int GoodsType = Integer.parseInt(request.getParameter("GoodsType"));
		int GoodsNumber = Integer.parseInt(request.getParameter("GoodsNumber"));
		double GoodsPrice = Double.parseDouble(request
				.getParameter("GoodsPrice"));
		double sum = GoodsNumber * GoodsPrice;
		int oid = 1;
		try {
			// 获取当前最大的订单号，在此基础上加一
			oid = goodsdao.getOId(request, con) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		// 生成订单
		Order order = new Order(oid, GoodsId, currentuser.getUID(),
				GoodsNumber, null, null, GoodsType);
		int result = 0;
		String s = "";
		try {
			// 检查此用户是否余额充足
			double balance = goodsdao.getbalance(request, con, currentuser);
			if (balance > sum) {
				// 余额充足将此订单插入数据库，购买成功
				result = goodsdao.addorder(request, con, order);
				// 购买完成后，扣除用户的余额
				goodsdao.minbalance(request, con, currentuser, sum);
				//购买完成后，增加买家用户的余额
				goodsdao.addbalance(request,con,order,sum);
				// 购买完成后，扣除此商品的库存
				goodsdao.minstock(request, con, order);
				// 购买完成后，更新bookslist，其实主要在于更新库存
				ArrayList<Goods> bookslist = new ArrayList<Goods>();
				bookslist = (ArrayList<Goods>) session
						.getAttribute("bookslist");
				for (int i = 0; i < bookslist.size(); i++) {
					if (bookslist.get(i).getBID() == order.getBId()) {
						bookslist.get(i).setBStock(
								bookslist.get(i).getBStock()
										- order.getONumber());
					}
				}
				// 购买完成后，更新用户session，扣除用户余额
				currentuser.setUBalance(currentuser.getUBalance()
						- order.getONumber() * GoodsPrice);
				session.setAttribute("currentuser", currentuser);
				// 更新我的购物中的session，使购买后立即能在我的淘宝中看到
				ArrayList<OrderGood> myshop = new ArrayList<OrderGood>();
				myshop = goodsdao.getmyshop(request, con, currentuser);
				session.setAttribute("myshoplist", myshop);
			}
			if (result == 1) {
				s = "购买成功";
			} else {
				s = "购买失败，请检查是否余额不足";
			}
			ResponseUtil.write(response, s);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void GetAppraise(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取前端传过来的商品的信息
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		ArrayList<Appraise> appraiselist = new ArrayList<Appraise>();
		int CBlassfify = Integer.parseInt(request.getParameter("classify"));
		int BID = Integer.parseInt(request.getParameter("bid"));
		try {
			appraiselist = goodsdao.getappraise(request, con, BID);
			HttpSession session = request.getSession();
			session.setAttribute("userappraise", appraiselist);
			session.setAttribute("AppraiseBClassify", CBlassfify);
			session.setAttribute("AppraiseBId", BID);
			response.sendRedirect("SecondPage.jsp");// 查询完用户的评价，进入商品详细界面
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
	}
}
