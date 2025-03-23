package com.sfs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfs.dto.LoginFormDTO;
import com.sfs.dto.RegisterFormDTO;
import com.sfs.dto.ResetPwtFormDTO;
import com.sfs.dto.UserDTO;
import com.sfs.entities.CityEntity;
import com.sfs.entities.CountryEntity;
import com.sfs.entities.StateEntity;
import com.sfs.entities.UserEntity;
import com.sfs.repository.CityRepo;
import com.sfs.repository.CountryRepo;
import com.sfs.repository.StateRepo;
import com.sfs.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;

	@Override
	public Map<Integer, String> getCountries() {		
		Map<Integer, String> countryList = new HashMap<>();		
		try {
			List<CountryEntity> countries = countryRepo.findAll();
			countries.forEach(c ->{
				countryList.put(c.getCountryId(), c.getCountryName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return countryList;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer, String> stateList = new HashMap<>();
		
		try {
			List<StateEntity> states = stateRepo.findByCountryId(countryId);
			states.forEach(s -> {
				stateList.put(s.getStateId(), s.getStateName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return stateList;
	}

	@Override
	public Map<Integer, String> getCity(Integer stateId) {
		Map<Integer, String> cityList = new HashMap<>();
		
		try {
			List<CityEntity> cities = cityRepo.findByStateId(stateId);
			cities.forEach(c -> {
				cityList.put(c.getCityId(), c.getCityName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return cityList;
	}

	@Override
	public boolean duplicateEmailCheck(String Email) {
		UserEntity user = new UserEntity();
		try {
			user = userRepo.findByUserEmail(Email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user != null;
	}

	@Override
	public boolean saveUser(RegisterFormDTO dto) {		
		try {
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(dto, userEntity);
			
			CountryEntity country = countryRepo.findById(dto.getCountryId()).orElse(null);
			userEntity.setCountry(country);
			
			StateEntity stateEntity = stateRepo.findById(dto.getStateId()).orElse(null);
			userEntity.setState(stateEntity);
			
			CityEntity cityEntity = cityRepo.findById(dto.getCityId()).orElse(null);
			userEntity.setCity(cityEntity);
			
			String randomPwd = generateRandomePwd();
			
			userEntity.setPassword(randomPwd);
			userEntity.setReset_pwd("No");
			
			UserEntity user = userRepo.save(userEntity);
			
			if(user.getUserId() != null) {
				String subject = "Your Accound created";
				String body =  "Your Password to be Login : "+randomPwd;
				String to = user.getUserEmail();
				emailService.sendMail(subject, body, to);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return false;
	}

	@Override
	public UserDTO login(LoginFormDTO dto) {		
		try {
			UserEntity userEntity = userRepo.findByUserEmailAndPassword(dto.getUserEmail(), dto.getPassword());
			if(userEntity != null) {
				UserDTO userDto = new UserDTO();
				BeanUtils.copyProperties(userEntity, userDto);
				return userDto;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwtFormDTO dto) {
		UserEntity userEntity = userRepo.findByUserEmail(dto.getUserEmail());
		userEntity.setPassword(dto.getNewPwd());
		userEntity.setReset_pwd("Yes");
		UserEntity updateUser = userRepo.save(userEntity);
		return updateUser != null;
	}
	
	private String generateRandomePwd() {
		String upperCaseLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetter = "abcdefghijklmnopqrstuvwxyz";
		
		String alphabet = upperCaseLetter + lowerCaseLetter;
		Random randome = new Random();
		StringBuffer generatePwd = new StringBuffer();
		for(int i=0; i<5; i++) {
			int randomIndex = randome.nextInt(alphabet.length());
			generatePwd.append(alphabet.charAt(randomIndex));
		}
		return generatePwd.toString();
	}

}
