
package com.example.roaster.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
@Entity
@Table(
    name = "unavailability",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "date","organization_id"})
    }
)
public class Unavailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "day_of_week", nullable = false)
//    private DayOfWeek dayOfWeek;

    @Column(name="reason")
    private String reason;
    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name="date")
    private LocalDate date;

    @Column(name = "is_active")
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 🔹 REQUIRED BY JPA
    public Unavailability() {
    }
}

//    // ===== GETTERS =====
//
//    public Long getId() {
//        return id;
//    }
//
//    public Long getDoctorId() {
//        return doctorId;
//    }
//
//    public Long getOrganizationId() {
//        return organizationId;
//    }
//
//    public DayOfWeek getDayOfWeek() {
//        return dayOfWeek;
//    }
//
//    public LocalTime getStartTime() {
//        return startTime;
//    }
//
//    public LocalTime getEndTime() {
//        return endTime;
//    }
//
//    public Integer getSlotDurationMin() {
//        return slotDurationMin;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    // ===== SETTERS =====
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setDoctorId(Long doctorId) {
//        this.doctorId = doctorId;
//    }
//
//    public void setOrganizationId(Long organizationId) {
//        this.organizationId = organizationId;
//    }
//
//    public void setDayOfWeek(DayOfWeek dayOfWeek) {
//        this.dayOfWeek = dayOfWeek;
//    }
//
//    public void setStartTime(LocalTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public void setEndTime(LocalTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public void setSlotDurationMin(Integer slotDurationMin) {
//        this.slotDurationMin = slotDurationMin;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
//}
