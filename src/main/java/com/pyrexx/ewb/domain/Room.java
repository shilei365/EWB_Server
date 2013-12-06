package com.pyrexx.ewb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.pyrexx.ewb.domain.Sensor;

@XmlRootElement
public class Room {
	
	private int id;
	private String name;
	@XmlElement
	private List<Sensor> sensors;
	
	public Room () {}
	
	public Room (String name) {
		this.name = name;
		if (this.sensors == null) {
			this.sensors = new ArrayList<Sensor>();
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	
}
