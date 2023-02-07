package no.hvl.dat110.tempsensor;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import no.hvl.dat110.rpcinterface.TempSensorInterface;

public class TemperatureDevice extends Thread {

	private TemperatureSensor sn;
	
	public TemperatureDevice() {
		this.sn = new TemperatureSensor();
	}
	
	public void run() {
		
		System.out.println("temperature device started");
		
		// TODO
		try {
		
		// Get a reference to the registry using the port
		Registry registry = LocateRegistry.createRegistry(TempSensorInterface.SERVER_PORT);
		
		// Look up the registry for the remote object (TempSensorInterface) using the name TempSensorInterface.REMOTE_IFACE_NAME
		TempSensorInterface si = (TempSensorInterface) registry.lookup(TempSensorInterface.REMOTE_IFACE_NAME);
		
		// loop 10 times and read the temp value from the TemperatureSensor each time
		// set the temperature value by calling the setTemperature remote method via the object of TempSensorInterface
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(500);
				int temp = sn.read();
				System.out.println("temp: " + temp);
				si.putTemperature(String.valueOf(temp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		si.setExit(true);
		
		
		} catch(RemoteException | NotBoundException e) {
			System.err.println("Error in RMI "+e.getMessage());
			e.getStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		TemperatureDevice tempdevice = new TemperatureDevice();
		
		tempdevice.start();
		
		try {
			tempdevice.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
