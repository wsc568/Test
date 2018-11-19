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
		if ("GetGoods".equals(action)) {//��һ�õ�ǰ������Ʒ
			GetGoods(request, response);
		} else if ("GetAppraise".equals(action)) {//��һ��ָ����Ʒ�������û�����
			GetAppraise(request, response);
		} else if ("ImBuy".equals(action)) {//��������������Ʒ
			ImBuy(request, response);
		} else if ("AddToShopCart".equals(action)) {//��ҽ�����Ʒ���빺�ﳵ
			AddToShopCart(request, response);
		} else if ("GetShopCart".equals(action)) {//��һ���Լ��Ĺ��ﳵ��Ϣ
			GetShopCart(request, response);
		} else if ("DeleteGoods".equals(action)) {//���ɾ���Լ�ָ���ļ��빺�ﳵ����Ʒ
			DeleteGoods(request, response);
		} else if ("GetMyShop".equals(action)) {//��һ���Լ����е�������Ʒ
			GetMyShop(request, response);
		} else if ("AddAppraise".equals(action)) {//��Ҷ��Լ��ƶ�������Ʒ��������
			AddAppraise(request, response);
		} else if ("DeleteOrder".equals(action)) {//���ɾ���Լ�ָ����������Ʒ
			DeleteOrder(request, response);
		} else if ("SellerGetGoods".equals(action)) {//���һ���Լ�����������Ʒ
			SellerGetGoods(request, response);
		} else if ("sellermodifygoods".equals(action)) {//�����޸��Լ�������Ʒ����Ϣ
			sellermodifygoods(request, response);
		} else if ("SellerDeleteGoods".equals(action)) {//����ɾ���Լ���ָ����Ʒ
			SellerDeleteGoods(request, response);
		} else if ("selleraddgoods".equals(action)) {//�������ָ����Ʒ����
			selleraddgoods(request, response);
		}
	}

	@SuppressWarnings({ "unchecked" })
	/*���ܣ�������GOODS�������һ����Ʒ���ۣ������󣬸������ҵ�session��Ʒ������ǰ��ʵʱ����*/
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
			// ��ȡ����bid���ڴ˻����ϼ�һ
			BID = goodsdao.sellergetbid(request, con) + 1;
			// ���뵽���ݿ�
			Goods goods = new Goods(BID, BName, BPrice, BStock, BDescripe,
					BClassify, currentuesr.getUID());
			savenum = goodsdao.selleraddgoods(request, con, goods);
			// ����������ݿ�ɹ���������Ʒ���뵽goods��ʾsession����
			if (savenum > 0) {
				ArrayList<Goods> sellergoodslist = new ArrayList<Goods>();
				sellergoodslist = (ArrayList<Goods>) session
						.getAttribute("sellergoodslist");
				sellergoodslist.add(goods);
			}
			response.sendRedirect("SellerFirstPage.jsp");// �������Ʒ��Ϣ�ض�����Ʒ�Ľ���
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
			
			goodsdao.sellerdeleteshopcart(request, con, goodsid);// ɾ�������˹��ﳵ�еĴ���Ʒ
			goodsdao.sellerdeletemyshop(request, con, goodsid);// ɾ��������������Ʒ�еĴ���Ʒ
			deletenum = goodsdao.sellerdeletegoods(request, con, goodsid);// ɾ����Ʒ������Ĵ���Ʒ
			if (deletenum > 0) {
				s = "ɾ����Ʒ�ɹ�";
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
				s = "ɾ����Ʒʧ��";
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
		// ��ȡǰ����Ϣ
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
			response.sendRedirect("SellerFirstPage.jsp");// �޸�����Ʒ��Ϣ�ض�����Ʒ�Ľ���
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
		// ��ѯ������Ʒ���ַ������list��
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
				s = "ɾ�������ɹ�";
			} else {
				s = "ɾ������ʧ��";
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
		// ��ȡǰ�˵�oid���û�������
		int OId = Integer.parseInt(request.getParameter("OId"));
		String app = request.getParameter("app");
		String app2 = null;
		// ���url�����ĵ����⣬ǰ�˼��룬��̨�ٽ���
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
			// �����۲������ݿ�
			savenum = goodsdao.addappraise(request, con, OId, app2);
			if (savenum == 1) {
				result.put("success", true);
				// �����ҵĹ�����������ۣ�ʹ���ۺ������������Լ�������
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
				result.put("errorMsg", "����ʧ��");
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
			// ����order���ҵ����еĶ��ҵĹ���
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
				s = "�ɹ�ɾ����Ʒ";
			} else {
				s = "ɾ����Ʒʧ��";
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
					s = "���빺�ﳵ�ɹ�";
					ArrayList<Goods> shopcartlist = new ArrayList<Goods>();
					shopcartlist = goodsdao.getshopcartlist(request, con,
							currentuser);
					session.setAttribute("shopcartlist", shopcartlist);
				} else {
					s = "���빺�ﳵʧ��";
				}
			} else {
				s = "����Ʒ���ڹ��ﳵ�������ظ����";
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
			// ��ȡ��ǰ���Ķ����ţ��ڴ˻����ϼ�һ
			oid = goodsdao.getOId(request, con) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		// ���ɶ���
		Order order = new Order(oid, GoodsId, currentuser.getUID(),
				GoodsNumber, null, null, GoodsType);
		int result = 0;
		String s = "";
		try {
			// �����û��Ƿ�������
			double balance = goodsdao.getbalance(request, con, currentuser);
			if (balance > sum) {
				// �����㽫�˶����������ݿ⣬����ɹ�
				result = goodsdao.addorder(request, con, order);
				// ������ɺ󣬿۳��û������
				goodsdao.minbalance(request, con, currentuser, sum);
				//������ɺ���������û������
				goodsdao.addbalance(request,con,order,sum);
				// ������ɺ󣬿۳�����Ʒ�Ŀ��
				goodsdao.minstock(request, con, order);
				// ������ɺ󣬸���bookslist����ʵ��Ҫ���ڸ��¿��
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
				// ������ɺ󣬸����û�session���۳��û����
				currentuser.setUBalance(currentuser.getUBalance()
						- order.getONumber() * GoodsPrice);
				session.setAttribute("currentuser", currentuser);
				// �����ҵĹ����е�session��ʹ��������������ҵ��Ա��п���
				ArrayList<OrderGood> myshop = new ArrayList<OrderGood>();
				myshop = goodsdao.getmyshop(request, con, currentuser);
				session.setAttribute("myshoplist", myshop);
			}
			if (result == 1) {
				s = "����ɹ�";
			} else {
				s = "����ʧ�ܣ������Ƿ�����";
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
		// ��ȡǰ�˴���������Ʒ����Ϣ
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
			response.sendRedirect("SecondPage.jsp");// ��ѯ���û������ۣ�������Ʒ��ϸ����
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
	}
}
