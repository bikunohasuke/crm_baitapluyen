package crm_baitapluyen.services;

import java.sql.Date;
import java.util.List;

import crm_baitapluyen.repository.JobRepository;
import entity.Jobs;

public class JobServices {
	
	private JobRepository jobRepository = new JobRepository();
	
	public List<Jobs> getAllJob() {
		return jobRepository.findAll();
	}
	
	public boolean insertNewJob(String name, Date startDate, Date endDate) {
		Jobs job = new Jobs();
		job.setName(name);
		job.setStartDate(startDate);
		job.setEndDate(endDate);
		
		return jobRepository.save(job) > 0;
	}
	
	public Jobs getJobById(int jobId) {
		return jobRepository.findById(jobId);
	}
	
	public boolean updateJob(int jobId, String name, Date startDate, Date endDate) {
		return jobRepository.updateById(jobId, name, startDate, endDate) > 0;
	}
	
	public boolean deleteJob(int jobId) {
		return jobRepository.deleteById(jobId) > 0;
	}

}
