package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Jobs;

public class JobRepository {

	public List<Jobs> findAll() {
		List<Jobs> listJobs = new ArrayList<Jobs>();
		
		String query = "SELECT * FROM jobs j";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Jobs job = new Jobs();
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getDate("start_date"));
				job.setEndDate(resultSet.getDate("end_date"));
				
				listJobs.add(job);
			}
		
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listJobs;
	}
	
	public int save(Jobs job) {
		int rowCount = 0;
		
		String query = "INSERT INTO jobs(name, start_date, end_date) VALUE(?, ?, ?)";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, job.getName());
			preparedStatement.setDate(2, job.getStartDate());
			preparedStatement.setDate(3, job.getEndDate());
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return rowCount;
		
	}
	
	public Jobs findById(int id) {
		Jobs job = new Jobs();
		String query = "SELECT * FROM jobs j WHERE j.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getDate("start_date"));
				job.setEndDate(resultSet.getDate("end_date"));
			}
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		return job;
		
	}
	
	public int updateById(int jobId, String name, Date startDate, Date endDate) {
		int rowCount = 0;
		
		String query = "UPDATE jobs j SET j.name = ?, j.start_date = ?, j.end_date = ?\r\n"
				+ "WHERE j.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, startDate);
			preparedStatement.setDate(3, endDate);
			preparedStatement.setInt(4, jobId);
			
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public int deleteById(int id) {
		int rowCount = 0;
		String query = "DELETE FROM jobs j WHERE j.id = ?";
		
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
