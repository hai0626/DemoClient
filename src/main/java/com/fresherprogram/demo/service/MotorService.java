package com.fresherprogram.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fresherprogram.demo.model.MotorPolicy;

@Service
public interface MotorService {
	List<MotorPolicy> getAll();
	void saveMotorPolicy(MotorPolicy motorPolicy);
	MotorPolicy detailMotor(String id);
	Optional<MotorPolicy> findByRegister(String register);
	Optional<MotorPolicy> findByEngine(String engine);	
	Optional<MotorPolicy> findByChassis(String chassis);
	MotorPolicy findByMotorPolicy (String motorPolicy);
}
