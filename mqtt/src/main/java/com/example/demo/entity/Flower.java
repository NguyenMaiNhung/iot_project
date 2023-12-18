package com.example.demo.entity;

public class Flower {
	private int id;
	private String type;
	private int min, max;
	
	public Flower() {
		// TODO Auto-generated constructor stub
	}

	public Flower(int id, String type, int min, int max) {
		super();
		this.id = id;
		this.type = type;
		this.min = min;
		this.max = max;
	}
	
	public Flower(String type, int min, int max) {
		this.type = type;
		this.min = min;
		this.max = max;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "Flower [id=" + id + ", type=" + type + ", min=" + min + ", max=" + max + "]";
	}

}
