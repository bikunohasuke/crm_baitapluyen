package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.LoginRepository;
import entity.Users;

public class LoginServices {

	private LoginRepository loginRepository = new LoginRepository();
	
	public List<Users> login(String email, String password) {
		List<Users> listUsers = loginRepository.findByEmailAndPassword(email, password);
		
		if (listUsers.isEmpty()) {
			System.out.println("Đăng nhập thất bại!");
		} else {
			System.out.println("Đăng nhập thành công!");
		}
		
		return listUsers;
	}
	
}
