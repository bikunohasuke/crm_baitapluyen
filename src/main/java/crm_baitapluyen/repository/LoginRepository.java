package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
import entity.Users;

public class LoginRepository {
	
	public List<Users> findByEmailAndPassword(String email, String password) {
		
		// Chuẩn bị câu truy vấn          SQL Injection
		String query = "SELECT *\r\n"
				+ "FROM users u\r\n"
				+ "WHERE u.email = ? AND u.password = ?";
		// Mở kết nối CSDL
		Connection connection = MySQLConfig.getConnection();
		// Tạo một danh sách rỗng để biến dữ liệu từ câu truy vấn trong Resultset thành mảng/danh sách
		List<Users> listUsers = new ArrayList<Users>();
		try {
			// Truyền câu truy vấn vào connection mới vừa kết nối
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, email);
			prepareStatement.setString(2, password);
			/*
			 * executeQuery:  SELECT
			 * executeUpdate: Không phải là câu SELECT
			 */
			ResultSet resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				Users user = new Users();
				user.setId(resultSet.getInt("id"));
				user.setFullname(resultSet.getString("fullname"));
				user.setRoleId(resultSet.getInt("role_id"));
				
				listUsers.add(user);
			}
			
		} catch (Exception e) {
			System.out.println("Query executing error: " + e.getMessage());
		}
		
		return listUsers;
	}
}
