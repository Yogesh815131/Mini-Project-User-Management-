package com.sfs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sfs.dto.RegisterFormDTO;
import com.sfs.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/welcome")
	public String welcome() {
		
		return "index";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		
		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);
		
		RegisterFormDTO registerFormDTO = new RegisterFormDTO();
		model.addAttribute("registerForm", registerFormDTO);
		
		return "register";
	}
	
	@GetMapping("/register/{countryId}")
	public String getStates(@PathVariable Integer countryId) {
		
		
		return "register";
	}

}
