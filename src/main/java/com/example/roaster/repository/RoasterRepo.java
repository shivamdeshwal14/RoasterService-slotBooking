package com.example.roaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roaster.Entity.Availability;

public interface RoasterRepo extends JpaRepository<Availability,Long>{
	

}
