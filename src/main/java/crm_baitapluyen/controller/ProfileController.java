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
import crm_baitapluyen.services.StatusServices;
import crm_baitapluyen.services.TaskServices;
import crm_baitapluyen.services.UserServices;
import entity.Jobs;
import entity.Roles;
import entity.Status;
import entity.Tasks;
import entity.Users;

@WebServlet(name = "profileController", urlPatterns = {"/profile", "/profile-edit", "/profile-user"})
public class ProfileController extends HttpServlet {
	
	private UserServices userServices = new UserServices();
	private JobServices jobServices = new JobServices();
	private TaskServices taskServices = new TaskServices();
	private StatusServices statusServices = new StatusServices();
	private RoleServices roleServices = new RoleServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		Users currentUser = (Users) req.getAttribute("currentUser");

		int currentUserId = currentUser.getId();
		List<Tasks> listTasks = taskServices.getTasksByUser(currentUserId);
		req.setAttribute("listTasks", listTasks);
		
		int totalTask = listTasks.size();
		int taskNotStart = 0, taskInProgress = 0, taskDone = 0;
		
		for (Tasks task : listTasks) {
			if (task.getStatusId() == 1) {
			    taskNotStart++;
			} else if (task.getStatusId() == 2) {
			    taskInProgress++;
			} else if (task.getStatusId() == 3) {
			    taskDone++;
			}
		}
		
		int notStartPercent = 0, inProgressPercent = 0, donePercent = 0;
		if (totalTask > 0) {
		    notStartPercent = (taskNotStart * 100) / totalTask;
		    inProgressPercent = (taskInProgress * 100) / totalTask;
		    donePercent = (taskDone * 100) / totalTask;
		}
		
		req.setAttribute("notStartPercent", notStartPercent);
		req.setAttribute("inProgressPercent", inProgressPercent);
		req.setAttribute("donePercent", donePercent);
		
		if ("/profile".equals(path)) {
			System.out.println("Tasks Not Start: " + taskNotStart);
			System.out.println("Tasks In Progress: " + taskInProgress);
			System.out.println("Task Done: " + taskDone);
			System.out.println("Total tasks: " + totalTask);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);
		}
		
		if ("/profile-edit".equals(path)) {
			String taskId = req.getParameter("id");
			Tasks task = taskServices.getTaskById(Integer.parseInt(taskId));
			req.setAttribute("task", task);
			List<Status> listStatus = statusServices.getAllStatus();
			req.setAttribute("listStatus", listStatus);
			System.out.println("Job name: " + task.getStatusId());
			req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
		}
		
		if ("/profile-user".equals(path)) {
			Users user = userServices.getUserById(currentUserId);
			req.setAttribute("user", currentUser);
			List<Roles> listRoles = roleServices.getAllRole();
			req.setAttribute("listRoles", listRoles);
			req.setAttribute("action", "update");
			req.getRequestDispatcher("profile-user.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		if ("/profile-edit".equals(path)) {
			String statusIdStr = req.getParameter("status");
			int statusId = Integer.parseInt(statusIdStr);
			
			String taskIdStr = req.getParameter("id");
			int taskId = Integer.parseInt(taskIdStr);
			
			if (taskIdStr != null) {
				boolean isSuccess = taskServices.updateTaskStatus(statusId, taskId);
			}
			
			resp.sendRedirect(req.getContextPath() + "/profile");
		}
		
		if ("/profile-user".equals(path)) {
			Users currentUser = (Users) req.getAttribute("currentUser");
			
			String userIdStr = currentUser.getId() + "";
			int userId = currentUser.getId();
			
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String avatar = req.getParameter("avatar");
			int roleId = Integer.parseInt(req.getParameter("role"));
			
			if (userIdStr != null) {
				boolean isSuccess = userServices.updateUser(userId, fullName, email, password, avatar, roleId);
			}
			
			resp.sendRedirect(req.getContextPath());
		}
	}
	
}
