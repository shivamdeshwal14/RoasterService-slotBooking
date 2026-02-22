package com.example.roaster.controller;
import com.example.roaster.security.JWTUtil;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.roaster.dto.AvailabilityRequest;
import com.example.roaster.dto.SlotResponse;
import com.example.roaster.dto.UnavailabilityRequest;
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

	@PostMapping("availability/create/rules")
	public String createRules(@RequestBody AvailabilityRequest rules,HttpServletRequest request) {
		try
		{
			String authHeader = request.getHeader("Authorization");
	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            throw new RuntimeException("Missing or invalid Authorization header");
	        }
	        String token = authHeader.substring(7); 
	        Claims claims = jwt.validateToken(token);
	        System.out.println("Claims: " + claims);			

	        Number orgIdNum = (Number) claims.get("orgId");
	        System.out.println(orgIdNum);
	        Long orgId = orgIdNum.longValue();
	        Long docId = Long.valueOf(claims.getSubject());
	        System.out.println(docId);

	        
	        System.out.println("orgId from token: " + orgId);
		   return  rse.createRulesService(rules,orgId,docId);
		}
		
		catch (Exception e) {
	        e.printStackTrace();  
		}
		return null;
	
		    
	}
	
	@PostMapping("unvailability/create/rules")
	public String createUnavailabiltyRules(@RequestBody UnavailabilityRequest rules,HttpServletRequest request) {
		try
		{
			System.out.println("unavailable hit  successfull");
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
		   return  rse.createUnavailabilityRulesService(rules,docId,orgId);
		}
		catch (Exception e) {
	        e.printStackTrace();  
		}
		return null;
	}
		
		@GetMapping("/org/{orgId}/doctor/{docId}/slots")
		public ResponseEntity<List<SlotResponse>> getDoctorSlots(
		        @PathVariable Long docId,
		        @PathVariable Long orgId,
		        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
		) {
		    List<SlotResponse> slots = rse.getSlotsForDoctor(docId, date,orgId);
		    return ResponseEntity.ok(slots);
	
	
}
}
