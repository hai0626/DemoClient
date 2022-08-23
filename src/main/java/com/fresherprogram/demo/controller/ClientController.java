package com.fresherprogram.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.*;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import com.fresherprogram.demo.model.Message;

import com.fresherprogram.demo.repository.ClientRepository;
import com.fresherprogram.demo.repository.MessageRepository;




@Controller
public class ClientController {
	@Autowired
	private ClientRepository cRepo;
	private Locale locale ;
	@Autowired
	private MessageRepository mRepo;
	
	final static Logger logger = Logger.getLogger(ClientController.class);
	
	
	@GetMapping({ "/show", "/", "/list" })
	public ModelAndView show(Client client, String keyword, String local) {
		ModelAndView mav = new ModelAndView("list-client");	
		if(local!=null) {
		 locale = new Locale(local);
		}
		else {
			 locale = new Locale("us");
		}
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
		Date date = new Date();

		Optional<Client> clientByIdentityNumber = cRepo.findClientByIdentityNumber(client.getIdentityNumber());
		
		if (bindingResult.hasErrors()) {
			return "add-client";
		}
		if (clientByIdentityNumber.isPresent()) {
			
			bindingResult.rejectValue("IdentityNumber", "client.identityNumber",
					mRepo.findMessageById(1));
			logger.error("This is error : " + mRepo.findMessageById(1));
			return "add-client";
		}
		if(client.getDateOfBirth().after(date)) {			
				bindingResult.rejectValue("DateOfBirth", "client.dateOfBirth",
						mRepo.findMessageById(2));
				logger.error("This is error : " + mRepo.findMessageById(2));
				return "add-client";
		}
		cRepo.save(client);
		model.addFlashAttribute("success", "Create clien success");
		return "redirect:/list";
	}

	@PostMapping("/saveClient")
	public String saveClient(@Valid Client client, BindingResult bindingResult, RedirectAttributes model) {
		Date date = new Date();
		if (bindingResult.hasErrors()) {
			return "update-client";
		}
		if(client.getDateOfBirth().after(date)) {			
			bindingResult.rejectValue("DateOfBirth", "client.dateOfBirth",
					mRepo.findMessageById(2));
			logger.error("This is error : " + mRepo.findMessageById(2));
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
	
	@GetMapping("/detailClientForm")
	public ModelAndView detailClientForm(@RequestParam String Id) {
		ModelAndView mav = new ModelAndView("detail-client");
		Client cli = cRepo.findById(Id).get();
		mav.addObject("client", cli);
		return mav;
	}

}
