package com.pyrexx.ewb.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sensor {
	
	private String serialnumber;
	private String manufacturer;
	private String type;
	private Double[][] location;
	private Date installation;
	
	public Sensor () {}
	
	public Sensor (String serial, String manufacturer, String type) {
		this.serialnumber = serial;
		this.manufacturer = manufacturer;
		this.type = type;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Double[][] getLocation() {
		return location;
	}

	public void setLocation(Double[][] location) {
		this.location = location;
	}

	public Date getInstallation() {
		return installation;
	}

	public void setInstallation(Date installation) {
		this.installation = installation;
	}
	
	
}
