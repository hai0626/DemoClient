package com.fresherprogram.demo.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Clients")
public class Client   {
	@Id
	@GeneratedValue(generator = "my_generator")
	@GenericGenerator(name = "my_generator", strategy = "com.fresherprogram.demo.extension.Generate")
	@Column(name = "id",columnDefinition = "nvarchar(8) ")
	private String Id;

	public Client(String firstName, String lastName, String gender, Date i, String identityNumber, String maritalStatus,
			String address, String country) {
		FirstName = firstName;
		LastName = lastName;
		Gender = gender;
		DateOfBirth = i;
		IdentityNumber = identityNumber;
		MaritalStatus = maritalStatus;
		Address = address;
		Country = country;
	}
	
	public Client() {
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public String getIdentityNumber() {
		return IdentityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		IdentityNumber = identityNumber;
	}

	public String getMaritalStatus() {
		return MaritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}
	
	@NotEmpty(message = "Please enter FirstName")
	@Size(min=2,message="LastName must be between 2 and 60 character")
	@Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
	@Column(name = "FirstName", columnDefinition = "nvarchar(60) not null")	
	private String FirstName;

	
	@NotEmpty(message = "Please enter LastName")
	@Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
	@Size(min=2,max=60,message="FirstName must be between 2 and 60 character")
	@Column(name = "LastName", columnDefinition = "nvarchar(60) not null")
	private String LastName;
	
	@NotEmpty(message = "Please enter Gender")
	@Column(name = "Gender", columnDefinition = "nvarchar(30) not null")
	private String Gender;

	@NotNull(message = "Please enter birth date")
    
	@Column(name = "DateOfBirth", columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date DateOfBirth;

	
	@NotEmpty(message = "Please enter IdentityNumber")
	@Column(name = "IdentityNumber", columnDefinition = "nvarchar(15) not null")
	private String IdentityNumber;

	@NotEmpty(message = "Please enter MaritalStatus")
	@Column(name = "MaritalStatus", columnDefinition = "nvarchar(30) not null")
	private String MaritalStatus;
	
	@NotEmpty(message = "Please enter Address")
	@Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")
	@Size(min=2,max=60,message="FirstName must be between 2 and 120 character")
	@Column(name = "Address", columnDefinition = "nvarchar(120) not null")
	private String Address;
	
	@NotEmpty(message = "Please enter Country")
	@Column(name = "Country", columnDefinition = "nvarchar(30) not null")
	private String Country;

}
