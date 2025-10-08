package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.UserServices;
import entity.Users;

@WebFilter(filterName = "authenFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
	
	private UserServices userServices = new UserServices();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (req.getRequestURI().endsWith("/login")) {
            chain.doFilter(request, response);
            return;
        }
		
		int userId = -1;
		int roleId = -1;
		
		Cookie[] listCookies = req.getCookies();
		
		if (listCookies != null) {
			for (Cookie cookie : listCookies) {
				String name = cookie.getName();
				if (name.equals("id")) {
					userId = Integer.parseInt(cookie.getValue());
				}
				if (name.equals("role")) {
					roleId = Integer.parseInt(cookie.getValue());
				}
			}
		}
		
		if (userId != -1 && roleId != -1) {
			Users currentUser = userServices.getUserById(userId);
			req.setAttribute("currentUser", currentUser);
			req.setAttribute("roleId", roleId);
			
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
		
	}
	
}
