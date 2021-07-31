package com.example.api.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// used for health checks and testing connection
@RestController
@RequestMapping("/ping")
public class PingController {

	@GetMapping
	public String pingTest() {
		return "Healthy";
	}
}
