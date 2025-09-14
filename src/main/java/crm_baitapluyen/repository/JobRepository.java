package crm_baitapluyen.repository;

import java.sql.Connection;
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
				job.setStartDate(resultSet.getString("start_date"));
				job.setEndDate(resultSet.getString("end_date"));
				
				listJobs.add(job);
			}
		
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listJobs;
	}
}
