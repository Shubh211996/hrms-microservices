package com.hrms.leaveservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	@Value("${app.message:Default message}")
	private String message;
	
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}

}
