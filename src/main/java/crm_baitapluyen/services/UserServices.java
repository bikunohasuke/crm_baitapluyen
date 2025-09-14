package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.UserRepository;
import entity.Users;

public class UserServices {

	private UserRepository userRepository = new UserRepository();
	
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}
	
}
