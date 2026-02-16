package com.example.roaster.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class AvailabilityRequest {

    private List<DayOfWeek> days;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationMin;

    // ===== NO-ARGS CONSTRUCTOR =====
    public AvailabilityRequest() {
    }

    // ===== GETTERS =====

    public List<DayOfWeek> getDays() {
        return days;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getSlotDurationMin() {
        return slotDurationMin;
    }

    // ===== SETTERS =====

    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setSlotDurationMin(Integer slotDurationMin) {
        this.slotDurationMin = slotDurationMin;
    }
}
