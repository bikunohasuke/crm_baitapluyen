package crm_baitapluyen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.MySQLConfig;
import crm_baitapluyen.services.LoginServices;
import entity.Users;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	
	private LoginServices loginServices = new LoginServices();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cookie[] listCookies = req.getCookies();
		String email = "";
		String password = "";
		
		for (Cookie cookie : listCookies) {
			// getName(): Trả ra tên cookie
			String name = cookie.getName();
			// getValue(): Trả ra giá trị lưu trữ trong cookie
			String value = cookie.getValue();
			
			if (name.equals("email")) {
				email = value;
			}
			
			if (name.equals("password")) {
				password = value;
			}
		}
		
		req.setAttribute("email", email);
		req.setAttribute("password", password);
		
		System.out.println("Email: " + email);
		System.out.println("Password: " + password);
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
		List<Users> listUsers = loginServices.login(email, password);
		
		if (!listUsers.isEmpty()) {
			// Tạo cookie
			// Tạo cookie có tên là email và giá trị lưu trữ là email người dùng nhập
			if (remember != null) {
				Cookie cEmail = new Cookie("email", email);
				cEmail.setMaxAge(1 * 60); // 1 * 60 * 60 * 1000
				
				Cookie cPassword = new Cookie("password", password);
				cPassword.setMaxAge(1 * 60);

				// Bắt client tạo ra cookie
				resp.addCookie(cEmail);
				resp.addCookie(cPassword);
			}
			
		}
		
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}
	
}
