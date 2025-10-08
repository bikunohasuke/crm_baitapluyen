package crm_baitapluyen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.LoginServices;
import entity.Users;

@WebServlet(name = "loginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

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
	
	private LoginServices loginServices = new LoginServices();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");
		
		List<Users> listUsers = loginServices.login(email, password, remember);
		
		for (Cookie cookie : loginServices.listCookies) {
			resp.addCookie(cookie);
		}
		
		resp.sendRedirect(req.getContextPath());
	}
	
}
