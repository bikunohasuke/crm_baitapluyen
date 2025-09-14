package crm_baitapluyen.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MySQLConfig;
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
}
