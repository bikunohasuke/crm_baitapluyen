package crm_baitapluyen.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "logoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cookie[] cookies = req.getCookies();
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id") || cookie.getName().equals("role") ||
					cookie.getName().equals("password") || cookie.getName().equals("password")) {

					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
		
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
