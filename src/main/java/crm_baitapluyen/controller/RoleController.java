package crm_baitapluyen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.RoleServices;
import entity.Roles;

@WebServlet(name = "roleController", urlPatterns = {"/role", "/role-add", "/role-update", "/role-delete"})
public class RoleController extends HttpServlet {
	
	RoleServices roleService = new RoleServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		if ("/role".equals(path)) {
	        List<Roles> listRoles = roleService.getAllRole();
	        req.setAttribute("listRoles", listRoles);
	        req.getRequestDispatcher("role-table.jsp").forward(req, resp);
	    }

	    if ("/role-add".equals(path)) {
	    	req.setAttribute("action", "add");
	    	req.setAttribute("pageTitle", "Thêm mới quyền");
	        req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	    }
	    
	    if ("/role-update".equals(path)) {
	    	String roleId = req.getParameter("id");
	    	Roles role = roleService.getRoleById(Integer.parseInt(roleId));
	    	req.setAttribute("role", role);
	    	req.setAttribute("action", "update");
	    	req.setAttribute("pageTitle", "Sửa thông tin quyền");
	    	req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	    }
	    
	    if ("/role-delete".equals(path)) {
	    	String roleId = req.getParameter("id");
	    	roleService.deleteRole(Integer.parseInt(roleId));
	    	resp.sendRedirect(req.getContextPath() + "/role");
	    }
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		String roleName = req.getParameter("name");
		String desc = req.getParameter("desc");
		
		if ("/role-add".equals(path)) {
			
			if (roleName != null) {
				boolean isSuccess = roleService.insertRole(roleName, desc);
			}
		}
		
		if ("/role-update".equals(path)) {
			String id = req.getParameter("id");
			int roleId = Integer.parseInt(id);
			if (id != null) {
				boolean isSuccess = roleService.updateRole(roleId, roleName, desc);
			}
		}
		
		resp.sendRedirect(req.getContextPath() + "/role");
	}
	
}
