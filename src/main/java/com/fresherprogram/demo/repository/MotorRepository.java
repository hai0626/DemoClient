package com.fresherprogram.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fresherprogram.demo.model.MotorPolicy;

@Repository
public interface MotorRepository extends JpaRepository<MotorPolicy, String> {
	
	@Query("SELECT m FROM MotorPolicy m WHERE m.VehicleRegistrationNo = ?1")
	Optional<MotorPolicy> findByRegister(String register);
	
	@Query("SELECT m FROM MotorPolicy m WHERE m.ChassisNo = ?1")
	Optional<MotorPolicy> findByChassis(String chassis);
	
	@Query("SELECT m FROM MotorPolicy m WHERE m.EngineNo = ?1")
	Optional<MotorPolicy> findByEngine(String engine);

}
