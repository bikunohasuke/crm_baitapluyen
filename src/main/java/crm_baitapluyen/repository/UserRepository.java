package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Users;

public class UserRepository {

	/*
	 * Cách đặt tên hàm trong repo để gợi nhớ tới câu truy vấn
	 * 
	 * Ví dụ: SELECT *			findByEmailAndPasswordOrUsername()
	 * 		  FROM users u
	 * 		  WHERE u.email = '' AND u.password = '' OR	u.username
	 * 
	 * SELECT *
	 * FROM roles r
	 * WHERE r.name = '' OR id = ''		findByNameOrId
	 * 
	 */
	
	public List<Users> findAll() {
		List<Users> listUsers = new ArrayList<Users>();
		
		String query = "SELECT * FROM users u JOIN roles r ON u.role_id = r.id";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				user.setFullname(resultSet.getString("fullname"));
				user.setEmail(resultSet.getString("email"));
				user.setRoleDescription(resultSet.getString("description"));
				
				listUsers.add(user);
			}
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listUsers;
	}
	
	public int save(String fullName, String email, String password, String avatar, int roleId) {
		int rowCount = 0;
		
		String query = "INSERT INTO users(email, password, fullname, avatar, role_id) VALUE(?, ?, ?, ?, ?)";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, email);
			prepareStatement.setString(2, password);
			prepareStatement.setString(3, fullName);
			prepareStatement.setString(4, avatar);
			prepareStatement.setInt(5, roleId);
			
			rowCount = prepareStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
		}
		
		return rowCount;
	}
	
	public Users findById(int id) {
		Users user = new Users();
		String query = "SELECT * FROM users u WHERE u.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setFullname(resultSet.getString("fullname"));
				user.setPassword(resultSet.getString("password"));
				user.setAvatar(resultSet.getString("avatar"));
				user.setRoleId(resultSet.getInt("role_id"));
			}
		} catch (Exception e) {
			System.out.println("Query Executing Error: " + e.getMessage());
		}
		
		return user;
	}
	
	public int updateById(int userId, String email, String password, String fullname, String avatar, int roleId) {
		int rowCount = 0;
		
		String query = "UPDATE users u SET u.email = ?, u.password = ?, u.fullname = ?, u.avatar = ?, u.role_id = ?\r\n"
				+ "WHERE u.id = ?";
		
		Connection connection = MySQLConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, fullname);
			preparedStatement.setString(4, avatar);
			preparedStatement.setInt(5, roleId);
			preparedStatement.setInt(6, userId);
			
			rowCount = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rowCount;
	}
	
	public int deleteById(int id) {
		int rowCount = 0;
		
		String query = "DELETE FROM users u WHERE u.id = ?";
		
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
