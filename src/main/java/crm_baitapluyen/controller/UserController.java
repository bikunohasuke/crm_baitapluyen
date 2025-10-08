package crm_baitapluyen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.JobServices;
import crm_baitapluyen.services.RoleServices;
import crm_baitapluyen.services.TaskServices;
import crm_baitapluyen.services.UserServices;
import entity.Roles;
import entity.Tasks;
import entity.Users;

@WebServlet(name = "userController", urlPatterns = {"/user", "/user-add", "/user-update", "/user-delete", "/user-details"})
public class UserController extends HttpServlet {
	
	private UserServices userServices = new UserServices();
	private RoleServices roleServices = new RoleServices();
	private TaskServices taskServices = new TaskServices();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		List<Users> listUsers = userServices.getAllUser();
		List<Roles> listRoles = roleServices.getAllRole();
		
		if ("/user".equals(path)) {
			req.setAttribute("listUsers", listUsers);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		}
		
		if ("/user-add".equals(path)) {
			req.setAttribute("action", "add");
			req.setAttribute("pageTitle", "Thêm mới người dùng");
			req.setAttribute("listRoles", listRoles);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		}
		
		if ("/user-update".equals(path)) {
			String userId = req.getParameter("id");
			Users user = userServices.getUserById(Integer.parseInt(userId));
			req.setAttribute("user", user);
			req.setAttribute("listRoles", listRoles);
			req.setAttribute("action", "update");
			req.setAttribute("pageTitle", "Sửa thông tin người dùng");
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		}
		
		if ("/user-delete".equals(path)) {
			String idUser = req.getParameter("id");
			userServices.deleteUser(Integer.parseInt(idUser));
			resp.sendRedirect(req.getContextPath() + "/user");
		}
		
		if ("/user-details".equals(path)) {
			int userId = Integer.parseInt(req.getParameter("id"));
			System.out.println("UserId: " + userId);
			
			List<Tasks> listTasks = taskServices.getTasksByUser(userId);
			List<Tasks> listTaskNotStart = taskServices.getTasksByUserAndStatus(userId, 1);
			List<Tasks> listTaskInProgress = taskServices.getTasksByUserAndStatus(userId, 2);
			List<Tasks> listTaskDone = taskServices.getTasksByUserAndStatus(userId, 3);
			
			int notStartPercent = 0, inProgressPercent = 0, donePercent = 0;
			if (listTasks.size() > 0) {
				notStartPercent = (listTaskNotStart.size() * 100) / listTasks.size();
				inProgressPercent = (listTaskInProgress.size() * 100) / listTasks.size();
				donePercent = (listTaskDone.size() * 100) / listTasks.size();
			}
			
			req.setAttribute("listTasks", listTasks);
			req.setAttribute("listTaskNotStart", listTaskNotStart);
			req.setAttribute("listTaskInProgress", listTaskInProgress);
			req.setAttribute("listTaskDone", listTaskDone);
			req.setAttribute("notStartPercent", notStartPercent);
			req.setAttribute("inProgressPercent", inProgressPercent);
			req.setAttribute("donePercent", donePercent);
			
			req.getRequestDispatcher("user-details.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		String fullName = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String avatar = req.getParameter("avatar");
		String roleIdStr = req.getParameter("role");
		int roleId = Integer.parseInt(roleIdStr);
		
		if ("/user-add".equals(path)) {
			if (fullName != null && email != null && roleIdStr != null) {
				boolean isSuccess = userServices.insertUsers(fullName, email, password, avatar, roleId);
			}
			
			resp.sendRedirect(req.getContextPath() + "/user");
		}
		
		if ("/user-update".equals(path)) {
			String id = req.getParameter("id");
			int userId = Integer.parseInt(id);
			if (id != null) {
				boolean isSuccess = userServices.updateUser(userId, fullName, email, password, avatar, roleId);
			}
			
			resp.sendRedirect(req.getContextPath() + "/user");
		}
		
	}
	
	
}
