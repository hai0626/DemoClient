package com.fresherprogram.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fresherprogram.demo.model.MotorPolicy;

@Service
public interface MotorService {
	List<MotorPolicy> getAll();
	void addMotorPolicy(MotorPolicy motorPolicy);
	MotorPolicy detailMotor(String id);
}
