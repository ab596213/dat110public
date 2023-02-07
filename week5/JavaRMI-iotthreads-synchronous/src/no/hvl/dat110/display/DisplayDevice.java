package no.hvl.dat110.display;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import no.hvl.dat110.rpcinterface.TempSensorInterface;

public class DisplayDevice extends Thread {
		
	
	public void run() {
		
		System.out.println("Display started...");	
		
		// TODO
		
		try {
			// Get a reference to the registry using the port
			Registry registry = LocateRegistry.getRegistry(TempSensorInterface.SERVER_PORT);
			
			// Look up the registry for the remote object (TempSensorInterface) using the name TempSensorInterface.REMOTE_IFACE_NAME
			TempSensorInterface si = (TempSensorInterface) registry.lookup(TempSensorInterface.REMOTE_IFACE_NAME);
			
			// loop 10 times and read the temp value from the TemperatureSensor each time
			// get the temperature value by calling the getTemperature remote method via the object of TempSensorInterface
			// print the temperature value to console
			for (int i = 0; i < 10; i++) {
				
				try {
					String temp = si.getTemperature();
					System.out.println("Display: [Temp] " + temp);
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
					
			}
			
			
		} catch(RemoteException | NotBoundException e) {
			System.err.println("Error in RMI "+e.getMessage());
			e.getStackTrace();
		}
		
		
	}
}
