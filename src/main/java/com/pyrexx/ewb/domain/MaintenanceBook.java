package com.pyrexx.ewb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.pyrexx.ewb.domain.Room;

@XmlRootElement
public class MaintenanceBook {

	private int id;
	private String postcode;
	private String road;
	private String housenumber;
	private String city;
	private String floor;
	private String position;

	// Renter data
	private String renter_Name;
	private String renter_Firstname;
	private String renter_Tel;

	@XmlElement
	private List<Room> rooms;

	public MaintenanceBook() {
	}
	
	public MaintenanceBook(String postcode, String road, String housenumber,
			String city, String floor, String position) {
		this.postcode = postcode;
		this.road = road;
		this.housenumber = housenumber;
		this.city = city;
		this.floor = floor;
		this.position = position;
		this.renter_Name = "";
		this.renter_Firstname = "";
		this.renter_Tel = "";
		if (this.rooms == null) {
			this.rooms = new ArrayList<Room>();
		}
	}
	
	public MaintenanceBook(String postcode, String road, String housenumber,
			String city, String floor, String position, String renterName,
			String renterFirstname, String renterTel) {
		this.postcode = postcode;
		this.road = road;
		this.housenumber = housenumber;
		this.city = city;
		this.floor = floor;
		this.position = position;
		this.renter_Name = renterName;
		this.renter_Firstname = renterFirstname;
		this.renter_Tel = renterTel;
		if (this.rooms == null) {
			this.rooms = new ArrayList<Room>();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getRenter_Name() {
		return renter_Name;
	}

	public void setRenter_Name(String renter_Name) {
		this.renter_Name = renter_Name;
	}

	public String getRenter_Firstname() {
		return renter_Firstname;
	}

	public void setRenter_Firstname(String renter_Firstname) {
		this.renter_Firstname = renter_Firstname;
	}

	public String getRenter_Tel() {
		return renter_Tel;
	}

	public void setRenter_Tel(String renter_Tel) {
		this.renter_Tel = renter_Tel;
	}

}
