package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.RoleRepository;
import entity.Roles;

public class RoleServices {

	private RoleRepository roleRepository = new RoleRepository();
	
	public List<Roles> getAllRole() {
		return roleRepository.findAll();
	}
	
	public boolean insertRole(String name, String desc) {
//		Roles role = new Roles();
//		role.setName(name);
//		role.setDescription(description);
		
		return roleRepository.save(name, desc) > 0;
	}
	
	public Roles getRoleById(int id) {
		return roleRepository.findById(id);
	}
	
	public boolean updateRole(int roleId, String name, String description) {
		return roleRepository.updateById(roleId, name, description) > 0;
	}
	
	public boolean deleteRole(int jobId) {
		return roleRepository.deleteById(jobId) > 0;
	}
	
}
