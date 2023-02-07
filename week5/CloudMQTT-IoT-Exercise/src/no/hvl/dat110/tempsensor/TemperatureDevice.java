package no.hvl.dat110.tempsensor;

import no.hvl.dat110.mqtt.brokerclient.MQTTPubClient;

public class TemperatureDevice extends Thread {

	private TemperatureSensor sn;
	
	public TemperatureDevice() {
		this.sn = new TemperatureSensor();
	}
	
	public void run() {
		
		System.out.println("temperature device started");
		
		//call MQTTPubClient (create a new instance) and make connection
		MQTTPubClient temppub = new MQTTPubClient();
		temppub.connect();
		
		// loop 10 times to read temp values			
			
			// read the temp from the TemperatureSensor
			
			// use the MQTTPubClient instance object to publish the temp to the MQTT Broker 
		
		for (int i = 0; i < 10; i++) {
			try  {
				int temp = sn.read();
				System.out.println("sensor rewading [temp]: " + temp);
				
				temppub.publish(String.valueOf(temp));
				
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		throw new RuntimeException("TemperatureDevice Client Not yet implemented...");
		
	}
	
}
