package com.example.demo.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.SoilMoisture;
import com.example.demo.mqtt.SubscribeSample;

//@Component
public class SubSchedule {
	private List<SoilMoisture> list = new ArrayList<>();
	private DAO dao = new DAO();
	
	public List<SoilMoisture> getList() {
		return list;
	}

	public void setList(List<SoilMoisture> list) {
		this.list = list;
	}

	public SubSchedule() {
		// TODO Auto-generated constructor stub
	}
	
	//@Scheduled(fixedRate = 5000)
	 public void getSoilMoistureFromSensor() {
		SubscribeSample sub = new SubscribeSample();
//		SoilMoisture soil = sub.getSoil();
//		
//		list.add(soil);
//		
//		if(list.size() == 10) {
//			for(SoilMoisture soilM : list) {
//				dao.addHum(soilM);
//			}
//			list.clear();
//		}
	}

}
