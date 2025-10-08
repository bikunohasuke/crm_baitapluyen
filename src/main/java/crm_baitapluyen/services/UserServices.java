package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.TaskRepository;
import crm_baitapluyen.repository.UserRepository;
import entity.Users;

public class UserServices {

	private UserRepository userRepository = new UserRepository();
	private TaskRepository taskRepository = new TaskRepository();
	
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}

	public boolean insertUsers(String fullName, String email, String password, String avatar, int roleId) {
		return userRepository.save(fullName, email, password, avatar, roleId) > 0;
	}
	
	public Users getUserById(int userId) {
		return userRepository.findById(userId);
	}
	
	public boolean updateUser(int userId, String fullname, String email, String password, String avatar, int roleId) {
		return userRepository.updateById(userId, email, password, fullname, avatar, roleId) > 0;
	}
	
	public boolean deleteUser(int userId) {
		return userRepository.deleteById(userId) > 0;
	}
	
}
