package com.example.demo.mqtt;

import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishSample {
	
	private String content;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public PublishSample(String content) {
		// TODO Auto-generated constructor stub
		String broker = "tcp://192.168.43.68:1883";
		String username = "admin";
		String password = "12345678";
		String clientid = "local_send_state";
		int qos = 0;
		String topic = "ESP32/Relay";

		try {
			System.out.println("hello");
			MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(password.toCharArray());
			// connect
			client.connect(options);
			// create message and setup QoS
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			// publish message
			client.publish(topic, message);
			System.out.println("Message published");
			System.out.println("topic: " + topic);

			System.out.println("message content: " + content);
			// disconnect
			client.disconnect();
			// close client
			client.close();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}
}
