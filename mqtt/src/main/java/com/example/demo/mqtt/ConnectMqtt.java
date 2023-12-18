package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ConnectMqtt {
	private String broker = "tcp://broker.emqx.io:1883";
	private String username = "nhungnm";
	private String password = "Nguyennhung10012002@";
	private String clientid = "JVUYCaWqt4";
	
	MqttClient client;
	
	public ConnectMqtt() {
		// TODO Auto-generated constructor stub
		try {
			client = new MqttClient(broker, clientid, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(password.toCharArray());
			options.setConnectionTimeout(60);
			options.setKeepAliveInterval(60);
			
			// connect
			client.connect(options);
			System.out.println("successfull");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}
	
//	public static void main(String[] args) {
//		ConnectMqtt conn = new ConnectMqtt();
//	}
}
