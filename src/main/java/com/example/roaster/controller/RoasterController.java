package com.example.roaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roaster")
public class RoasterController {

	@GetMapping("/hit")
	public String check() {
		return "hit succesfull";
	}
	
}
