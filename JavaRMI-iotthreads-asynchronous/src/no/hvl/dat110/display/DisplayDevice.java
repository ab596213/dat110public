package no.hvl.dat110.display;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import no.hvl.dat110.rpcinterface.DisplayCallbackInterface;
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
			
			DisplayCallbackInterface displaycallbackobj = new DisplayCallBackImpl();
			
			si.registerDisplayCallback(displaycallbackobj);
			// loop 10 times and read the temp value from the TemperatureSensor each time
			// get the temperature value by calling the getTemperature remote method via the object of TempSensorInterface
			// print the temperature value to console
			System.out.println("Display: temp value will be displayed once notified by TempRPC server");
			while (!si.isExit()) {
				try {
					System.out.println(".");
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			System.out.println();
			si.shutdown();
			
		} catch(RemoteException | NotBoundException e) {
			System.err.println("Error in RMI "+e.getMessage());
			e.getStackTrace();
		}
		
		}
	
	public static void main(String [] args) {
		DisplayDevice display = new DisplayDevice();
		display.start();
		
		try {
			display.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
