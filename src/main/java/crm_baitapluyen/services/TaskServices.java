package crm_baitapluyen.services;

import java.sql.Date;
import java.util.List;

import crm_baitapluyen.repository.TaskRepository;
import entity.Tasks;

public class TaskServices {
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Tasks> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public boolean insertTask(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		return taskRepository.save(name, startDate, endDate, userId, jobId, statusId) > 0;
	}
	
	public Tasks getTaskById(int taskId) {
		return taskRepository.findById(taskId);
	}
	
	public List<Tasks> getTaskByJob(int jobId) {
		return taskRepository.findByJobId(jobId);
	}
	
	public boolean updateTask(int taskId, String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		return taskRepository.updateById(taskId, name, startDate, endDate, userId, jobId, statusId) > 0;
	}
	
	public boolean updateTaskStatus(int statusId, int taskId) {
		return taskRepository.updateStatus(statusId, taskId) > 0;
	}
	
	public boolean deleteTask(int taskId) {
		return taskRepository.deleteById(taskId) > 0;
	}
	
	public List<Tasks> getTasksByUser(int currentUserId) {
		return taskRepository.findByUserId(currentUserId);
	}
	
	public List<Tasks> getTasksByUserAndStatus(int userId, int statusId) {
		return taskRepository.findByUserIdAndStatusId(userId, statusId);
	}
}
