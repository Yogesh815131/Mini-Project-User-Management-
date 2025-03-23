package com.sfs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfs.dto.LoginFormDTO;
import com.sfs.dto.QuoteApiResponseDTO;
import com.sfs.dto.RegisterFormDTO;
import com.sfs.dto.ResetPwdFormDTO;
import com.sfs.dto.UserDTO;
import com.sfs.service.DashboardService;
import com.sfs.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DashboardService dashboardService;
	
	
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
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {		
		Map<Integer, String> statesMap = userService.getStates(countryId);		
		return statesMap;
	}
	
	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {		
		Map<Integer, String> citiesMap = userService.getCity(stateId);		
		return citiesMap;
	}
	
	@PostMapping("/register")
	public String handleRegister(RegisterFormDTO registerFormDTO, Model model) {		
		boolean status = userService.duplicateEmailCheck(registerFormDTO.getUserEmail());
		if(status) {
			model.addAttribute("emsg", "Dublicate Email Found");
		}else {
			boolean saveUser = userService.saveUser(registerFormDTO);
			if(saveUser) {
				model.addAttribute("smsg", "Register Success, Please check your Email");
			}else {
				model.addAttribute("emsg", "Regitration Failed");
			}
		}		
		model.addAttribute("countries", userService.getCountries());		
		return "register";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		LoginFormDTO loginFormDTO = new LoginFormDTO();
		model.addAttribute("loginForm", loginFormDTO);
		return "login";
	}
	
	@PostMapping("/login")
	public String handleLogin(LoginFormDTO loginFormDTO, Model model) {
		UserDTO login = userService.login(loginFormDTO);		
		
		if(login == null) {
			model.addAttribute("emsg", "Invalid credintial");
			model.addAttribute("loginForm", loginFormDTO);
		}else {
			String reset_pwd = login.getReset_pwd();
			if("Yes".equals(reset_pwd)) {
				//return dashboard page
				return "redirect:dashboard";
			}else {
				// return pws reset				
				return "pwd-reset-page?email="+login.getUserEmail();
			}
		}		
		return "login";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
		model.addAttribute("quote", quoteApiResponseDTO);
		return "dashboard";
	}
	
	@GetMapping("pwd-reset-page")
	public String loadResetPwd(@PathVariable("email") String email, Model model) {		
		ResetPwdFormDTO resetPwdFormDTO = new ResetPwdFormDTO();
		resetPwdFormDTO.setUserEmail(email);
		model.addAttribute("resetPWd", resetPwdFormDTO);		
		return "resetpwd";		
	}
	
	@PostMapping("/pwd-rest-page")
	public String handleResetPwd(ResetPwdFormDTO dto, Model model) {
		boolean resetPwd = userService.resetPwd(dto);
		if(resetPwd) {
			return "redirect:dashboard";
		}
		return "resetpwd";
	}

}





















