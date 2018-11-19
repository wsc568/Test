package com.Servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CreateCode")
public class CreateCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//禁止浏览器缓存随机图片
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		
		//通知客户机以图片方式打开发送过去的数据
		response.setHeader("Content-Type", "image/jpeg");
		//在内存中创建一幅图片
		BufferedImage image = new BufferedImage(60, 25, BufferedImage.TYPE_INT_BGR);
		//向图片上写数据
		Graphics graphics = image.getGraphics();
		//设置图片背景色
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 60, 25);
		//设置写入数据的颜色和字体
		graphics.setColor(Color.RED);
		graphics.setFont(new Font(null, Font.BOLD, 20));
		//向图片上写数据
		String num = makeNum();
		//这句话就是把随机生成的数值，保存到session中
		request.getSession().setAttribute("CHECKCODE", num);
		graphics.drawString(num, 0, 20);
		//把写好数据的图片输出给浏览器
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		
	}
	//该函数是随机生成四位数
	public String makeNum() {
		Random random = new Random();
		String	num= random.nextInt(9999)+"";
		StringBuffer sBuffer = new StringBuffer();
		for(int i=0;i<4-num.length();i++){
			sBuffer.append("0");
		}
		num = sBuffer.toString()+num;
		return num;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
