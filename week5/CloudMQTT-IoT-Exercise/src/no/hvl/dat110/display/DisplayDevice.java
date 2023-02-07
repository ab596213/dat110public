package no.hvl.dat110.display;

import org.eclipse.paho.client.mqttv3.MqttException;

import no.hvl.dat110.mqtt.brokerclient.MQTTSubClient;

public class DisplayDevice extends Thread {
		
	
	public void run() {
		
		System.out.println("Display started");	
		
		// TODO 
		
		// Make a new instance of MQTTSubClient	
		
		// use the sub instance to get the temperature
		
		// Do the display in the MQTTSubClient
		try {
			MQTTSubClient sc = new MQTTSubClient();
			
			
			
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new RuntimeException("DisplayDevice Client Not yet implemented...");
		
	}

}
