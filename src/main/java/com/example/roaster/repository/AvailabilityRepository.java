	package com.example.roaster.repository;
	
	import java.time.DayOfWeek;
	import java.util.Optional;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.example.roaster.entity.Availability;
	
	public interface AvailabilityRepository extends JpaRepository<Availability,Long>{
	
		Optional<Availability> findByDoctorIdAndDayOfWeekAndOrganizationId(  Long doctorId, DayOfWeek dayOfWeek, Long orgId);
		
	
	}
	
