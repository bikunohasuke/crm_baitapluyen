package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Tasks;

public class TaskRepository {
	public List<Tasks> findAll() {
		List<Tasks> listTasks = new ArrayList<Tasks>();
		
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, u.fullname AS user, j.name AS job, s.name AS status\r\n"
				+ "FROM tasks t\r\n"
				+ "JOIN users u ON t.user_id = u.id\r\n"
				+ "JOIN jobs j ON t.job_id = j.id\r\n"
				+ "JOIN status s ON t.status_id = s.id";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tasks task = new Tasks();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date"));
				task.setEndDate(resultSet.getDate("end_date"));
				task.setUser(resultSet.getString("user"));
				task.setJob(resultSet.getString("job"));
				task.setStatus(resultSet.getString("status"));
				
				listTasks.add(task);
			}
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listTasks;
	}
	
	public List<Tasks> findByUserId(int userId) {
		List<Tasks> listTasks = new ArrayList<Tasks>();
		
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, j.name AS job, s.name AS status, s.id AS status_id\r\n"
				+ "FROM tasks t\r\n"
				+ "JOIN jobs j ON t.job_id = j.id\r\n"
				+ "JOIN status s ON t.status_id = s.id\r\n"
				+ "WHERE t.user_id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tasks task = new Tasks();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setJob(resultSet.getString("job"));
				task.setStartDate(resultSet.getDate("start_date"));
				task.setEndDate(resultSet.getDate("end_date"));
				task.setStatus(resultSet.getString("status"));
				task.setStatusId(resultSet.getInt("status_id"));
				listTasks.add(task);
			}
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		return listTasks;
		
	}
	
	public List<Tasks> findByJobId(int jobId) {
		List<Tasks> listTasks = new ArrayList<Tasks>();
		
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, t.job_id, j.name AS job, u.avatar AS user_avatar, u.fullname AS user, t.status_id, s.name AS status\r\n"
				+ "FROM tasks t \r\n"
				+ "JOIN jobs j ON t.job_id = j.id\r\n"
				+ "JOIN users u ON t.user_id = u.id\r\n"
				+ "JOIN status s ON t.status_id = s.id\r\n"
				+ "WHERE t.job_id = ?\r\n"
				+ "ORDER BY u.fullname, s.id";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, jobId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tasks task = new Tasks();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date"));
				task.setEndDate(resultSet.getDate("end_date"));
				task.setJob(resultSet.getString("job"));
				task.setUser(resultSet.getString("user"));
				task.setUserAvatar(resultSet.getString("user_avatar"));
				task.setStatusId(resultSet.getInt("status_id"));
				task.setStatus(resultSet.getString("status"));
				listTasks.add(task);
			}
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		return listTasks;
		
	}
	
	public int save(String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		int rowCount = 0;
		
		String query = "INSERT INTO tasks(name, start_date, end_date, user_id, job_id, status_id) VALUE(?, ?, ?, ?, ?, ?)";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5, jobId);
			preparedStatement.setInt(6, statusId);
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public Tasks findById(int id) {
		Tasks task = new Tasks();
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, t.user_id, t.job_id, j.name AS job, t.status_id, s.name AS status \r\n"
				+ "FROM tasks t \r\n"
				+ "JOIN jobs j ON t.job_id = j.id\r\n"
				+ "JOIN status s ON t.status_id = s.id\r\n"
				+ "WHERE t.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date"));
				task.setEndDate(resultSet.getDate("end_date"));
				task.setUserId(resultSet.getInt("user_id"));
				task.setJobId(resultSet.getInt("job_id"));
				task.setJob(resultSet.getString("job"));
				task.setStatusId(resultSet.getInt("status_id"));
				task.setStatus(resultSet.getString("status"));
			}
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		return task;
		
	}
	
	public List<Tasks> findByUserIdAndStatusId(int userId, int statusId) {
		List<Tasks> listTasks = new ArrayList<Tasks>();
		
		String query = "SELECT * FROM tasks t WHERE t.user_id = ? AND t.status_id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, statusId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Tasks task = new Tasks();
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date"));
				task.setEndDate(resultSet.getDate("end_date"));
				task.setStatusId(resultSet.getInt("status_id"));
				listTasks.add(task);
			}
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		return listTasks;
		
	}
	
	public int updateById(int taskId, String name, Date startDate, Date endDate, int userId, int jobId, int statusId) {
		int rowCount = 0;
		
		String query = "UPDATE tasks t SET t.name = ?, t.start_date = ?, t.end_date = ?, t.user_id = ?, t.job_id = ?, t.status_id = ?\r\n"
				+ "WHERE t.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5, jobId);
			preparedStatement.setInt(6, statusId);
			preparedStatement.setInt(7, taskId);
			
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		
		return rowCount;
		
	}
	
	public int updateStatus(int statusId, int taskId) {
		int rowCount = 0;
		
		String query = "UPDATE tasks t SET t.status_id = ? WHERE t.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, statusId);
			preparedStatement.setInt(2, taskId);
			rowCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public int deleteById(int id) {
		int rowCount = 0;
		
		String query = "DELETE FROM tasks t WHERE t.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
}
