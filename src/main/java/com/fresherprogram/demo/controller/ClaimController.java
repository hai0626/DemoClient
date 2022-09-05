package com.fresherprogram.demo.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fresherprogram.demo.extension.GlobalExceptionHandler;
import com.fresherprogram.demo.model.Claim;
import com.fresherprogram.demo.model.MotorPolicy;
import com.fresherprogram.demo.service.ClaimService;
import com.fresherprogram.demo.service.MotorService;

@Controller
public class ClaimController {
	final static Logger logger = Logger.getLogger(ClaimController.class);
	private Locale locale;

	@Autowired
	private ClaimService claimService;
	@Autowired
	private MotorService motorService;
	@Autowired
	private GlobalExceptionHandler exceptionHandler;

	@GetMapping("/claim")
	public ModelAndView index(String local) {
		ModelAndView mav = new ModelAndView("list-claim");
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
		List<Claim> list = claimService.listAll();
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("/AddClaim")
	public ModelAndView addClaim() {
		ModelAndView mav = new ModelAndView("add-claim");
		Claim claim = new Claim();
		mav.addObject("claim", claim);
		return mav;
	}

	@PostMapping("/CreateClaim")
	public String createClaim(@Valid Claim claim, BindingResult bindingResult, RedirectAttributes model, String check) {
		Date date = new Date();
		MotorPolicy mp = motorService.findByMotorPolicy(claim.getPolicyNo());

		if (bindingResult.hasErrors()) {
			return "add-claim";
		}
		if (mp == null) {
			bindingResult.addError(new FieldError("claim", "PolicyNo", exceptionHandler.policyExist()));
			return "add-claim";
		}
		claim.setPolicyOwner(mp.getPolicyOwner());
		claim.setEngineNo(mp.getEngineNo());
		claim.setChassisNo(mp.getChassisNo());
		claim.setSumInsured(mp.getSumInsured());
		claim.setVehicleRegistrationNo(mp.getVehicleRegistrationNo());
		if (claim.getDateOccurred().after(date) || !mp.getPolicyStatus().equals("IF")
				|| (claim.getSumInsured().compareTo(claim.getReserveAmount()) == -1)) {
			if (claim.getDateOccurred().after(date)) {
				bindingResult.addError(new FieldError("claim", "DateOccurred", exceptionHandler.dayFuture()));
			}

			if (!mp.getPolicyStatus().equals("IF")) {
				bindingResult.addError(new FieldError("claim", "ClaimStatus", exceptionHandler.policyStatus()));

			}
			if (claim.getSumInsured().compareTo(claim.getReserveAmount()) == -1) {
				bindingResult.addError(new FieldError("claim", "ReserveAmount", exceptionHandler.larger()));
			}
			return "add-claim";
		}

		claim.setClaimStatus("Pending");
		if (check.equals("Save")) {
			claim.setClaimStatus("Pending");
		} else if (check.equals("Approve")) {
			claim.setClaimStatus("Active");
		} else {
			return "add-claim";
		}
		claimService.saveClaim(claim);
		model.addFlashAttribute("success", "Create claim successfully");
		return "redirect:/claim";
	}

	@GetMapping("/UpdateClaim")
	public ModelAndView updateClaim(@RequestParam String Id) {
		ModelAndView mav = new ModelAndView("update-claim");
		Claim claim = claimService.detaiClaim(Id);
		mav.addObject("claim", claim);
		return mav;
	}

	@PostMapping("/SaveClaim")
	public String saveClaim(@Valid Claim claim, BindingResult bindingResult, RedirectAttributes model, String check) {
		Date date = new Date();
		if (bindingResult.hasErrors()) {
			return "add-claim";
		}
		if (claim.getDateOccurred().after(date) || (claim.getSumInsured().compareTo(claim.getReserveAmount()) == -1)) {
			if (claim.getDateOccurred().after(date)) {
				bindingResult.addError(new FieldError("claim", "DateOccurred", exceptionHandler.dayFuture()));
			}
			if (claim.getSumInsured().compareTo(claim.getReserveAmount()) == -1) {
				bindingResult.addError(new FieldError("claim", "ReserveAmount", exceptionHandler.larger()));
			}
			return "add-claim";
		}
		if (check.equals("Save")) {
			claim.setClaimStatus("Pending");
		}
		else {
			claim.setClaimStatus("Active");
		}		
		claimService.saveClaim(claim);
		model.addFlashAttribute("success", "Update claim successfully");
		return "redirect:/claim";
	}
}
