package com.example.roaster.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UnavailabilityResponse {
	   private Long docId;
	   private Long orgId;
	   private List<LocalDate> dates;
	    private LocalTime startTime;
	    private LocalTime endTime;
	    private String reason;
}
