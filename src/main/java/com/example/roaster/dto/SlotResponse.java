package com.example.roaster.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SlotResponse(
        Long doctorId,
        Long orgId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {}
