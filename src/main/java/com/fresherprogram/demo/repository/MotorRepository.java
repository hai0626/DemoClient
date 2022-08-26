package com.fresherprogram.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresherprogram.demo.model.MotorPolicy;

@Repository
public interface MotorRepository extends JpaRepository<MotorPolicy, String> {

}
