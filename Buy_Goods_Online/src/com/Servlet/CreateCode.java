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
		//��ֹ������������ͼƬ
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		
		//֪ͨ�ͻ�����ͼƬ��ʽ�򿪷��͹�ȥ������
		response.setHeader("Content-Type", "image/jpeg");
		//���ڴ��д���һ��ͼƬ
		BufferedImage image = new BufferedImage(60, 25, BufferedImage.TYPE_INT_BGR);
		//��ͼƬ��д����
		Graphics graphics = image.getGraphics();
		//����ͼƬ����ɫ
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, 60, 25);
		//����д�����ݵ���ɫ������
		graphics.setColor(Color.RED);
		graphics.setFont(new Font(null, Font.BOLD, 20));
		//��ͼƬ��д����
		String num = makeNum();
		//��仰���ǰ�������ɵ���ֵ�����浽session��
		request.getSession().setAttribute("CHECKCODE", num);
		graphics.drawString(num, 0, 20);
		//��д�����ݵ�ͼƬ����������
		ImageIO.write(image, "jpg", response.getOutputStream());
		
		
	}
	//�ú��������������λ��
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
