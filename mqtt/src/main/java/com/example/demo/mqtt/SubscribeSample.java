package com.example.demo.mqtt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.example.demo.DAO.DAO;
import com.example.demo.entity.SoilMoisture;

public class SubscribeSample {
	
	private MqttClient client;
	private String topic_hum = "ESP32/Soil/Humidity";
	private DAO dao = new DAO();
	
	private SoilMoisture soil;
	
	public SoilMoisture getSoil() {
		return soil;
	}
	
	public void setSoil(SoilMoisture soil) {
		this.soil = soil;
	}

	public SubscribeSample() {
		// TODO Auto-generated constructor stub
		try {
			this.connect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	MqttCallback callback = new MqttCallback() {
		
		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			// TODO Auto-generated method stub
			String receiveHum = new String(message.getPayload());
		    System.out.println("topic - " + topic + ": " + receiveHum);
		    LocalDateTime currentTime = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedTime = currentTime.format(formatter);
	        
	        SoilMoisture soiled = new SoilMoisture(Float.parseFloat(receiveHum), formattedTime);
	        setSoil(soiled);
	        dao.addHum(soiled);
		}
		
		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void connectionLost(Throwable cause) {
			// TODO Auto-generated method stub
//			SubscribeSample sub = new SubscribeSample();
			System.out.println("connect lost");
		}

	};
	
	public void connect() throws MqttException {
		client = new MqttClient("tcp://192.168.43.68:1883", "local_receive",  new MemoryPersistence());
	    client.setCallback(callback);
	    MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("admin");
        options.setPassword("12345678".toCharArray());
	    client.connect(options);
	    client.subscribe(topic_hum, 0);
	}
	
//    public static void main(String[] args) {
//    	SubscribeSample sub = new SubscribeSample();
//    }
}
