package com.example.roaster.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.roaster.dto.AvailabilityRequest;
import com.example.roaster.dto.SlotResponse;
import com.example.roaster.dto.UnavailabilityRequest;
import com.example.roaster.entity.Availability;
import com.example.roaster.entity.Unavailability;
import com.example.roaster.repository.AvailabilityRepository;
import com.example.roaster.repository.UnavailabilityRepository;

@Service
public class RoasterService {

    private final AvailabilityRepository repo;
    private final UnavailabilityRepository unp;
    			   

    public RoasterService( AvailabilityRepository repo,UnavailabilityRepository unp) {
        this.repo = repo;
        this.unp=unp;
    }

    
//    availability rules;
    
    public String createRulesService( AvailabilityRequest rules, Long orgId, Long doctorId) {

        if (rules.getStartTime().isAfter(rules.getEndTime())) {
            throw new RuntimeException("Start time must be before end time");
        }

        for (DayOfWeek day : rules.getDays()) {
        	if(repo.findByDoctorIdAndDayOfWeekAndOrganizationId(doctorId, day, orgId).isPresent()) throw new RuntimeException("rule already exists for this day-"+day);
            Availability availability =
                    repo.findByDoctorIdAndDayOfWeekAndOrganizationId(doctorId, day, orgId)
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

    
//    unavailability rules
    public String createUnavailabilityRulesService(UnavailabilityRequest rules,Long docId,Long orgId ) {
    	
    	for(LocalDate date:rules.getDates()) {
    		
    		if(unp.findByDoctorIdAndDateAndOrganizationId(docId, date, orgId).isPresent()) throw new RuntimeException("Leaves already exists");
    		Unavailability unv=unp.findByDoctorIdAndDateAndOrganizationId(docId, date, orgId)
    				.orElseGet(Unavailability::new);
    		
    		unv.setOrganizationId(orgId);
    		unv.setDoctorId(docId);
    		unv.setDate(date);
    		unv.setActive(true);
    		unv.setReason(rules.getReason());
    		unp.save(unv);    		
    	}
    	return "Leaves added succesfully";
    	
    }
    
    
    
   //get slots for doctor by using orgId and DocId
    public List<SlotResponse> getSlotsForDoctor(Long docId, LocalDate date,Long orgId) {

    	Unavailability unv=unp.findByDoctorIdAndDateAndOrganizationId(docId, date, orgId).orElse(null);
    	if(unv!=null &&  date.equals(unv.getDate())) {
    		throw new RuntimeException("Doctor is on leave on"+date+" because "+unv.getReason());
    	}
        Availability availability = repo.findByDoctorIdAndDayOfWeekAndOrganizationId( docId, date.getDayOfWeek(),orgId).orElseThrow(() ->
                        new RuntimeException("Doctor not available on this day on this organization"));

        return generateSlots(availability.getStartTime(),availability.getEndTime(), availability.getSlotDurationMin(),docId,orgId,date);
    }
    
    
    
    
    
//    unavailability service;
    public Unavailability getUnavailability(Long orgId,Long docId,LocalDate date) {
    	Unavailability unv=unp.findByDoctorIdAndDateAndOrganizationId(docId, date, orgId).orElseThrow(()->{
    		throw new RuntimeException("no unavailability Found");
    	});
    	
    	return unv;
    	
 
    }

    
    
    
    private List<SlotResponse> generateSlots(LocalTime startTime,LocalTime endTime,  int durationMin,Long docId,Long orgId,LocalDate date) {

        List<SlotResponse> slots = new ArrayList<>();

        LocalTime current = startTime;

        while (!current.plusMinutes(durationMin).isAfter(endTime)) {

            slots.add(new SlotResponse(
                    docId,orgId,date, current, current.plusMinutes(durationMin)
            ));

            current = current.plusMinutes(durationMin);
        }

        return slots;
    }
   
}
