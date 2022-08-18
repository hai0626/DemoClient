package com.fresherprogram.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fresherprogram.demo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	
	@Query("SELECT c FROM Client c WHERE c.IdentityNumber = ?1")
	Optional<Client> findClientByIdentityNumber(String identityNumber);
	
	
}
