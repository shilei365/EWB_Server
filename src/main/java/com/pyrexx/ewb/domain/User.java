package com.pyrexx.ewb.domain;

import java.util.ArrayList;
import java.util.List;

import com.pyrexx.ewb.domain.MaintenanceBook;
import com.pyrexx.ewb.domain.UserProfile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user")
@XmlRootElement (name = "UserData")
public class User {
	
	private int id;
	private String username;
	private String password;
	@XmlElement
	private List<MaintenanceBook> maintenanceBooks;
	@XmlElement
	private UserProfile userProfile;
	
	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		if (this.maintenanceBooks == null) {
			this.maintenanceBooks = new ArrayList<MaintenanceBook>();
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<MaintenanceBook> getMaintenanceBooks() {
		return maintenanceBooks;
	}

	public void setMaintencenBooks(List<MaintenanceBook> maintenanceBooks) {
		this.maintenanceBooks = maintenanceBooks;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}
