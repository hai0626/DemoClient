package com.fresherprogram.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fresherprogram.demo.model.Claim;
import com.fresherprogram.demo.repository.ClaimRepository;
import com.fresherprogram.demo.service.ClaimService;

@Component
public class ClaimDAO implements ClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Override
	public void saveClaim(Claim claim) {
		claimRepository.save(claim);
	}

	@Override
	public List<Claim> listAll() {
		// TODO Auto-generated method stub
		return claimRepository.findAll();
	}

	@Override
	public Claim detaiClaim(String id) {
		// TODO Auto-generated method stub
		return claimRepository.findById(id).get();
	}

}
