package com.fresherprogram.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fresherprogram.demo.model.Claim;

@Service
public interface ClaimService {
	List<Claim> listAll();
	void saveClaim(Claim claim);
	Claim detaiClaim(String id);
}
