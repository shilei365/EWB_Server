package com.pyrexx.ewb.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="UserProfile")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProfile {
	
	private int userId;
	private int id;
	private String surname;
	private String firstname;
	private String street;
	private String postcode;
	private String city;
	private int gender; // 1=Herr, 2=Frau
	private String telephone;
	private String email;
	
	public UserProfile () {}

	public UserProfile (String name, String firstname, String street, String postcode, String city, int gender, String tel, String email) {
		this.surname = name;
		this.firstname = firstname;
		this.street = street;
		this.postcode = postcode;
		this.gender = gender;
		this.city = city;
		this.telephone = tel;
		this.email = email;
	}
	
	public int getGender() {
		return gender;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
