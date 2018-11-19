package com.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.naming.java.javaURLContextFactory;

import net.sf.json.JSONObject;
import com.Dao.LoginDao;
import com.Util.DbUtil;
import com.Util.ResponseUtil;
import com.model.User;
import com.sun.org.apache.bcel.internal.generic.NEW;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
// ע�ⷽʽ
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	//����û�ע���ʱ�����Ĭ��Ϊ10000��
	private static final double BALABCE = 10000.0;
	LoginDao logindao = new LoginDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		// �����ע�ᣬ����ע��ĺ���
		if ("register".equals(action)) {
			register(request, response);// ע�ắ����
		} else if ("login".equals(action)) {
			login(request, response);// ��¼����
		} else if ("modifypassword".equals(action)) {
			modifypassword(request, response);// �޸����뺯��
		}
	}

	private void modifypassword(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String newpassword = request.getParameter("newPassword2");
		System.out.println(newpassword);   
		HttpSession session = request.getSession();
		User currentuser = (User) session.getAttribute("currentuser");
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int savenum = 0;
		try {
			// �޸�����
			savenum = logindao.modifypassword(request, con, currentuser,
					newpassword);
			if (savenum > 0) {
				result.put("success", "success");
			} else {
				result.put("errorMsg", "�޸�����ʧ��");
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

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String UName = request.getParameter("UName");
		String UPassword = request.getParameter("UPassword");
		// ����û����������ǲ���Ϊ��
		request.setAttribute("UName", UName);
		request.setAttribute("UPassword", UPassword);
	//	String val = request.getParameter("iskeepinfo");
		HttpSession session = request.getSession();
		String checkcode = request.getParameter("checkcode");
		String checkcode2 = (String) session.getAttribute("CHECKCODE");
		

		if(checkcode.equals(checkcode2)){
			if (StringUtils.isEmpty(UName)) {
				request.setAttribute("error", "�û���Ϊ��");
				request.getRequestDispatcher("login.jsp")
						.forward(request, response);
				return;
			} else if (StringUtils.isEmpty(UPassword)) {
				request.setAttribute("error", "����ȷ��������");
				request.getRequestDispatcher("login.jsp")
						.forward(request, response);
				return;
			}
		}else{
			request.setAttribute("error", "��֤�����");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		}
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		User user = null;
		// �������ݿ����û����������ѯ���ݿ⣬���в鵽֤����¼�ɹ�
		try {
			//��¼����
			user = logindao.login(request, con, UName, UPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
		if (user == null) {
			request.setAttribute("error", "�û������������");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
			return;
		} else {// �鵽�д��û������������棬��Ϊ��ǰ�û�����һ��currentuser��ֵ��
			session.setAttribute("currentuser", user);// ���ﲻ����request.setAttribute,��Ϊ�������ڵ�����
			
			//����Cookie,��ʾ�û��ϴε�¼ʱ��
			Cookie []cookies = request.getCookies();
			boolean b=false;//����û��lastTime
			//���cookie�Ƿ�Ϊ��
			if(cookies != null){
				//�����Ϊ�����coookis���б���
				for(Cookie cookie : cookies){
					//ȡ��cookie����
					String name = cookie.getName();
					if("lastTime".equals(name)){
						session.setAttribute("cookie", cookie.getValue());
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd/HH:mm:ss");
						String nowTime=simpleDateFormat.format(new java.util.Date());
						cookie.setValue(nowTime);
						cookie.setMaxAge(3600*24*7);
						response.addCookie(cookie);
						b=true;
						break;
					}
					
				}
			}
			if(!b){
				session.setAttribute("cookie", "��ӭ��һ�ε�½");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd/HH:mm:ss");
				String nowTime=simpleDateFormat.format(new java.util.Date());
				Cookie cookie = new Cookie("lastTime", nowTime);
				cookie.setMaxAge(3600*24*7);
				response.addCookie(cookie);
			}
			
			
			if (user.getUClassify() == 1) {// ����û���¼����FirstPage���������
				response.sendRedirect("FirstPage.jsp");// ��¼�ɹ�������������
			} else if (user.getUClassify() == 2) {//�����û�����SellerFirstPage����������
				response.sendRedirect("SellerFirstPage.jsp");
			}
		}
	}

	private void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ��ȡǰ������
		String UName = request.getParameter("UName");
		String UPassword = request.getParameter("UPassword");
		int UClassify = Integer.parseInt(request.getParameter("UClassify"));
		long UTel = Long.parseLong(request.getParameter("UTel"));
		long UIdentify = Long.parseLong(request.getParameter("UIdentify"));
		String UAdress = request.getParameter("UAdress");
		double UBalabce = UClassify==1?BALABCE:0.0;//����û�Ĭ�����Ϊbalance������Ϊ0

		// ��ȡ����
		DbUtil dbutil = new DbUtil();
		Connection con = dbutil.getCon();
		int UID = 0;
		// ��ȡ���ݿ�������UIDD���ڴ˻����ϼ�1��ʵ����������Ч��
		try {
			UID = logindao.getmaxid(request, con) + 1;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// ��user��Ϣ��װ���࣬UID�����������Ĭ��Ϊ10000
		User user = new User(UID, UName, UPassword, UBalabce, UTel, UIdentify,
				UAdress, UClassify);
		// ע���û�
		int addresult = 0;
		try {
			//���û���Ϣ��ӽ����û���
			addresult = logindao.addUser(request, con, user);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbutil.closed(con);
		}
		// ע��ɹ����ض��򵽵�½����,ע��ʧ�ܺ��ض���ע�����
		if (addresult == 1) {
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else {
			request.getRequestDispatcher("register.jsp").forward(request,
					response);
		}
	}

}
