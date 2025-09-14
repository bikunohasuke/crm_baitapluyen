package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.RoleRepository;
import entity.Roles;

public class RoleServices {

	private RoleRepository roleRepository = new RoleRepository();
	
	public List<Roles> getAllRole() {
		return roleRepository.findAll();
	}
	
}
