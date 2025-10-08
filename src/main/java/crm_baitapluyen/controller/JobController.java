package crm_baitapluyen.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_baitapluyen.services.JobServices;
import crm_baitapluyen.services.TaskServices;
import entity.Jobs;
import entity.Tasks;

@WebServlet(name = "jobController", urlPatterns = {"/groupwork", "/groupwork-add", "/groupwork-update", "/groupwork-delete",
												   "/groupwork-details"})
public class JobController extends HttpServlet {

	private JobServices jobServices = new JobServices();
	private TaskServices taskServices = new TaskServices();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		
		if ("/groupwork".equals(path)) {
			List<Jobs> listJobs = jobServices.getAllJob();
			req.setAttribute("listJobs", listJobs);
			req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
		}
		
		if ("/groupwork-add".equals(path)) {
			req.setAttribute("action", "add");
			req.setAttribute("pageTitle", "Thêm mới dự án");
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		}
		
		if ("/groupwork-update".equals(path)) {
			String jobId = req.getParameter("id");
			Jobs job = jobServices.getJobById(Integer.parseInt(jobId));
			req.setAttribute("job", job);
			req.setAttribute("action", "update");
			req.setAttribute("pageTitle", "Sửa thông tin dự án");
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		}
		
		if ("/groupwork-details".equals(path)) {
			String jobId = req.getParameter("id");
			List<Tasks> listTasks = taskServices.getTaskByJob(Integer.parseInt(jobId));
			req.setAttribute("listTasks", listTasks);
			int totalTask = listTasks.size(), taskNotStart = 0, taskInProgress = 0, taskDone = 0;
			
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
			
			
			
			req.getRequestDispatcher("groupwork-details.jsp").forward(req, resp);
		}
		
		if ("/groupwork-delete".equals(path)) {
			String jobId = req.getParameter("id");
			jobServices.deleteJob(Integer.parseInt(jobId));
			resp.sendRedirect(req.getContextPath() + "/groupwork");
		}
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8"); 
		resp.setContentType("text/html; charset=UTF-8");
		
		String path = req.getServletPath();
		String name = req.getParameter("name");
		String startDateString = req.getParameter("startDate");
		String endDateString = req.getParameter("endDate");
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate startDateParse = LocalDate.parse(startDateString, dateTimeFormatter);
		LocalDate endDateParse = LocalDate.parse(endDateString, dateTimeFormatter);
		java.sql.Date startDate = java.sql.Date.valueOf(startDateParse);
		java.sql.Date endDate = java.sql.Date.valueOf(endDateParse);
		
		
		if ("/groupwork-add".equals(path)) {
			if (name != null) {
				boolean isSuccess = jobServices.insertNewJob(name, startDate, endDate);
			}
			
			resp.sendRedirect(req.getContextPath() + "/groupwork");
		}
		
		if ("/groupwork-update".equals(path)) {
			String id = req.getParameter("id");
			int jobId = Integer.parseInt(id);
			if (id != null) {
				boolean isSuccess = jobServices.updateJob(jobId, name, startDate, endDate);
			}
			
			resp.sendRedirect(req.getContextPath() + "/groupwork");
		}
	}
}
