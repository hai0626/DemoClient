package com.fresherprogram.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.fresherprogram.demo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("SELECT m.Description FROM Message m WHERE m.id = :id")
	String findMessageById(@Param("id") int id);

}
