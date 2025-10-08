package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Jobs;
import entity.Roles;

public class RoleRepository {

	public List<Roles> findAll() {
		List<Roles> listRoles = new ArrayList<>();
		
		String query = "SELECT * FROM roles r";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Roles role = new Roles();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				
				listRoles.add(role);
			}
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listRoles;
	}
	
	public int save(String name, String desc) {
		int rowCount = 0;
		
		String query = "INSERT INTO roles(name, description) VALUE(?, ?)";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, desc);
			
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public Roles findById(int id) {
		Roles role = new Roles();
		String query = "SELECT * FROM roles r WHERE r.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
			}
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		return role;
	}
	
	public int updateById(int roleId, String name, String description) {
		int rowCount = 0;
		
		String query = "UPDATE roles r SET r.name = ?, r.description = ? WHERE r.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setInt(3, roleId);
			
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public int deleteById(int id) {
		int rowCount = 0;
		
		String query = "DELETE FROM roles r WHERE r.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			rowCount = preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return rowCount;
	}
}
