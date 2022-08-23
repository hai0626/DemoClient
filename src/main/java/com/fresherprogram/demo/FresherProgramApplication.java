package com.fresherprogram.demo;


import java.util.Date;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class FresherProgramApplication {


	private static final Logger logger = LogManager.getLogger(FresherProgramApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FresherProgramApplication.class, args);	
        		
	}
	
	
	

}
