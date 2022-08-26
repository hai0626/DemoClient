package com.fresherprogram.demo.dao;

import java.util.List;

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

}
