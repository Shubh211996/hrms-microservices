package com.hrms.leaveservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.leaveservice.entity.Leave;
import com.hrms.leaveservice.service.LeaveService;

@RestController
@RequestMapping("api/leaves")
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	
	@PostMapping
	public ResponseEntity<Leave> createLeave(@RequestBody Leave leave){
		Leave savedLeave = leaveService.saveLeave(leave);
		return ResponseEntity.ok(savedLeave);
	}
	
	@GetMapping
	public ResponseEntity<List<Leave>> getAllLeaves(){
		List<Leave> leaves = leaveService.getAllLeaves();
		return ResponseEntity.ok(leaves);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Leave> getLeaveById(@PathVariable Long id){
		Optional<Leave> leave = leaveService.getLeaveById(id);
		return leave.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Leave> updateLeave(@PathVariable Long id, @RequestBody Leave updatedLeave){
		Leave leave = leaveService.updateLeave(id, updatedLeave);
		if(leave != null) {
			return ResponseEntity.ok(leave);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLeave(@PathVariable Long id){
		leaveService.deleteLeave(id);
		return ResponseEntity.noContent().build();
	}
}
