package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.StatusRepository;
import entity.Status;

public class StatusServices {

	private StatusRepository statusRepository = new StatusRepository();
	
	public List<Status> getAllStatus() {
		return statusRepository.findAll();
	}
	
}
