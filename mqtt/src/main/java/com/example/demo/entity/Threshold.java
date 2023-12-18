package com.example.demo.entity;

public class Threshold {
	private int id;
	private int min, max;
	
	public Threshold() {
		// TODO Auto-generated constructor stub
	}

	public Threshold(int id, int min, int max) {
		super();
		this.id = id;
		this.min = min;
		this.max = max;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "Threshold [id=" + id + ", min=" + min + ", max=" + max + "]";
	}
	
}
