package crm_baitapluyen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.TaskServices;
import entity.Tasks;
import entity.Users;

@WebServlet(name = "utilController", urlPatterns = {"", "/404"})
public class UtilController extends HttpServlet {
	
	private TaskServices taskServices = new TaskServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		Users currentUser = (Users) req.getAttribute("currentUser");
		req.setAttribute("currentUser", currentUser);
		
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
		
		req.setAttribute("taskNotStart", taskNotStart);
		req.setAttribute("taskInProgress", taskInProgress);
		req.setAttribute("taskDone", taskDone);
		
		int notStartPercent = 0, inProgressPercent = 0, donePercent = 0;
		if (totalTask > 0) {
			notStartPercent = (taskNotStart * 100) / totalTask;
			inProgressPercent = (taskInProgress * 100) / totalTask;
			donePercent = (taskDone * 100) / totalTask;
		}
		
		req.setAttribute("notStartPercent", notStartPercent);
		req.setAttribute("inProgressPercent", inProgressPercent);
		req.setAttribute("donePercent", donePercent);
		
		if ("".equals(path)) {
			System.out.println("CurrentUserID: " + currentUserId);
			System.out.println("CurrentUserRoleID: " + req.getAttribute("roleId"));
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}
		
		if ("/404".equals(path)) {
			req.getRequestDispatcher("404.jsp").forward(req, resp);
		}
		
	}
}
