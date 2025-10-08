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

@WebFilter(filterName = "authorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		
		if (uri.endsWith("/") || uri.endsWith("/login") 
				|| uri.endsWith("/logout") || uri.endsWith("/404")) {
            chain.doFilter(request, response);
            return;
        }
		
		Integer roleId = (Integer) req.getAttribute("roleId");
		
		if (roleId == 1) {
			chain.doFilter(request, response);
			return;
		} 
		
		if (roleId == 2) {
			if (uri.contains("/role") || uri.contains("/user-add") || 
				uri.contains("/user-update") || uri.contains("/user-delete") || uri.contains("/user-details")) {
				resp.sendRedirect(req.getContextPath() + "/404");
				return;
			}
			chain.doFilter(request, response);
			return;
		} 
		
		if (roleId >= 3) {
			if (!uri.startsWith(req.getContextPath() + "/profile")) {
				resp.sendRedirect(req.getContextPath() + "/404");
				return;
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
		
	}
}
