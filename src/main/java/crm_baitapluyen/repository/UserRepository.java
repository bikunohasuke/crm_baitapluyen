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
	
}
