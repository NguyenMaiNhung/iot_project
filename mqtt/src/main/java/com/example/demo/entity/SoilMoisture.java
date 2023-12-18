package com.example.demo.entity;

public class SoilMoisture {
	private int id;
	private float hum;
	private String time;
	
	public SoilMoisture() {
		// TODO Auto-generated constructor stub
	}

	public SoilMoisture(int id, float hum, String time) {
		super();
		this.id = id;
		this.hum = hum;
		this.time = time;
	}

	public SoilMoisture(float hum, String time) {
		this.hum = hum;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getHum() {
		return hum;
	}

	public void setHum(float hum) {
		this.hum = hum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "SoilMoisture [id=" + id + ", hum=" + hum + ", time=" + time + "]";
	}
	
}
