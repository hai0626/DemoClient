package com.fresherprogram.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "MotorPolicys")
public class MotorPolicy {

	@Id
	@GeneratedValue(generator = "my_generator")
	@GenericGenerator(name = "my_generator", strategy = "com.fresherprogram.demo.extension.GeneratePolicy")
	@Column(name = "PolicyNo", columnDefinition = "nvarchar(8)")
	private String PolicyNo;

	@NotNull(message = "Please choose Inception Date")
	@Column(name = "InceptionDate", columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date InceptionDate;

	@NotEmpty(message = "Please enter Policy Owner")
	@Size(min = 8, max = 8, message = "Policy Owner must be 8 character")
	@Pattern(regexp = "^[A-Za-z_0-9\\s+]*$", message = "Invalid Input")
	@Column(name = "PolicyOwner", columnDefinition = "nvarchar(8) not null")
	private String PolicyOwner;

	@NotNull(message = "Please choose Expiry Date")
	@Column(name = "ExpiryDate", columnDefinition = "date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ExpiryDate;

	@NotEmpty(message = "Please enter Engine No")
	@Size(min = 2, max = 30, message = "Engine No must be between 2 and 30 character")
	@Pattern(regexp = "^[A-Za-z_0-9]*$", message = "Invalid Input")
	@Column(name = "EngineNo", columnDefinition = "nvarchar(30) not null")
	private String EngineNo;

	@NotEmpty(message = "Please enter Chassis No")
	@Size(min = 2, max = 30, message = "ChassisNo must be between 2 and 30 character")
	@Pattern(regexp = "^[A-Za-z_0-9]*$", message = "Invalid Input")
	@Column(name = "ChassisNo", columnDefinition = "nvarchar(30) not null")
	private String ChassisNo;

	@NotEmpty(message = "Please enter Vehicle Registration No")
	@Size(min = 2, max = 30, message = "Vehicle Registration No must be between 2 and 30 character")
	@Pattern(regexp = "^[A-Za-z_0-9_-]*$", message = "Invalid Input")
	@Column(name = "VehicleRegistrationNo", columnDefinition = "nvarchar(30) not null")
	private String VehicleRegistrationNo;

	@NotEmpty(message = "Please enter Billing Currency")
	@Column(name = "BillingCurrency", columnDefinition = "nvarchar(30) not null")
	private String BillingCurrency;

	@NotNull(message = "Please enter Sum Insured")
	@DecimalMin(value = "0", message = "Sum Insured must not be negative")
	@Digits(integer = 17, fraction = 2)
	@Column(name = "SumInsured", precision = 17, scale = 2, columnDefinition = "decimal (17,2) not null")
	private BigDecimal SumInsured;

	@NotNull(message = "Please enter Rate")
	@Digits(integer = 7, fraction = 5)
	@DecimalMin(value = "0.00000", message = "Rate must not be negative")
	@Column(name = "Rate", precision = 7, scale = 5, columnDefinition = "decimal (7,5) not null")
	private BigDecimal Rate;

	@Column(name = "AnnualPremium", precision = 17, scale = 2, columnDefinition = "decimal (17,2) not null")
	private BigDecimal AnnualPremium;

	@Column(name = "PostedPremium", precision = 17, scale = 2, columnDefinition = "decimal (17,2) not null")
	private BigDecimal PostedPremium;

	@Column(name = "PolicyStatus")
	private String PolicyStatus;

	public MotorPolicy() {
	}

	public MotorPolicy(Date inceptionDate, Date expiryDate, String policyOwner, String engineNo, String chassisNo,
			String vehicleRegistrationNo, String billingCurrency, BigDecimal sumInsured, BigDecimal rate,
			BigDecimal annualPremium, BigDecimal postedPremium, String policyStatus) {
		super();
		InceptionDate = inceptionDate;
		ExpiryDate = expiryDate;
		PolicyOwner = policyOwner;
		EngineNo = engineNo;
		ChassisNo = chassisNo;
		VehicleRegistrationNo = vehicleRegistrationNo;
		BillingCurrency = billingCurrency;
		SumInsured = sumInsured;
		Rate = rate;
		AnnualPremium = annualPremium;
		PostedPremium = postedPremium;
		PolicyStatus = policyStatus;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public Date getInceptionDate() {
		return InceptionDate;
	}

	public void setInceptionDate(Date inceptionDate) {
		InceptionDate = inceptionDate;
	}

	public Date getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		ExpiryDate = expiryDate;
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

	public String getBillingCurrency() {
		return BillingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		BillingCurrency = billingCurrency;
	}

	public BigDecimal getSumInsured() {
		return SumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		SumInsured = sumInsured;
	}

	public BigDecimal getRate() {
		return Rate;
	}

	public void setRate(BigDecimal rate) {
		Rate = rate;
	}

	public BigDecimal getAnnualPremium() {
		return AnnualPremium;
	}

	public void setAnnualPremium(BigDecimal annualPremium) {
		AnnualPremium = annualPremium;
	}

	public BigDecimal getPostedPremium() {
		return PostedPremium;
	}

	public void setPostedPremium(BigDecimal postedPremium) {
		PostedPremium = postedPremium;
	}

	public String getPolicyStatus() {
		return PolicyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		PolicyStatus = policyStatus;
	}

}
