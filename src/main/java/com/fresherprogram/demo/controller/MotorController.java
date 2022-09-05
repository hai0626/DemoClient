package com.fresherprogram.demo.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fresherprogram.demo.extension.GlobalExceptionHandler;
import com.fresherprogram.demo.model.Client;
import com.fresherprogram.demo.model.MotorPolicy;
import com.fresherprogram.demo.repository.ClientRepository;
import com.fresherprogram.demo.service.MotorService;

@Controller
public class MotorController {

	final static Logger logger = Logger.getLogger(MotorController.class);

	@Autowired
	private MotorService motorService;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private GlobalExceptionHandler exceptionHandler;

	private Locale locale;
	static BigDecimal hundred = new BigDecimal(100);
	static BigDecimal numberDay;
	static BigDecimal dayOfYear = new BigDecimal(365);
	static BigDecimal annualPremium;

	static BigDecimal calAnnualPremium(BigDecimal a, BigDecimal b) {
		return a.multiply(b).divide(hundred);
	}
	static BigDecimal calPostedPremium(BigDecimal a, BigDecimal b) {
		return a.multiply(b);
	}
	
	

	@GetMapping({ "/motor" })
	public ModelAndView show(String local) {
		ModelAndView mav = new ModelAndView("list-motor");
		if (local != null) {
			locale = new Locale(local);
		} else {
			locale = new Locale("us");
		}
		ResourceBundle bundle = ResourceBundle.getBundle("res", locale);
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = bundle.getString(key);
			mav.addObject(key, value);
		}
		List<MotorPolicy> list = motorService.getAll();
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("/AddMotor")
	public ModelAndView addClient() {
		ModelAndView mav = new ModelAndView("add-motor");
		MotorPolicy motor = new MotorPolicy();
		mav.addObject("motor", motor);
		return mav;
	}

	@PostMapping("/CreateMotor")
	public String createClient(@ModelAttribute("motor") @Valid MotorPolicy motor, BindingResult bindingResult,
			RedirectAttributes model, String check) {
		Optional<Client> clientById = clientRepository.findById(motor.getPolicyOwner());
		Optional<MotorPolicy> chassis = motorService.findByChassis(motor.getChassisNo());
		Optional<MotorPolicy> engine = motorService.findByChassis(motor.getEngineNo());
		Optional<MotorPolicy> reg = motorService.findByRegister(motor.getVehicleRegistrationNo());

		motor.setChassisNo(motor.getChassisNo().toUpperCase());
		motor.setEngineNo(motor.getEngineNo().toUpperCase());
		motor.setVehicleRegistrationNo(motor.getVehicleRegistrationNo().toUpperCase());

		if (bindingResult.hasErrors()) {
			return "add-motor";
		}
		if (!clientById.isPresent() || motor.getExpiryDate().before(motor.getInceptionDate()) || chassis.isPresent()
				|| engine.isPresent() || reg.isPresent()) {
			if (!clientById.isPresent()) {
				bindingResult.addError(new FieldError("motor", "PolicyOwner", exceptionHandler.clientExist()));
				logger.error("This is error : " + exceptionHandler.clientExist());
			}
			if (engine.isPresent()) {
				bindingResult.addError(new FieldError("motor", "EngineNo", exceptionHandler.duplicate()));
				logger.error("This is error : " + exceptionHandler.duplicate());
			}
			if (chassis.isPresent()) {
				bindingResult.addError(new FieldError("motor", "ChassisNo", exceptionHandler.duplicate()));
				logger.error("This is error : " + exceptionHandler.duplicate());
			}
			if (reg.isPresent()) {
				bindingResult.addError(new FieldError("motor", "VehicleRegistrationNo", exceptionHandler.regExist()));
				logger.error("This is error : " + exceptionHandler.regExist());
			}
			if (motor.getExpiryDate().before(motor.getInceptionDate())) {
				bindingResult.addError(new FieldError("motor", "ExpiryDate", exceptionHandler.dayLarger()));
				logger.error("This is error : " + exceptionHandler.dayLarger());
			}
			return "add-motor";
		}

		long countDay = TimeUnit.DAYS.convert(
				Math.abs(motor.getExpiryDate().getTime() - motor.getInceptionDate().getTime()), TimeUnit.MILLISECONDS);
		numberDay = BigDecimal.valueOf(countDay).divide(dayOfYear, 5, 5);
		annualPremium = calAnnualPremium(motor.getSumInsured(), motor.getRate());
		motor.setAnnualPremium(annualPremium);
		motor.setPostedPremium(calPostedPremium(annualPremium, numberDay));

		if (check.equals("Save")) {
			motor.setPolicyStatus("PN");
		} else if (check.equals("Issue")) {
			motor.setPolicyStatus("IF");
		} else {
			return "add-motor";
		}
		motorService.saveMotorPolicy(motor);
		model.addFlashAttribute("success", "Create policy successfully");
		return "redirect:/motor";
	}

	@GetMapping("/UpdateMotor")
	public ModelAndView updatePolicy(@RequestParam String Id) {
		ModelAndView mav = new ModelAndView("update-motor");
		MotorPolicy mp = motorService.detailMotor(Id);
		mav.addObject("motor", mp);
		return mav;
	}

	@PostMapping("/SaveMotor")
	public String savePoliCy(@ModelAttribute("motor") @Valid MotorPolicy motor, BindingResult bindingResult,
			RedirectAttributes model, String check) {
		Optional<Client> clientById = clientRepository.findById(motor.getPolicyOwner());
		motor.setChassisNo(motor.getChassisNo().toUpperCase());
		motor.setEngineNo(motor.getEngineNo().toUpperCase());
		motor.setVehicleRegistrationNo(motor.getVehicleRegistrationNo().toUpperCase());		
		
		if (bindingResult.hasErrors()) {
			return "update-motor";
		}
		if (motor.getExpiryDate().before(motor.getInceptionDate())) {
			bindingResult.rejectValue("ExpiryDate", "motor.ExpiryDate", exceptionHandler.dayLarger());
			logger.error("This is error : " + exceptionHandler.dayLarger());
			return "update-motor";
		}

		long countDay = TimeUnit.DAYS.convert(
				Math.abs(motor.getExpiryDate().getTime() - motor.getInceptionDate().getTime()), TimeUnit.MILLISECONDS);

		numberDay = BigDecimal.valueOf(countDay).divide(dayOfYear, 5, 5);
		annualPremium = calAnnualPremium(motor.getSumInsured(), motor.getRate());
		motor.setAnnualPremium(annualPremium);
		motor.setPostedPremium(calPostedPremium(annualPremium, numberDay));
		
		if (check.equals("Save")) {
			motor.setPolicyStatus("PN");
		} else if (check.equals("Issue")) {
			motor.setPolicyStatus("IF");
		} else {
			return "update-motor";
		}
		motorService.saveMotorPolicy(motor);
		model.addFlashAttribute("success", "Update policy successfully");
		return "redirect:/motor";
	}

	@GetMapping("/DetailMotor")
	public ModelAndView detailPolicy(@RequestParam String Id) {
		ModelAndView mav = new ModelAndView("detail-motor");
		MotorPolicy mp = motorService.detailMotor(Id);
		mav.addObject("motor", mp);
		return mav;
	}
}
