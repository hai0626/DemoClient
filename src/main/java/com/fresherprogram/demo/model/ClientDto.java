package com.fresherprogram.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
	@NotNull(message = "Thiếu FirstName")
	private String FirstName;

	@NotNull(message = "Thiếu LastName")
	private String LastName;

	@NotNull(message = "Thiếu LastName")
	private String Gender;

	@NotNull(message = "Thieu DateOfBirth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date DateOfBirth;


	@NotNull(message = "Thiếu IdentityNumber")
	private String IdentityNumber;

	@NotNull(message = "Thiếu MaritalStatus")
	private String MaritalStatus;

	@NotNull(message = "Thiếu Address")
	private String Address;

	@NotNull(message = "Thiếu Country")
	private String Country;
}
