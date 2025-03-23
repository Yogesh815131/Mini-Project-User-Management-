package com.sfs.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_tbl")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userName;
	@Column(unique = true)
	private String userEmail;
	private String userPhno;
	private String password;
	private String reset_pwd;
	
	@ManyToOne
	@JoinColumn(name = "countryId", referencedColumnName = "countryId")
	private CountryEntity country;
	@ManyToOne
	@JoinColumn(name = "stateId", referencedColumnName = "stateId")
	private StateEntity state;
	@ManyToOne
	@JoinColumn(name = "cityId", referencedColumnName = "cityId")
	private CityEntity city;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationTime;
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate updationTime;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhno() {
		return userPhno;
	}
	public void setUserPhno(String userPhno) {
		this.userPhno = userPhno;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReset_pwd() {
		return reset_pwd;
	}
	public void setReset_pwd(String reset_pwd) {
		this.reset_pwd = reset_pwd;
	}
	public CountryEntity getCountry() {
		return country;
	}
	public void setCountry(CountryEntity country) {
		this.country = country;
	}
	public StateEntity getState() {
		return state;
	}
	public void setState(StateEntity state) {
		this.state = state;
	}
	public CityEntity getCity() {
		return city;
	}
	public void setCity(CityEntity city) {
		this.city = city;
	}
	public LocalDate getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDate creationTime) {
		this.creationTime = creationTime;
	}
	public LocalDate getUpdationTime() {
		return updationTime;
	}
	public void setUpdationTime(LocalDate updationTime) {
		this.updationTime = updationTime;
	}
	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhno="
				+ userPhno + ", password=" + password + ", reset_pwd=" + reset_pwd + ", country=" + country + ", state="
				+ state + ", city=" + city + ", creationTime=" + creationTime + ", updationTime=" + updationTime + "]";
	}
}
