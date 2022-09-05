package com.fresherprogram.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresherprogram.demo.model.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {

}
