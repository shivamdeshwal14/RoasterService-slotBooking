package com.example.roaster.service;

import java.time.DayOfWeek;

import org.springframework.stereotype.Service;

import com.example.roaster.entity.Availability;
import com.example.roaster.dto.AvailabilityRequest;
import com.example.roaster.repository.AvailabilityRepository;

@Service
public class RoasterService {
	private final AvailabilityRepository repo;
	
	
	public RoasterService(AvailabilityRepository repo) {
		this.repo=repo;
	}
	
	
	public String createRulesService(AvailabilityRequest rules,Long orgId,Long doctorId) {
		
		   if (rules.getStartTime().isAfter(rules.getEndTime())) {
	            throw new RuntimeException("Start time must be before end time");
	        }

	        for (DayOfWeek day : rules.getDays()) {

	            // check if availability already exists for this doctor + day
	        	Availability availability =
	        	        repo.findByDoctorIdAndDayOfWeek(doctorId, day)
	        	            .orElseGet(Availability::new);


	            availability.setDoctorId(doctorId);
	            availability.setOrganizationId(orgId);
	            availability.setDayOfWeek(day);
	            availability.setStartTime(rules.getStartTime());
	            availability.setEndTime(rules.getEndTime());
	            availability.setSlotDurationMin(rules.getSlotDurationMin());
	            availability.setActive(true);

	            repo.save(availability);
	        }

	        return "Availability rules saved successfully";
	}

}
