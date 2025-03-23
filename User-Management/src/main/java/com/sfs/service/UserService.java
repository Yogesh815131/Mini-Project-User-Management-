package com.sfs.service;

import java.util.Map;

import com.sfs.dto.LoginFormDTO;
import com.sfs.dto.RegisterFormDTO;
import com.sfs.dto.ResetPwtFormDTO;
import com.sfs.dto.UserDTO;

public interface UserService {
	
	public Map<Integer,String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCity(Integer stateId);
	
	public boolean duplicateEmailCheck(String Email);
	
	public boolean saveUser(RegisterFormDTO dto);
	
	public UserDTO login(LoginFormDTO dto);
	
	public boolean resetPwd(ResetPwtFormDTO dto);

}
