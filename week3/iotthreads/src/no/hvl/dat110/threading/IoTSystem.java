package no.hvl.dat110.threading;

public class IoTSystem {

	public static void main(String[] args) {
		
		System.out.println("System starting ... ");
		
		TemperatureMeasurement tm = new TemperatureMeasurement();
		
		TemperatureDevice tempdevice1 = new TemperatureDevice(tm);
		TemperatureDevice tempdevice2 = new TemperatureDevice(tm);
		TemperatureDevice tempdevice3 = new TemperatureDevice(tm);
		TemperatureDevice tempdevice4 = new TemperatureDevice(tm);
		DisplayDevice disdevice1 = new DisplayDevice(tm);
		DisplayDevice disdevice2 = new DisplayDevice(tm);
		DisplayDevice disdevice3 = new DisplayDevice(tm);
		DisplayDevice disdevice4 = new DisplayDevice(tm);
		
		tempdevice1.start();
		tempdevice2.start();
		tempdevice3.start();
		tempdevice4.start();
		
		disdevice1.start();
		disdevice2.start();
		disdevice3.start();
		disdevice4.start();
		
		try {
			tempdevice1.join();
			tempdevice2.join();
			tempdevice3.join();
			tempdevice4.join();
			
			disdevice1.join();
			disdevice2.join();
			disdevice3.join();
			disdevice4.join();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("System shutting down ... ");	
		
	}

}
