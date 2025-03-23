package com.sfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfs.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findByUserEmail(String userEmail);
	public UserEntity findByUserEmailAndPassword(String userEmail, String password);

}
