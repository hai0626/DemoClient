package com.fresherprogram.demo.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fresherprogram.demo.model.Client;
import com.fresherprogram.demo.model.MotorPolicy;
import com.fresherprogram.demo.repository.ClientRepository;
import com.fresherprogram.demo.service.MotorService;

@Controller
public class MotorController {

	@Autowired
	private MotorService motorService;
	private Locale locale;

	@Autowired
	private ClientRepository clientRepository;

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
			RedirectAttributes model) {
		Optional<Client> clientById = clientRepository.findById(motor.getPolicyOwner());
		
		motor.setChassisNo(motor.getChassisNo().toUpperCase());
		motor.setEngineNo(motor.getEngineNo().toUpperCase());
		motor.setVehicleRegistrationNo(motor.getVehicleRegistrationNo().toUpperCase());

		if (bindingResult.hasErrors()) {
			return "add-motor";
		}
		if (clientById.isEmpty()) {
			bindingResult.rejectValue("PolicyOwner", "motor.PolicyOwner", "Client not exist");
			// logger.error("This is error : " + mRepo.findMessageById(1));
			return "add-motor";
		}
		if (motor.getExpiryDate().before(motor.getInceptionDate())) {
			bindingResult.rejectValue("ExpiryDate", "motor.ExpiryDate", "ExpiryDatemust > InceptionDate");
			// logger.error("This is error : " + mRepo.findMessageById(2));
			return "add-motor";
		}

		long countDay = TimeUnit.DAYS.convert(
				Math.abs(motor.getExpiryDate().getTime() - motor.getInceptionDate().getTime()), TimeUnit.MILLISECONDS);
		System.out.println(countDay);

		numberDay = BigDecimal.valueOf(countDay).divide(dayOfYear, 5, 5);

		annualPremium = calAnnualPremium(motor.getSumInsured(), motor.getRate());
		motor.setAnnualPremium(annualPremium);

		motor.setPostedPremium(calPostedPremium(annualPremium, numberDay));
		motor.setPolicyStatus("PN");
		motorService.addMotorPolicy(motor);
		model.addFlashAttribute("success", "Create clien success");
		return "redirect:/motor";
	}
}
