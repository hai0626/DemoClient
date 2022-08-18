package com.fresherprogram.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fresherprogram.demo.model.Client;

import com.fresherprogram.demo.repository.ClientRepository;

@Controller
public class ClientController {
	@Autowired
	private ClientRepository cRepo;

	@GetMapping({ "/show", "/", "/list" })
	public ModelAndView show(Client client, String keyword) {
		ModelAndView mav = new ModelAndView("list-client");
		Locale locale = new Locale("us");

		ResourceBundle bundle = ResourceBundle.getBundle("res", locale);
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = bundle.getString(key);
			mav.addObject(key, value);
		}
		if (keyword != null) {
			List<Client> list = cRepo.findByKeyword(keyword);
			mav.addObject("client", list);
		} else {
			List<Client> list = cRepo.findAll();
			mav.addObject("client", list);
		}
		return mav;
	}

	@GetMapping("/addClientForm")
	public ModelAndView addClient(@ModelAttribute @Valid Client client, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView("add-client");
		Client newClient = new Client();

		mav.addObject("client", newClient);
		return mav;
	}

	@PostMapping("/createClient")
	public String createClient(@Valid Client client, BindingResult bindingResult, RedirectAttributes model) {

		Optional<Client> clientByIdentityNumber = cRepo.findClientByIdentityNumber(client.getIdentityNumber());
		if (bindingResult.hasErrors()) {
			return "add-client";
		}
		if (clientByIdentityNumber.isPresent()) {
			bindingResult.rejectValue("IdentityNumber", "client.identityNumber",
					"There is already a Client registered with the ID Number provided");
			return "add-client";
		}
		cRepo.save(client);
		model.addFlashAttribute("success", "Create clien success");
		return "redirect:/list";
	}

	@PostMapping("/saveClient")
	public String saveClient(@Valid Client client, BindingResult bindingResult, RedirectAttributes model) {
		if (bindingResult.hasErrors()) {
			return "update-client";
		}

		cRepo.save(client);
		model.addFlashAttribute("update", "Update client success");
		return "redirect:/list";
	}

	@GetMapping("/updateClientForm")
	public ModelAndView updateClientForm(@RequestParam String Id) {
		ModelAndView mav = new ModelAndView("update-client");
		Client cli = cRepo.findById(Id).get();
		mav.addObject("client", cli);
		return mav;
	}

}
