package com.sfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfs.entities.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer>{

}
