package com.fresherprogram.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Claims")
public class Claim {
	
	public Claim(String policyNo, Date dateOccurred, String policyOwner, String engineNo, String chassisNo,
			String vehicleRegistrationNo, BigDecimal sumInsured, String reserveCurrency, BigDecimal reserveAmount,
			String claimStatus) {
		super();
		PolicyNo = policyNo;
		DateOccurred = dateOccurred;
		PolicyOwner = policyOwner;
		EngineNo = engineNo;
		ChassisNo = chassisNo;
		VehicleRegistrationNo = vehicleRegistrationNo;
		SumInsured = sumInsured;
		ReserveCurrency = reserveCurrency;
		ReserveAmount = reserveAmount;
		ClaimStatus = claimStatus;
	}
	public Claim() {
		super();
	}	
	
	public String getClaimNo() {
		return ClaimNo;
	}
	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
	}
	public String getPolicyNo() {
		return PolicyNo;
	}
	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}
	public Date getDateOccurred() {
		return DateOccurred;
	}
	public void setDateOccurred(Date dateOccurred) {
		DateOccurred = dateOccurred;
	}
	public String getPolicyOwner() {
		return PolicyOwner;
	}
	public void setPolicyOwner(String policyOwner) {
		PolicyOwner = policyOwner;
	}
	public String getEngineNo() {
		return EngineNo;
	}
	public void setEngineNo(String engineNo) {
		EngineNo = engineNo;
	}
	public String getChassisNo() {
		return ChassisNo;
	}
	public void setChassisNo(String chassisNo) {
		ChassisNo = chassisNo;
	}
	public String getVehicleRegistrationNo() {
		return VehicleRegistrationNo;
	}
	public void setVehicleRegistrationNo(String vehicleRegistrationNo) {
		VehicleRegistrationNo = vehicleRegistrationNo;
	}
	public BigDecimal getSumInsured() {
		return SumInsured;
	}
	public void setSumInsured(BigDecimal sumInsured) {
		SumInsured = sumInsured;
	}
	public String getReserveCurrency() {
		return ReserveCurrency;
	}
	public void setReserveCurrency(String reserveCurrency) {
		ReserveCurrency = reserveCurrency;
	}
	public BigDecimal getReserveAmount() {
		return ReserveAmount;
	}
	public void setReserveAmount(BigDecimal reserveAmount) {
		ReserveAmount = reserveAmount;
	}
	public String getClaimStatus() {
		return ClaimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		ClaimStatus = claimStatus;
	}

	@Id
	@GeneratedValue(generator = "my_generator")
	@GenericGenerator(name = "my_generator", strategy = "com.fresherprogram.demo.extension.GenerateClaim")
	@Column(name = "ClaimNo", columnDefinition = "nvarchar(8)")	
	private String ClaimNo;
	
	@NotEmpty(message = "Please enter Policy No")
	@Size(min = 2, max = 8, message = "Policy No must be between 2 and 8 character")
	@Pattern(regexp = "^[A-Za-z_0-9\\s+]*$", message = "Invalid Input")
	@Column(name = "PolicyNo", columnDefinition = "nvarchar(8) not null")
	private String PolicyNo;
	
	@NotNull(message = "Please choose Date Occurred")
	@Column(name = "DateOccurred", columnDefinition = "date")
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date DateOccurred;
	
	@Column(name = "PolicyOwner", columnDefinition = "nvarchar(8) not null")
	private String PolicyOwner;
	
	@Column(name = "EngineNo", columnDefinition = "nvarchar(30) not null")
	private String EngineNo;
	
	@Column(name = "ChassisNo", columnDefinition = "nvarchar(30) not null")
	private String ChassisNo;
	
	@Column(name = "VehicleRegistrationNo", columnDefinition = "nvarchar(30) not null")
	private String VehicleRegistrationNo;
	
	@Column(name = "SumInsured", precision = 17, scale = 2,columnDefinition = "decimal (17,2) not null")
	private BigDecimal SumInsured;
	
	@NotEmpty(message = "Please choose Reserve Currency")
	@Column(name = "ReserveCurrency", columnDefinition = "nvarchar(30) not null")
	private String ReserveCurrency;
	
	@NotNull(message = "Please enter Sum Insured")
	@DecimalMin(value = "0", message = "Sum Insured must not be negative")
	@Digits(integer = 17, fraction = 2)
	@Column(name = "ReserveAmount", precision = 17, scale = 2, columnDefinition = "decimal (17,2) not null")
	private BigDecimal ReserveAmount;
	
	
	@Column(name = "ClaimStatus", columnDefinition = "nvarchar(30) not null")
	private String ClaimStatus;
	

}
