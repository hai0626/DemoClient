package com.fresherprogram.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fresherprogram.demo.model.MotorPolicy;
import com.fresherprogram.demo.repository.MotorRepository;
import com.fresherprogram.demo.service.MotorService;

@Component
public class MotorDAO implements MotorService {
	
	@Autowired
	private MotorRepository motorRepository;
	
	@Override
	public List<MotorPolicy> getAll() {
		
		return motorRepository.findAll();
	}

	@Override
	public void addMotorPolicy(MotorPolicy motorPolicy) {
		motorRepository.save(motorPolicy);

	}
	@Override
	public MotorPolicy detailMotor(String id) {		
		return motorRepository.findById(id).get();
	}

	@Override
	public Optional<MotorPolicy> findByRegister(String register) {
		
		return motorRepository.findByRegister(register);
	}

	@Override
	public Optional<MotorPolicy> findByEngine(String engine) {
		// TODO Auto-generated method stub
		return motorRepository.findByEngine(engine);
	}

	@Override
	public Optional<MotorPolicy> findByChassis(String chassis) {
		// TODO Auto-generated method stub
		return motorRepository.findByChassis(chassis);
	}

}
