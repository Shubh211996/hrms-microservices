package com.hrms.leaveservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.leaveservice.entity.Leave;
import com.hrms.leaveservice.repository.LeaveRepository;

@Service
public class LeaveService {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	public Leave saveLeave(Leave leave) {
		return leaveRepository.save(leave);
	}
	
	public List<Leave> getAllLeaves(){
		return leaveRepository.findAll();
	}
	
	public Optional<Leave> getLeaveById(Long id){
		return leaveRepository.findById(id);
	}
	
	public Leave updateLeave(Long id, Leave updatedLeave) {
		Optional<Leave> optionalLeave = leaveRepository.findById(id);
		if(optionalLeave.isPresent()) {
			Leave existingLeave = optionalLeave.get();
			existingLeave.setEmployeeId(updatedLeave.getEmployeeId());
			existingLeave.setLeaveType(updatedLeave.getLeaveType());
			existingLeave.setStartDate(updatedLeave.getStartDate());
			existingLeave.setEndDate(updatedLeave.getEndDate());
			existingLeave.setStatus(updatedLeave.getStatus());
			return leaveRepository.save(existingLeave);
		}else {
			return null;
		}
	}
	
	public void deleteLeave(Long id) {
		leaveRepository.deleteById(id);
	}

}
