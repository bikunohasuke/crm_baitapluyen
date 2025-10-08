package crm_baitapluyen.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;

import crm_baitapluyen.services.JobServices;
import crm_baitapluyen.services.StatusServices;
import crm_baitapluyen.services.TaskServices;
import crm_baitapluyen.services.UserServices;
import entity.Jobs;
import entity.Status;
import entity.Tasks;
import entity.Users;

@WebServlet(name = "taskController", urlPatterns = {"/task", "/task-add", "/task-update", "/task-delete"})
public class TaskController extends HttpServlet {

	private TaskServices taskServices = new TaskServices();
	private UserServices userServices = new UserServices();
	private JobServices jobServices = new JobServices();
	private StatusServices statusServices = new StatusServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		List<Users> listUsers = userServices.getAllUser();
		List<Jobs> listJobs = jobServices.getAllJob();
		List<Status> listStatus = statusServices.getAllStatus();
		
		if ("/task".equals(path)) {
			List<Tasks> listTasks = taskServices.getAllTasks();
			req.setAttribute("listTasks", listTasks);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
		
		if ("/task-add".equals(path)) {
			req.setAttribute("action", "add");
			req.setAttribute("pageTitle", "Thêm mới công việc");
			req.setAttribute("listUsers", listUsers);
			req.setAttribute("listJobs", listJobs);
			req.setAttribute("listStatus", listStatus);
			
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		}
		
		if ("/task-update".equals(path)) {
			String taskId = req.getParameter("id");
			Tasks task = taskServices.getTaskById(Integer.parseInt(taskId));
			req.setAttribute("task", task);
			req.setAttribute("listUsers", listUsers);
			req.setAttribute("listJobs", listJobs);
			req.setAttribute("listStatus", listStatus);
			req.setAttribute("action", "update");
			req.setAttribute("pageTitle", "Sửa thông tin công việc");
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
		}
		
		if ("/task-delete".equals(path)) {
			String taskId = req.getParameter("id");
			taskServices.deleteTask(Integer.parseInt(taskId));
			resp.sendRedirect(req.getContextPath()+ "/task");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		String name = req.getParameter("name");
		String startDateStr = req.getParameter("start_date");
		String endDateStr = req.getParameter("end_date");
		String userIdStr = req.getParameter("user");
		String jobIdStr = req.getParameter("job");
		String statusIdStr = req.getParameter("status");
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate startDateParse = LocalDate.parse(startDateStr, dateTimeFormatter);
		LocalDate endDateParse = LocalDate.parse(endDateStr, dateTimeFormatter);
		java.sql.Date startDate = java.sql.Date.valueOf(startDateParse);
		java.sql.Date endDate = java.sql.Date.valueOf(endDateParse);
		
		int userId = Integer.parseInt(userIdStr);
		int jobId = Integer.parseInt(jobIdStr);
		int statusId = Integer.parseInt(statusIdStr);
		
		if ("/task-add".equals(path)) {
			if (name != null && userIdStr != null && jobIdStr != null && statusIdStr != null) {
				boolean isSuccess = taskServices.insertTask(name, startDate, endDate, userId, jobId, statusId);
			}
			
			resp.sendRedirect(req.getContextPath() + "/task");
		}
		
		if ("/task-update".equals(path)) {
			String id = req.getParameter("id");
			int taskId = Integer.parseInt(id);
			if (id != null) {
				boolean isSuccess = taskServices.updateTask(taskId, name, startDate, endDate, userId, jobId, statusId);
			}
			
			resp.sendRedirect(req.getContextPath() + "/task");
		}
		
	}
	
	
}
