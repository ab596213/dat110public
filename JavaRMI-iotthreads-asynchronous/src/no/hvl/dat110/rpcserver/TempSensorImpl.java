package no.hvl.dat110.rpcserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import no.hvl.dat110.rpcinterface.DisplayCallbackInterface;
import no.hvl.dat110.rpcinterface.TempSensorInterface;

/**
 * For demonstration purpose in dat110 course
 */

public class TempSensorImpl extends UnicastRemoteObject implements TempSensorInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DisplayCallbackInterface displaycallbackobj = null;
	
	private boolean exit = false;
	
	public TempSensorImpl() throws RemoteException {
		super();
		Thread shutdownhook = new Thread (() -> System.out.println("Temp RPC server is now shutting down.."));
		Runtime.getRuntime().addShutdownHook(shutdownhook);
	}
	
	@Override
	public void registerDisplayCallback(DisplayCallbackInterface displaycallbackobj) throws RemoteException {
		this.displaycallbackobj = displaycallbackobj;
	}

	@Override
	public void putTemperature(String temp) throws RemoteException{
		if (this.displaycallbackobj != null) {
			this.displaycallbackobj.notifyTemp(temp);
		}
	}
	
	@Override
	public void setExit(boolean exit) throws RemoteException {
		this.exit = exit;
	}
	
	@Override
	public void shutdown() throws RemoteException {
		System.out.println("Temp RPC server will shut down in 2 seconds");
		try  {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	@Override
	public boolean isExit() throws RemoteException {
		return exit;
	}

}
