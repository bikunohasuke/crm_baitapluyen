package crm_baitapluyen.services;

import java.util.List;

import crm_baitapluyen.repository.JobRepository;
import entity.Jobs;

public class JobServices {
	
	private JobRepository jobRepository = new JobRepository();
	
	public List<Jobs> getAllJob() {
		return jobRepository.findAll();
	}

}
