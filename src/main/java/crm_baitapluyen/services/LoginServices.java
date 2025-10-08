package crm_baitapluyen.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import crm_baitapluyen.repository.LoginRepository;
import entity.Users;
import filter.AuthenticationFilter;

public class LoginServices {

	private LoginRepository loginRepository = new LoginRepository();

	public List<Cookie> listCookies = new ArrayList<Cookie>();
	
	public List<Users> login(String email, String password, String remember) {
		List<Users> listUsers = loginRepository.findByEmailAndPassword(email, password);
		
		if (listUsers.isEmpty()) {
			System.out.println("Đăng nhập thất bại!");
		} else {
			Cookie cId = new Cookie("id", listUsers.get(0).getId() + "");
			cId.setMaxAge(8 * 60);
			listCookies.add(cId);
			Cookie cRole = new Cookie("role", listUsers.get(0).getRoleId() + "");
			cRole.setMaxAge(8 * 60);
			listCookies.add(cRole);
			System.out.println("Đăng nhập thành công!");
			
			if (remember != null) {
				Cookie cEmail = new Cookie("email", email);
				cEmail.setMaxAge(1 * 60);
				Cookie cPassword = new Cookie("password", password);
				cPassword.setMaxAge(1 * 60);
				
				listCookies.add(cEmail);
				listCookies.add(cPassword);
			}
		}
		
		return listUsers;
	}
	
}
