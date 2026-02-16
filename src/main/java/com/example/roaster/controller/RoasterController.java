package com.example.roaster.controller;
import com.example.roaster.security.JWTUtil;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roaster.dto.AvailabilityRequest;
import com.example.roaster.service.RoasterService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/roaster")
public class RoasterController {
	private final RoasterService rse;
	 private final JWTUtil jwt;

	RoasterController(RoasterService rse, JWTUtil jwt){
		this.rse=rse;
		this.jwt=jwt;
	}

	@PostMapping("/createRules")
	public String createRules(@RequestBody AvailabilityRequest rules,HttpServletRequest request) {
		try
		{
			String authHeader = request.getHeader("Authorization");
	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            throw new RuntimeException("Missing or invalid Authorization header");
	        }
	        String token = authHeader.substring(7); // remove "Bearer "
	        Claims claims = jwt.validateToken(token);
	        System.out.println("Claims: " + claims);

	        Number orgIdNum = (Number) claims.get("orgId");
	        Long orgId = orgIdNum.longValue();
	        Long docId = Long.valueOf(claims.getSubject());
	        System.out.println("orgId from token: " + orgId);
		   return  rse.createRulesService(rules,orgId,docId);
		}
		catch (Exception e) {
	        e.printStackTrace();  
	        throw e;
		    
	}
	
}
}
